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

export const Main = () => {
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [newses, setNewses] = useState([]);
  const onDismiss = () => {
    setOpen(false);
  };
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (loading) {
      getNews(
        (response) => {
          console.log(response);
          setNewses(response.data);
          setLoading(false);
        },
        (fail) => {
          console.log(fail);
          setLoading(false);
        }
      );
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
              // naviagate("/ride", { state: { courseName: "나만의 길" } });
              setOpen(true);
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
                <div className="card">
                  <BestCourse />
                </div>
                <div className="card">
                  <BestCourse />
                </div>
                <div className="card">
                  <BestCourse />
                </div>
                <div className="card">
                  <BestCourse />
                </div>
                <div className="card">
                  <BestCourse />
                </div>
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
