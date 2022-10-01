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
          borderRadius: "10px",
          boxShadow: "4px 4px 4px -4px rgb(0 0 0 / 0.2)",
          width: "283px",
          height: "143px",
        }}
        background="white"
        pad="medium"
        margin={{ top: "20px" }}
      >
        {/* 코스 박스 */}
        <Box width="100%" align="center">
          {/* 제목, 북마크 버튼 */}
          <Box direction="row" justify="between" width="100%">
            <StyledText
              text={courseName}
              weight="bold"
              size="18px"
              style={{
                width: "80%",
                height: "30px",
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "nowrap",
                display: "block",
              }}
            />
            <Box direction="row" align="start">
              <img
                src={bm ? Bookmark : BookmarkBlank}
                width="18px"
                height="20px"
              />
              {likeCount}
            </Box>
          </Box>
          {/* 코스 데이터, 사진 */}
          <Box direction="row" width="100%" justify="between" align="center">
            <Box gap="small">
              <StyledText
                text={"코스 길이 : " + distance + "km"}
                style={{
                  backgroundColor: "#BDE0EF",
                  borderRadius: "10px",
                  // margin: "3px",
                  fontSize: "12px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                }}
                weight="bold"
              />
              <StyledText
                text={"예상 시간 : " + expectTimeHandle(expectedTime)}
                style={{
                  backgroundColor: "#F8F38F",
                  borderRadius: "10px",
                  fontSize: "12px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                }}
                weight="bold"
              />
              <StyledText
                text={start.split(" ")[0] + " " + start.split(" ")[1]}
                style={{
                  backgroundColor: "#F4D4D4",
                  borderRadius: "10px",
                  fontSize: "12px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                }}
                weight="bold"
              />
            </Box>
            <Box style={{ marginRight: "25px" }}>
              <img
                src={imageUrl ? imageUrl : NoImage}
                width="70px"
                height="70px"
                style={{ borderRadius: "10px" }}
              />
            </Box>
          </Box>
          {/* 사진 */}
        </Box>
        {/* 태그 */}
        <Box direction="row" gap="small">
          {tags &&
            tags.map((t, idx) => {
              return (
                <StyledText text={`#${t.tagName} `} key={t.tagId} size="12px" />
              );
            })}
        </Box>
      </Box>
    );
};
