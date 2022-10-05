import { Avatar, Box, Spinner } from "grommet";
import React, { useState } from "react";
import { useEffect } from "react";
import { StyledText } from "./Common";
import Bookmark from "../assets/images/bookmark.png";
import NoImage from "../assets/images/noimage.jpg";
import BookmarkBlank from "../assets/images/bookmark_blank.png";
import { useNavigate } from "react-router-dom";
import { expectTimeHandle, timeHandle2 } from "../utils/util";
import Clock from "../assets/icons/clock.svg";
import Flag from "../assets/icons/flag.svg";
import Mark from "../assets/icons/mark.svg";
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
            <Box style={{ marginRight: "25px" }}>
              <img
                src={imageUrl ? imageUrl : NoImage}
                width="140px"
                height="75px"
                style={{ borderRadius: "10px", objectFit: "cover" }}
              />
            </Box>
            <Box gap="small" align="end">
              <Box
                justify="center"
                align="center"
                direction="row"
                gap="4px"
                style={{
                  backgroundColor: "#BDE0EF",
                  borderRadius: "10px",
                  padding: "0px 4px",
                  // margin: "3px",
                }}
              >
                <Avatar src={Flag} size="12px" />
                <StyledText size="13px" text={distance + "km"} weight="bold" />
              </Box>

              <Box
                justify="center"
                align="center"
                direction="row"
                gap="4px"
                style={{
                  backgroundColor: "#F8F38F",
                  borderRadius: "10px",
                  // margin: "3px",
                  padding: "0px 4px",
                }}
              >
                <Avatar src={Clock} size="12px" />
                <StyledText
                  size="13px"
                  text={timeHandle2(expectedTime)}
                  weight="bold"
                />
              </Box>
              <Box
                justify="center"
                align="center"
                direction="row"
                gap="4px"
                style={{
                  backgroundColor: "#F4D4D4",
                  borderRadius: "10px",
                  // margin: "3px",
                  padding: "0px 4px",
                }}
              >
                <Avatar src={Mark} size="12px" />
                <StyledText
                  size="13px"
                  text={start.split(" ")[0]}
                  weight="bold"
                />
              </Box>
              {/* 
              <StyledText
                text={start.split(" ")[0]}
                style={{
                  backgroundColor: "#F4D4D4",
                  borderRadius: "10px",
                  fontSize: "12px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                }}
                weight="bold"
              /> */}
            </Box>
          </Box>
          {/* 사진 */}
        </Box>
        {/* 태그 */}
        <Box direction="row" gap="small">
          {tags &&
            tags.map((t, idx) => {
              return (
                <StyledText text={`#${t.tagName} `} key={t.tagId} size="11px" />
              );
            })}
        </Box>
      </Box>
    );
};
