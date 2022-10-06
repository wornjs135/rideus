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
import Recommend from "../assets/images/recommend.png";
import All from "../assets/images/all.png";
import Meal from "../assets/images/meal.png";
import Tour from "../assets/images/tour.png";
import Cafe from "../assets/images/cafe.png";
import Repair from "../assets/images/repair.png";
import Toilets from "../assets/images/toilets.png";
import Art from "../assets/images/art.png";
import Cvs from "../assets/images/cvs.png";
import Rec from "../assets/images/rec.png";
import { useNavigate } from "react-router-dom";
import { getAllCourse, getRecommendationCourses } from "../utils/api/courseApi";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { categorys } from "../utils/util";
import { motion } from "framer-motion";
import { container } from "./Main";

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

export const RecommendMentBox = ({ selected }) => {
  const imgSize = "24px";
  switch (selected) {
    case 0:
      return (
        <Box
          direction="row"
          margin={{ top: "15px", left: "10px" }}
          width="90%"
          gap="small"
        >
          <img src={All} width={imgSize} height={imgSize} />
          <StyledText
            text="RideUs가 준비한 알찬 코스"
            size="16px"
            style={{
              fontFamily: "gwtt",
            }}
          />
        </Box>
      );
    case 1:
      return (
        <Box direction="row" margin={{ top: "15px" }} width="90%" gap="small">
          <img src={Recommend} width={imgSize} height={imgSize} />
          <StyledText
            text="오직, 당신만을 위한"
            size="16px"
            style={{
              fontFamily: "gwtt",
            }}
          />
        </Box>
      );
    case 2:
      return (
        <Box direction="row" margin={{ top: "15px" }} width="90%" gap="small">
          <img src={Tour} width={imgSize} height={imgSize} />
          <StyledText
            text="관광명소가 얼마나 가까울까?"
            size="16px"
            style={{
              fontFamily: "gwtt",
            }}
          />
        </Box>
      );
    case 3:
      return (
        <Box direction="row" margin={{ top: "15px" }} width="90%" gap="small">
          <img src={Meal} width={imgSize} height={imgSize} />
          <StyledText
            text="라이딩도 식후경"
            size="16px"
            style={{
              fontFamily: "gwtt",
            }}
          />
        </Box>
      );
    case 4:
      return (
        <Box direction="row" margin={{ top: "15px" }} width="90%" gap="small">
          <img src={Cafe} width={imgSize} height={imgSize} />
          <StyledText
            text="커피 한 잔 할래용~♬"
            size="16px"
            style={{
              fontFamily: "gwtt",
            }}
          />
        </Box>
      );
    case 5:
      return (
        <Box direction="row" margin={{ top: "15px" }} width="90%" gap="small">
          <img src={Cvs} width={imgSize} height={imgSize} />
          <StyledText
            text="아, 생수! 아, 휴지!"
            size="16px"
            style={{
              fontFamily: "gwtt",
            }}
          />
        </Box>
      );
    case 6:
      return (
        <Box direction="row" margin={{ top: "15px" }} width="90%" gap="small">
          <img src={Repair} width={imgSize} height={imgSize} />
          <StyledText
            text="장비를 정지..합니...다.."
            size="16px"
            style={{
              fontFamily: "gwtt",
            }}
          />
        </Box>
      );
    case 7:
      return (
        <Box direction="row" margin={{ top: "15px" }} width="90%" gap="small">
          <img src={Art} width={imgSize} height={imgSize} />
          <StyledText
            text="고고한 당신을 초대합니다"
            size="16px"
            style={{
              fontFamily: "gwtt",
            }}
          />
        </Box>
      );
    case 8:
      return (
        <Box direction="row" margin={{ top: "15px" }} width="90%" gap="small">
          <img src={Toilets} width={imgSize} height={imgSize} />
          <StyledText
            text="한 순간도 방심해선 안된다"
            size="16px"
            style={{
              fontFamily: "gwtt",
            }}
          />
        </Box>
      );
  }
};

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
      <motion.div
        style={{
          align: "center",
          width: "100%",
        }}
        initial="hidden"
        animate="visible"
        variants={container}
      >
        {/* 추천코스, 검색 버튼 */}
        <Box
          width="100%"
          align="center"
          margin={{
            top: "25px",
            bottom: "10px",
          }}
          gap="25px"
        >
          <Box direction="row" gap="medium">
            <img src={Rec} width="30px" height="30px" />
            <StyledText
              text="추천 코스"
              weight="bold"
              size="24px"
              style={{
                fontFamily: "gwtt",
              }}
            />
          </Box>
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
                style={{
                  fontFamily: "gwmd",
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
          margin={{ bottom: "15px" }}
          justify="center"
        >
          <Box direction="row" justify="start" overflow="scroll" height="85px">
            <StyledHorizonTable>
              {categorys.map((cat, idx) => {
                return (
                  <motion.button
                    key={idx}
                    onClick={() => {
                      setSelected(idx);
                    }}
                    whileTap={{ scale: 1.2 }}
                    style={{
                      boxShadow: "none",
                      textTransform: "none",
                      fontSize: 13,
                      padding: "6px 12px",
                      color: selected === idx ? "white" : "black",
                      border: "none",
                      backgroundColor: selected === idx ? "#64CCBE" : "#E0F7F4",
                      fontWeight: "bold",
                      width: "65px",
                      height: "65px",
                      borderRadius: "10px",
                      marginRight: "10px",
                    }}
                  >
                    <Box
                      align="center"
                      style={{
                        fontFamily: "gwmd",
                      }}
                    >
                      {cat.icon}
                      {cat.name}
                    </Box>
                  </motion.button>
                );
              })}
            </StyledHorizonTable>
          </Box>
        </Box>
        {/* 리스트 */}
        <Box
          align="center"
          round={{ corner: "top", size: "large" }}
          background="#E0F7F4"
          gap="25px"
          height={{ min: "80vh" }}
          width="100%"
        >
          <RecommendMentBox selected={selected} />
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
      </motion.div>
    );
};
