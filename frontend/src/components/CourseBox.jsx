import { Box } from "grommet";
import React, { useState } from "react";
import { useEffect } from "react";
import { StyledText } from "./Common";
import Bookmark from "../assets/images/bookmark.png";
import BookmarkBlank from "../assets/images/bookmark_blank.png";
import { useNavigate } from "react-router-dom";
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

  return (
    <Box
      onClick={() => {
        navigate(`/courseDetail/${courseId}`);
      }}
    >
      {/* 코스 박스 */}
      <Box direction="row">
        {/* 사진 */}
        <Box>
          <img src={imageUrl} />
        </Box>
        {/* 데이터들 */}
        <Box>
          {/* 제목, 북마크 버튼 */}
          <Box direction="row">
            <StyledText text={courseName} weight="bold" size="20px" />
            <img src={bm ? Bookmark : BookmarkBlank} />
            <StyledText text={likeCount} />
          </Box>
          {/* 여정 */}
          <Box>
            <StyledText text={start + " - " + finish} color="lightgray" />
          </Box>
          {/* 거리, 예상 시간 */}
          <Box direction="row">
            <StyledText text="거리 : " wight="bold" />
            <StyledText text={`${distance} km`} />
            <StyledText text="예상 시간 : " wight="bold" />
            <StyledText text={expectedTime} />
          </Box>
        </Box>
      </Box>
      {/* 태그 */}
      <Box direction="row">
        {tags.map((t, idx) => {
          return <StyledText text={`#${t.tagName} `} key={t.tagId} />;
        })}
      </Box>
    </Box>
  );
};
