import { Button } from "@mui/material";
import { Box, Spinner } from "grommet";
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
import RecommendIcon from "@mui/icons-material/Recommend";
import { useNavigate } from "react-router-dom";
import { getAllCourse } from "../utils/api/courseApi";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { categorys } from "../utils/util";

const theme = createTheme({
  status: {
    danger: "#e53e3e",
  },
  palette: {
    deactive: {
      main: "#000000",
      contrastText: "#053e85",
    },
    active: {
      main: "#439652",
      contrastText: "#fff",
    },
  },
});
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
  const [searchTerm, setSearchTerm] = useState("");
  const navigate = useNavigate();
  useEffect(() => {
    if (loading) {
      //API 호출
      getAllCourse(
        (response) => {
          console.log(response);
          setCourses((prev) => (prev = response.data));
        },
        (fail) => {
          console.log(fail);
        }
      );
      setLoading(false);
    }
  }, []);
  if (loading) return <Spinner />;
  else
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
                onChange={(event) => {
                  setSearchTerm(event.target.value);
                }}
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
            <ThemeProvider theme={theme}>
              <StyledHorizonTable>
                <IconButton
                  type="button"
                  color={selected === 0 ? "active" : "deactive"}
                  onClick={() => {
                    setSelected(0);
                  }}
                >
                  <DensitySmallIcon />
                  전체
                </IconButton>
                <IconButton
                  type="button"
                  color={selected === 1 ? "active" : "deactive"}
                  onClick={() => {
                    setSelected(1);
                  }}
                >
                  <RecommendIcon />
                  추천
                </IconButton>
                <IconButton
                  type="button"
                  color={selected === 2 ? "active" : "deactive"}
                  onClick={() => {
                    setSelected(2);
                  }}
                >
                  관광명소
                </IconButton>
                <IconButton
                  type="button"
                  color={selected === 3 ? "active" : "deactive"}
                  onClick={() => {
                    setSelected(3);
                  }}
                >
                  음식점
                </IconButton>
                <IconButton
                  type="button"
                  color={selected === 4 ? "active" : "deactive"}
                  onClick={() => {
                    setSelected(4);
                  }}
                >
                  카페
                </IconButton>
                <IconButton
                  type="button"
                  color={selected === 5 ? "active" : "deactive"}
                  onClick={() => {
                    setSelected(5);
                  }}
                >
                  편의점
                </IconButton>
                <IconButton
                  type="button"
                  color={selected === 6 ? "active" : "deactive"}
                  onClick={() => {
                    setSelected(6);
                  }}
                >
                  화장실
                </IconButton>
                <IconButton
                  type="button"
                  color={selected === 7 ? "active" : "deactive"}
                  onClick={() => {
                    setSelected(7);
                  }}
                >
                  문화시설
                </IconButton>
                <IconButton
                  type="button"
                  color={selected === 8 ? "active" : "deactive"}
                  onClick={() => {
                    setSelected(8);
                  }}
                >
                  자전거수리
                </IconButton>
              </StyledHorizonTable>
            </ThemeProvider>
          </Box>
        </Box>
        {/* 리스트 */}
        <Box>
          {courses &&
            courses
              .filter((course) => {
                if (selected === 0 || selected === 1) {
                  if (searchTerm === "") {
                    return course;
                  } else if (course.courseName.includes(searchTerm)) {
                    return course;
                  }
                } else {
                  if (
                    searchTerm === "" &&
                    course.category !== null &&
                    course.category.includes(categorys[selected])
                  ) {
                    return course;
                  } else if (
                    course.courseName.includes(searchTerm) &&
                    course.category !== null &&
                    course.category.includes(categorys[selected])
                  ) {
                    return course;
                  }
                }
              })
              .map((course, idx) => {
                return (
                  <CourseBox loading={loading} course={course} key={idx} />
                );
              })}
        </Box>
        {/* <Button
          children="코스 상세"
          onClick={() => {
            navigate(`/courseDetail/${18}`, {
              state: { courseName: "마포-정서점" },
            });
          }}
        /> */}
      </Box>
    );
};
