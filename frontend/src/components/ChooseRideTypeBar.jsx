import React from "react";
import { BottomSheet } from "react-spring-bottom-sheet";
import styled from "styled-components";
import { StyledText } from "./Common";
import CloseButton from "../assets/images/close.png";
import CourseButton from "../assets/images/recCourse.png";
import RideButton from "../assets/images/myride.png";
import { useNavigate } from "react-router-dom";
import { Box, Button } from "grommet";
import { Button as GBtn } from "grommet";
import SoloBtn from "../assets/images/solo.png";
import GroupBtn from "../assets/images/group.png";
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
    <BottomSheet
      open={open}
      onDismiss={() => {
        onDismiss();
      }}
    >
      <HeaderBox
        title="RIDE!"
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
            navigate("/courseList");
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
            navigate("/ride", {
              state: {
                courseName: "나만의 길",
                rideType: "solo",
                courseType: "my",
              },
            });
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

export const ChooseSoloGroupBar = ({ open, onDismiss, title }) => {
  const navigate = useNavigate();
  return (
    <BottomSheet
      open={open}
      onDismiss={() => {
        onDismiss();
      }}
    >
      <HeaderBox
        title="RIDE!"
        goBack={() => {
          onDismiss();
        }}
      />
      <Box direction="row" justify="center" width="100%" pad="15px" gap="small">
        <GBtn
          color="#439652"
          onClick={() => {
            navigate("/ride", {
              state: {
                courseName: title,
                rideType: "solo",
                courseType: "course",
              },
            });
          }}
          children={
            <Box
              width="145px"
              height="140px"
              align="center"
              justify="between"
              background="#439652"
              style={{ borderRadius: "8px" }}
              pad="small"
            >
              <img src={SoloBtn} width="100px" />
              <StyledText
                text="혼자 타기"
                color="white"
                weight="bold"
                size="18px"
              />
            </Box>
          }
        />
        <GBtn
          onClick={() => {
            navigate("/ride", {
              state: {
                courseName: title,
                rideType: "group",
                courseType: "course",
              },
            });
          }}
          children={
            <Box
              width="145px"
              align="center"
              height="140px"
              justify="between"
              background="#439652"
              style={{ borderRadius: "8px" }}
              pad="small"
            >
              <img src={GroupBtn} width="100px" />
              <StyledText
                text="같이 타기"
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
