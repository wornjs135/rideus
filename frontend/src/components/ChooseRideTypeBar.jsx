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
import { createGroupRoom, startRidding } from "../utils/api/rideApi";
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
          onClick={() => {
            navigate("/courseList");
          }}
          children={
            <Box width="145px" align="center" style={{ borderRadius: "8px" }}>
              <img src={CourseButton} width="100px" />
              <StyledText
                text="추천 코스"
                color="black"
                weight="bold"
                size="18px"
              />
            </Box>
          }
        />
        <Button
          onClick={() => {
            // 탑승
            // navigate("/ride", {
            //   state: {
            //     courseName: "나만의 길",
            //     rideType: "single",
            //     courseType: "my",
            //     // recordId: response.data.recordId,
            //     coordinates: undefined,
            //     checkPoints: undefined,
            //     courseId: undefined,
            //     roomInfo: undefined,
            //   },
            // });
            startRidding(
              (response) => {
                console.log(response);
                navigate("/ride", {
                  state: {
                    courseName: "나만의 길",
                    rideType: "single",
                    courseType: "my",
                    recordId: response.data.recordId,
                    coordinates: undefined,
                    checkPoints: undefined,
                    courseId: undefined,
                    roomInfo: undefined,
                  },
                });
              },
              (fail) => {
                console.log(fail);
              }
            );
          }}
          children={
            <Box width="145px" align="center" style={{ borderRadius: "8px" }}>
              <img src={RideButton} width="100px" />
              <StyledText
                text="나만의 코스"
                color="black"
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

export const ChooseSoloGroupBar = ({
  open,
  onDismiss,
  title,
  coordinates,
  checkPoints,
  courseId,
}) => {
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
          onClick={() => {
            startRidding(
              (response) => {
                console.log(response);
                navigate("/ride", {
                  state: {
                    courseName: title,
                    rideType: "single",
                    courseType: "course",
                    recordId: response.data.recordId,
                    coordinates: coordinates,
                    checkPoints: checkPoints,
                    courseId: courseId,
                    roomInfo: undefined,
                  },
                });
              },
              (fail) => {
                console.log(fail);
              }
            );
            // navigate("/ride", {
            //   state: {
            //     courseName: title,
            //     rideType: "single",
            //     courseType: "course",
            //     coordinates: coordinates,
            //     checkPoints: checkPoints,
            //     courseId: courseId,
            //   },
            // });
          }}
          children={
            <Box
              width="145px"
              height="140px"
              align="center"
              justify="between"
              style={{ borderRadius: "8px" }}
              pad="small"
            >
              <img src={SoloBtn} width="100px" />
              <StyledText
                text="혼자 타기"
                color="black"
                weight="bold"
                size="18px"
              />
            </Box>
          }
        />
        <GBtn
          onClick={() => {
            createGroupRoom(
              courseId,
              (response) => {
                console.log(response);
                const roomInfo = {
                  courseId: response.data.courseId,
                  nickname: response.data.nickname,
                  rideRoomId: response.data.rideRoomId,
                };
                startRidding(
                  (response2) => {
                    console.log(response2);
                    navigate("/ride", {
                      state: {
                        courseName: title,
                        rideType: "group",
                        courseType: "course",
                        recordId: response2.data.recordId,
                        coordinates: coordinates,
                        checkPoints: checkPoints,
                        courseId: courseId,
                        roomInfo: roomInfo,
                      },
                    });
                  },
                  (fail) => {
                    console.log(fail);
                  }
                );
              },
              (fail) => {
                console.log(fail);
              }
            );
            // startRidding(
            //   (response) => {
            //     console.log(response);
            //     navigate("/ride", {
            //       state: {
            //         courseName: title,
            //         rideType: "group",
            //         courseType: "course",
            //         coordinates: coordinates,
            //         checkPoints: checkPoints,
            //         courseId: courseId,
            //       },
            //     });
            //   },
            //   (fail) => {
            //     console.log(fail);
            //   }
            // );

            // navigate("/ride", {
            //   state: {
            //     courseName: title,
            //     rideType: "group",
            //     courseType: "course",
            //     coordinates: coordinates,
            //     checkPoints: checkPoints,
            //     courseId: courseId,
            //   },
            // });
          }}
          children={
            <Box
              width="145px"
              align="center"
              height="140px"
              justify="between"
              style={{ borderRadius: "8px" }}
              pad="small"
            >
              <img src={GroupBtn} width="100px" />
              <StyledText
                text="같이 타기"
                color="black"
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
