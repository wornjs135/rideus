import React from "react";
import { Box, Spinner } from "grommet";
import bike1 from "../assets/images/bicycle.png";
import bike2 from "../assets/images/bicycle2.png";
// import Button from "../components/Button";
// import { Button, styled } from "@material-ui/core";
import { StyledText } from "../components/Common";
// import styled from "styled-components";
import { StyledHorizonTable } from "../components/HorizontalScrollBox";
import { BestCourse, NewsBox } from "../components/CourseComponent";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { ChooseRideTypeBar } from "../components/ChooseRideTypeBar";
import { styled } from "@mui/material/styles";
import { Button } from "@mui/material";
import { BootstrapButton } from "../components/Buttons";
import { useEffect } from "react";
import { getNews } from "../utils/api/newsApi";
import { useSelector } from "react-redux";
import {
  getPopularCourses,
  getRecommendationCourseByLocation,
} from "../utils/api/mainApi";
import { useGeolocated } from "react-geolocated";

// 코스 구조
// [
//   {
//     "courseId": "string",
//     "courseName": "string",
//     "distance": 0,
//     "expectedTime": 0,
//     "finish": "string",
//     "likeCount": 0,
//     "start": "string",
//     "tags": [
//       {
//         "tagId": 0,
//         "tagName": "string"
//       }
//     ]
//   }
// ]

export const Main = () => {
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [newses, setNewses] = useState([]);
  const [popularCourses, setPopularCourses] = useState([]);
  const [nearCourses, setNearCourses] = useState([]);
  const [loc, setLoc] = useState({
    center: {
      lat: 33.450701,
      lng: 126.570667,
    },
    errMsg: null,
    isLoading: true,
  });
  const { coords, isGeolocationAvailable, isGeolocationEnabled } =
    useGeolocated({});
  const onDismiss = () => {
    setOpen(false);
  };
  const [loading, setLoading] = useState(true);
  const User = useSelector((state) => state.user.user.user);
  // console.log(User);
  useEffect(() => {
    if (User === undefined) {
      navigate("/login");
    } else {
    if (loading) {
      getNews(
        (response) => {
          console.log(response);
          setNewses(response.data);
        },
        (fail) => {
          console.log(fail);
          setLoading(false);
        }
      );
      getPopularCourses(
        (response) => {
          console.log(response);
          setPopularCourses(response.data);
        },
        (fail) => {
          console.log(fail);
          setLoading(false);
        }
      );
      navigator.geolocation.getCurrentPosition(
        (position) => {
          console.log(position);
          setLoc((prev) => ({
            ...prev,
            center: {
              lat: position.coords.latitude, // 위도
              lng: position.coords.longitude, // 경도
            },
          }));

          getRecommendationCourseByLocation(
            {
              lat: position.coords.latitude,
              lng: position.coords.longitude,
            },
            (response) => {
              console.log(response);
              setNearCourses(response.data);
              setLoading(false);
            },
            (fail) => {
              console.log(fail);
              setLoading(false);
            }
          );
        },
        (err) => {
          setLoc((prev) => ({
            ...prev,
            errMsg: err.message,
          }));
          setLoading(false);
        }
      );
      // if (isGeolocationAvailable && isGeolocationEnabled) {
      //   const gps = {
      //     lat: coords.latitude,
      //     lng: coords.longitude,
      //   };
      //   console.log(gps);
      //   // getRecommendationCourseByLocation(
      //   //   {
      //   //     lat: coords.latitude,
      //   //     lng: coords.longitude,
      //   //   },
      //   //   (response) => {
      //   //     console.log(response);
      //   //   },
      //   //   (fail) => {
      //   //     console.log(fail);
      //   //     setLoading(false);
      //   //   }
      //   // );
      // }

      setLoading(false);
      }
    }
  }, []);
  if (loading) return <Spinner />;
  else
    return (
      <Box width="100vw" justify="center" gap="small">
        {/* 자전거사진, 멘트, 버튼 */}
        <Box align="center" justify="between">
          <Box direction="row" align="center">
            <img src={bike1} />
            <img src={bike2} />
          </Box>
          <StyledText
            color="#4B4B4B"
            text="즐거운 자전거 여행, 달려볼까요?"
            weight="bold"
            size="18px"
          />
          <BootstrapButton
            onClick={() => {
              // if (User === undefined) {
              //   alert("로그인 하세요!");
              //   navigate("/login");
              // } else {

              // naviagate("/ride", { state: { courseName: "나만의 길" } });
              setOpen(true);
              // }
            }}
          >
            RIDE!
          </BootstrapButton>
          {/* <Button
          variant="contained"
          size="large"
          onClick={() => {
            navigate("/gpsTest");
          }}
        >
          label="GPS TEST"
        </Button> */}
        </Box>
        {/* 인기코스, 월간코스, 인기태그 */}
        <Box
          align="center"
          justify="between"
          round
          background="#F3F3F3"
          border={{ color: "#F3F3F3", size: "small", side: "all" }}
        >
          {/* 인기코스 */}
          <Box align="start" width="100%" gap="large" margin="large">
            <Box pad={{ left: "20px" }}>
              <StyledText text="인기 코스" weight="bold" size="18px" />
            </Box>
            {/* 인기코스 리스트 */}
            <Box direction="row" overflow="scroll">
              <StyledHorizonTable>
                {popularCourses.length > 0
                  ? popularCourses.map((course, idx) => {
                      return (
                        <div className="card" key={idx}>
                          <BestCourse course={course} />
                        </div>
                      );
                    })
                  : "코스가 없습니다!"}
              </StyledHorizonTable>
            </Box>
          </Box>
          {/*  추천 코스 */}
          <Box align="start" width="100%" gap="large" margin="large">
            <Box pad={{ left: "20px" }}>
              <StyledText text="주변 코스" weight="bold" size="18px" />
            </Box>
            {/* 인기코스 리스트 */}
            <Box direction="row" overflow="scroll">
              <StyledHorizonTable>
                {nearCourses.length > 0
                  ? nearCourses.map((course, idx) => {
                      return (
                        <div className="card" key={idx}>
                          <BestCourse course={course} />
                        </div>
                      );
                    })
                  : "코스가 없습니다!"}
              </StyledHorizonTable>
            </Box>
          </Box>
          {/* 월간자전거 */}
          <Box align="start" width="100%" gap="large" margin="large">
            <Box pad={{ left: "20px" }}>
              <StyledText text="월간 자전거" weight="bold" size="18px" />
            </Box>
            {/* 월간자전거 리스트 */}
            <Box direction="row" overflow="scroll">
              <StyledHorizonTable>
                {newses &&
                  newses.map((news, idx) => {
                    return (
                      <div className="card" key={idx}>
                        <NewsBox news={news} />
                      </div>
                    );
                  })}
              </StyledHorizonTable>
            </Box>
          </Box>
          {/* 인기태그 */}
          <Box align="start" width="100%" gap="large" margin="large">
            <Box pad={{ left: "20px" }}>
              <StyledText text="인기 태그" weight="bold" size="18px" />
            </Box>
            {/* 인기태그 리스트 */}
            <Box direction="row" overflow="scroll"></Box>
          </Box>
        </Box>
        <ChooseRideTypeBar open={open} onDismiss={onDismiss} />
      </Box>
    );
};
