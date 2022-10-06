import { Box, Spinner } from "grommet";
import React, { useState } from "react";
import { useEffect } from "react";
import { StyledText } from "./Common";
import Bookmark from "../assets/images/bookmark.png";
import NoImage from "../assets/images/noimage.jpg";
import BookmarkBlank from "../assets/images/bookmark_blank.png";
import { useNavigate } from "react-router-dom";
import { expectTimeHandle } from "../utils/util";
// {
//   "bookmarkId": "string",
//   "category": "string",
//   "courseId": "string",
//   "courseName": "string",
//   "distance": 0,
//   "expectedTime": 0,
//   "finish": "string",
//   "imageUrl": "string",
//   "likeCount": 0,
//   "start": "string",
//   "tags": [
//     {
//       "tagId": 0,
//       "tagName": "string"
//     }
//   ]
// }
export const CourseBox = ({
  loading,
  course: {
    bookmarkId,
    category,
    courseId,
    courseName,
    distance,
    expectedTime,
    finish,
    imageUrl,
    likeCount,
    start,
    tags,
  },
}) => {
  const [bm, setBm] = useState(false);
  const navigate = useNavigate();
  useEffect(() => {
    if (bookmarkId) setBm(true);
  }, []);
  if (loading) return <Spinner />;
  else
    return (
      <Box
        onClick={() => {
          navigate(`/courseDetail/${courseId}`);
        }}
        style={{
          borderRadius: "5px",
          boxShadow: "4px 4px 4px -4px rgb(0 0 0 / 0.2)",
        }}
        pad="medium"
        margin="small"
        width="95vw"
      >
        {/* 코스 박스 */}
        <Box direction="row" width="100%" align="center" gap="medium">
          {/* 사진 */}
          <Box
            width="40%"
            border={{ color: "#439652", size: "medium", side: "all" }}
          >
            <img src={imageUrl ? imageUrl : NoImage} />
          </Box>
          {/* 데이터들 */}
          <Box width="100%">
            {/* 제목, 북마크 버튼 */}
            <Box direction="row" justify="between" align="center">
              <StyledText text={courseName} weight="bold" size="20px" />
              <img src={bm ? Bookmark : BookmarkBlank} width="20vw" />
            </Box>
            {/* 여정 */}
            <Box width="40vw" align="center" margin={{ left: "5vw" }}>
              <StyledText text={start + " - " + finish} color="lightgray" />
            </Box>
            {/* 거리, 예상 시간 */}
            <Box direction="row" gap="medium" justify="end">
              <Box direction="row" align="end" gap="small">
                <StyledText text="거리 : " weight="bold" size="16px" />
                <StyledText text={`${distance} km`} />
              </Box>
              <Box direction="row" align="end" gap="small">
                <StyledText text="예상 시간 : " weight="bold" size="16px" />
                <StyledText text={expectTimeHandle(expectedTime)} />
              </Box>
            </Box>
          </Box>
        </Box>
        {/* 태그 */}
        <Box direction="row">
          {tags &&
            tags.map((t, idx) => {
              return <StyledText text={`#${t.tagName} `} key={t.tagId} />;
            })}
        </Box>
      </Box>
    );
};
