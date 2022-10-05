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

import { useNavigate } from "react-router-dom";
import { getAllCourse, getRecommendationCourses } from "../utils/api/courseApi";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { categorys } from "../utils/util";

export const theme = createTheme({
  status: {
    danger: "#e53e3e",
  },
  palette: {
    deactive: {
      main: "#fff",
      contrastText: "#000000",
    },
    active: {
      main: "#64CCBE",
      contrastText: "#fff",
    },
    전체: {
      main: "#64CCBE",
      contrastText: "#000",
    },
    관광명소: {
      main: "#D6FFAD",
      contrastText: "#000",
    },
    음식점: {
      main: "#C0E7EC",
      contrastText: "#000",
    },
    카페: {
      main: "#FFC95F",
      contrastText: "#000",
    },
    편의점: {
      main: "#FFFA85",
      contrastText: "#000",
    },
    자전거정비: {
      main: "#FF9999",
      contrastText: "#000",
    },
    문화시설: {
      main: "#6499FF",
      contrastText: "#000",
    },
    공중화장실: {
      main: "#B7B7B7",
      contrastText: "#000",
    },
    group: {
      main: "#64CCBE",
      contrastText: "#000",
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
  const [recCourses, setRecCourses] = useState([]);
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

          setLoading(false);
        },
        (fail) => {
          console.log(fail);
        }
      );
      getRecommendationCourses(
        (response) => {
          console.log(response);
          setRecCourses((prev) => (prev = response.data));
          setLoading(false);
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
      <Box align="center" width="100%">
        {/* 추천코스, 검색 버튼 */}
        <Box
          width="100%"
          align="center"
          margin={{
            top: "10px",
            bottom: "25px",
          }}
          gap="25px"
        >
          <StyledText text="추천 코스" weight="bold" size="24px" />
          <Box direction="row" width="100%" align="center" justify="center">
            <Paper
              component="form"
              sx={{
                p: "2px 4px",
                display: "flex",
                alignItems: "center",
                width: "90%",
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
        <Box
          width="100%"
          align="center"
          margin={{ top: "15px", bottom: "15px" }}
        >
          <Box direction="row" justify="start" overflow="scroll" height="70px">
            <ThemeProvider theme={theme}>
              <StyledHorizonTable>
                {categorys.map((cat, idx) => {
                  return (
                    <Button
                      key={idx}
                      variant="contained"
                      color={selected === idx ? "active" : "deactive"}
                      onClick={() => {
                        setSelected(idx);
                      }}
                      style={{
                        fontWeight: "bold",
                        width: "55px",
                        height: "55px",
                        borderRadius: "10px",
                        marginRight: "10px",
                      }}
                    >
                      <Box align="center">
                        {cat.icon}
                        {cat.name}
                      </Box>
                    </Button>
                  );
                })}
              </StyledHorizonTable>
            </ThemeProvider>
          </Box>
        </Box>
        {/* 리스트 */}
        <Box
          align="center"
          round={{ corner: "top", size: "large" }}
          background="#E0F7F4"
          gap="15px"
          height={{ min: "80vh" }}
          width="100%"
          pad={{ top: "25px" }}
        >
          {selected === 1 ? (
            recCourses ? (
              recCourses.filter((course) => {
                if (searchTerm === "") {
                  return course;
                } else if (course.courseName.includes(searchTerm)) {
                  return course;
                }
              }).length > 0 ? (
                recCourses
                  .filter((course) => {
                    if (searchTerm === "") {
                      return course;
                    } else if (course.courseName.includes(searchTerm)) {
                      return course;
                    }
                  })
                  .map((course, idx) => {
                    return (
                      <CourseBox loading={loading} course={course} key={idx} />
                    );
                  })
              ) : (
                <Box margin={{ top: "30px" }}>
                  {loading ? (
                    <Spinner />
                  ) : (
                    <StyledText
                      text="해당하는 코스가 없습니다!"
                      size="16px"
                      weight="bold"
                    />
                  )}
                </Box>
              )
            ) : (
              <Spinner />
            )
          ) : courses ? (
            courses.filter((course) => {
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
                  course.category.includes(categorys[selected].name)
                ) {
                  return course;
                } else if (
                  course.courseName.includes(searchTerm) &&
                  course.category !== null &&
                  course.category.includes(categorys[selected].name)
                ) {
                  return course;
                }
              }
            }).length > 0 ? (
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
                      course.category.includes(categorys[selected].name)
                    ) {
                      return course;
                    } else if (
                      course.courseName.includes(searchTerm) &&
                      course.category !== null &&
                      course.category.includes(categorys[selected].name)
                    ) {
                      return course;
                    }
                  }
                })
                .map((course, idx) => {
                  return (
                    <CourseBox loading={loading} course={course} key={idx} />
                  );
                })
            ) : (
              <Box margin={{ top: "30px" }}>
                {loading ? (
                  <Spinner />
                ) : (
                  <StyledText
                    text="해당하는 코스가 없습니다!"
                    size="16px"
                    weight="bold"
                  />
                )}
              </Box>
            )
          ) : (
            <Spinner />
          )}
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
