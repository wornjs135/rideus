import React from "react";
import { BottomSheet } from "react-spring-bottom-sheet";
import styled from "styled-components";
import { StyledText } from "./Common";
import CloseButton from "../assets/images/close.png";
import CourseButton from "../assets/images/recCourse.png";
import RideButton from "../assets/images/myride.png";
import { useNavigate } from "react-router-dom";
import { Box, Button } from "grommet";
import { Dialog } from "@mui/material";
import { MdDirectionsBike, MdOutlineMap } from "react-icons/md";
import { GrMapLocation } from "react-icons/gr";
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

export const HeaderBox = ({ goBack, title }) => {
  return (
    <HeaderDiv>
      <div style={{ width: "10vw" }}></div>
      <StyledText size="20px" weight="bold" text={title} />
      <BackButton onClick={goBack}>
        <img src={CloseButton} />
      </BackButton>
    </HeaderDiv>
  );
};
export const ChooseRideTypeBar = ({ open, onDismiss }) => {
  const navigate = useNavigate();
  return (
    <Dialog
      open={open}
      onClose={() => {
        onDismiss();
      }}
    >
      <Box
        direction="row"
        justify="around"
        width="80vw"
        height="15vh"
        pad="small"
        gap="small"
      >
        <Button
          onClick={() => {
            navigate("/course");
          }}
          children={
            <Box width="145px" align="center" style={{ borderRadius: "8px" }}>
              {/* <img src={CourseButton} width="100px" /> */}

              <MdOutlineMap size="35" color="#439652" />
              <StyledText
                text="추천 코스"
                color="#439652"
                weight="bold"
                size="18px"
              />
            </Box>
          }
        />
        <Box style={{ borderLeft: "1px solid black" }} />
        <Button
          onClick={() => {
            navigate("/ride", {
              state: {
                courseName: "나만의 길",
                rideType: "solo",
                courseType: "my",
              },
            });
          }}
          children={
            <Box width="145px" align="center" style={{ borderRadius: "8px" }}>
              {/* <img src={RideButton} width="100px" /> */}
              <MdDirectionsBike size="35" color="#439652" />
              <StyledText
                text="나만의 코스"
                color="#439652"
                weight="bold"
                size="18px"
              />
            </Box>
          }
        />
      </Box>
    </Dialog>
  );
};
