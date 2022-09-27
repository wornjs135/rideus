import { Button } from "@mui/material";
import { Box } from "grommet";
import React, { useState } from "react";
import { useEffect } from "react";
import { StyledText } from "../components/Common";
import { CourseBox } from "../components/CourseBox";
import { StyledHorizonTable } from "../components/HorizontalScrollBox";
import Paper from "@mui/material/Paper";
import InputBase from "@mui/material/InputBase";
import Divider from "@mui/material/Divider";
import IconButton from "@mui/material/IconButton";
import SearchIcon from "@mui/icons-material/Search";
import DensitySmallIcon from "@mui/icons-material/DensitySmall";

// 코스 구조
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

export const CourseList = () => {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selected, setSelected] = useState(0);
  useEffect(() => {
    if (loading) {
      //API 호출
      setLoading(false);
    }
  }, []);
  return (
    <Box>
      {/* 추천코스, 검색 버튼 */}
      <Box align="center">
        <StyledText text="추천 코스" weight="bold" size="24px" />
        <Box direction="row">
          <Paper
            component="form"
            sx={{
              p: "2px 4px",
              display: "flex",
              alignItems: "center",
              width: 300,
            }}
          >
            <InputBase
              sx={{ ml: 1, flex: 1 }}
              placeholder="코스 검색"
              inputProps={{ "aria-label": "코스 검색" }}
            />
            <IconButton type="button" sx={{ p: "10px" }} aria-label="search">
              <SearchIcon />
            </IconButton>
          </Paper>
        </Box>
      </Box>
      {/* 카테고리 버튼 */}
      <Box width="100%" align="center">
        <Box direction="row" justify="start" overflow="scroll">
          <StyledHorizonTable>
            <IconButton type="button">
              <DensitySmallIcon />
              전체
            </IconButton>
            <Button>추천</Button>
            <Button>관광명소</Button>
            <Button>음식점</Button>
            <Button>카페</Button>
            <Button>편의점</Button>
            <Button>화장실</Button>
            <Button>문화시설</Button>
            <Button>자전거수리</Button>
          </StyledHorizonTable>
        </Box>
      </Box>
      {/* 리스트 */}
      <Box>
        {courses.map((course, idx) => {
          return <CourseBox course={course} key={idx} />;
        })}
      </Box>
    </Box>
  );
};
