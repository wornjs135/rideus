import { Avatar, Box, Spinner } from "grommet";
import React, { useState } from "react";
import { useEffect } from "react";
import { StyledText } from "./Common";
import Bookmark from "../assets/images/bookmark.png";
import NoImage from "../assets/images/noimage.jpg";
import BookmarkBlank from "../assets/images/bookmark_blank.png";
import { useNavigate } from "react-router-dom";
import { expectTimeHandle, TimeBox, timeHandle2 } from "../utils/util";
import Clock from "../assets/icons/clock.svg";
import Flag from "../assets/icons/flag.svg";
import Mark from "../assets/icons/mark.svg";
import { motion } from "framer-motion";
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
      <motion.div
        onClick={() => {
          navigate(`/courseDetail/${courseId}`);
        }}
        whileTap={{ scale: 1.2 }}
        style={{
          borderRadius: "10px",
          boxShadow: "4px 4px 4px -4px rgb(0 0 0 / 0.2)",
          width: "85%",
          height: "15%",
          padding: "10px 6px",
          background: "white",
        }}
      >
        {/* 코스 박스 */}
        <Box width="100%" align="center">
          {/* 제목, 북마크 버튼 */}
          <Box
            direction="row"
            justify="between"
            width="100%"
            align="center"
            pad={{ left: "15px", right: "15px" }}
          >
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
            <Box direction="row" align="center">
              <img
                src={bm ? Bookmark : BookmarkBlank}
                width="18px"
                height="20px"
              />
              {likeCount}
            </Box>
          </Box>
          {/* 코스 데이터, 사진 */}
          <Box
            direction="row"
            width="100%"
            justify="between"
            align="center"
            pad={{ left: "15px", right: "15px" }}
          >
            <Box style={{ marginRight: "25px" }}>
              <img
                src={imageUrl ? imageUrl : NoImage}
                width="156px"
                height="84px"
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
                  padding: "1px 5px",
                  // margin: "3px",
                }}
              >
                <Avatar src={Flag} size="13px" />
                <Box align="end" direction="row">
                  <StyledText text={distance} weight="bold" size="17px" />
                  <StyledText size="13px" text={"km"} weight="bold" />
                </Box>
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
                  padding: "1px 5px",
                }}
              >
                <Avatar src={Clock} size="13px" />
                <TimeBox time={expectedTime} />
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
                  padding: "2px 5px",
                }}
              >
                <Avatar src={Mark} size="13px" />
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
        <Box
          direction="row"
          gap="small"
          justify="center"
          margin={{ top: "5px" }}
        >
          {tags &&
            tags.map((t, idx) => {
              return (
                <StyledText
                  text={`#${t.tagName} `}
                  key={t.tagId}
                  size="11px"
                  color="black"
                  weight="bold"
                />
              );
            })}
        </Box>
      </motion.div>
    );
};
