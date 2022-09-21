import React from "react";
import { BottomSheet } from "react-spring-bottom-sheet";
import styled from "styled-components";
import { StyledText } from "./Common";
import CloseButton from "../assets/images/close.png";
import CourseButton from "../assets/images/recCourse.png";
import RideButton from "../assets/images/myride.png";
import { useNavigate } from "react-router-dom";
import { Box, Button } from "grommet";
const HeaderDiv = styled.div`
  margin: 5px;
  display: flex;
  justify-content: space-between;
`;

const BackButton = styled.button`
  background: none;
  font-size: 12px;
  font-family: Noto Sans KR, sans-serif;
  border: 0px;
  width: 10vw;
`;

export const HeaderBox = ({ goBack }) => {
  return (
    <HeaderDiv>
      <div style={{ width: "10vw" }}></div>
      <StyledText size="20px" weight="bold" text="RIDE!" />
      <BackButton onClick={goBack}>
        <img src={CloseButton} />
      </BackButton>
    </HeaderDiv>
  );
};

export const ChooseRideTypeBar = ({ open, onDismiss }) => {
  const navigate = useNavigate();
  return (
    <BottomSheet
      open={open}
      onDismiss={() => {
        onDismiss();
      }}
    >
      <HeaderBox
        goBack={() => {
          onDismiss();
        }}
      />
      <Box
        direction="row"
        justify="around"
        width="100%"
        pad="15px"
        margin={{ top: "20px", bottom: "50px" }}
      >
        <Button
          color="#439652"
          onClick={() => {
            navigate("/course");
          }}
          children={
            <Box
              width="145px"
              align="center"
              background="#439652"
              style={{ borderRadius: "8px" }}
            >
              <img src={CourseButton} width="100px" />
              <StyledText
                text="추천 코스"
                color="white"
                weight="bold"
                size="18px"
              />
            </Box>
          }
        />
        <Button
          onClick={() => {
            navigate("/ride", { state: { courseName: "나만의 길" } });
          }}
          children={
            <Box
              width="145px"
              align="center"
              background="#439652"
              style={{ borderRadius: "8px" }}
            >
              <img src={RideButton} width="100px" />
              <StyledText
                text="나만의 코스"
                color="white"
                weight="bold"
                size="18px"
              />
            </Box>
          }
        />
      </Box>
    </BottomSheet>
  );
};
