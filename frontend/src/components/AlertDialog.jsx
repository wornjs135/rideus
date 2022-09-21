import React from "react";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Button from "./Button";
import { Box } from "grommet";
import styled from "styled-components";
import { StyledText } from "./Common";
import WeatherBtn from "../assets/images/weather.png";
import { StyledHorizonTable } from "./HorizontalScrollBox";

export const AlertDialog = ({
  open,
  handleClose,
  handleAction,
  title,
  desc,
  cancel,
  accept,
}) => {
  return (
    <Dialog
      open={open}
      onClose={handleClose}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
    >
      <DialogTitle id="alert-dialog-title">{title}</DialogTitle>
      <DialogContent>
        <DialogContentText id="alert-dialog-description">
          {desc}
        </DialogContentText>
      </DialogContent>

      <Box direction="row" justify="center">
        <Button SmallWhite onClick={handleClose}>
          {cancel}
        </Button>
        {accept && (
          <Button SmallPink onClick={handleAction} autoFocus>
            {accept}
          </Button>
        )}
      </Box>
    </Dialog>
  );
};

export const RideDialog = ({
  open,
  handleClose,
  handleAction,
  title,
  desc,
  cancel,
  accept,
}) => {
  return (
    <Dialog
      open={open}
      onClose={handleClose}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
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
    </Dialog>
  );
};

const BottomBtn = styled.div`
  position: absolute;
  width: 100%;
  z-index: 5;
  bottom: 0;
  display: flex;
  justify-content: space-around;
`;

const TopBanner = styled.div`
  position: absolute;
  width: 100%;
  z-index: 5;
  top: 0;
  display: flex;
  margin-top: 15px;
`;

export const MapDialog = ({
  open,
  handleClose,
  handleAction,
  title,
  map,
  cancel,
  accept,
}) => {
  return (
    <Dialog fullScreen open={open} onClose={handleClose}>
      <Box width="100vw" height="100vh" align="center">
        {map}
        {/* 상단 바 */}
        <Box
          align="center"
          style={{
            position: "absolute",
            width: "100%",
            zIndex: "5",
            top: "0",
            display: "flex",
            marginTop: "15px",
          }}
        >
          <Box width="100%" align="center">
            {/* 코스 이름 */}
            <Box
              round={{ size: "small" }}
              width="90%"
              height="43px"
              background={"rgb(67, 150, 82)"}
              align="center"
              justify="center"
            >
              <StyledText text={title} color="white" size="20px" />
            </Box>
            {/* 주변 정보 버튼 */}
            <Box direction="row" justify="start" overflow="scroll">
              <StyledHorizonTable>
                <Button InfoSelect children="전체" />
                <Button Info children="관광명소" />
                <Button Info children="음식점" />
                <Button Info children="카페" />
                <Button Info children="편의점" />
                <Button Info children="화장실" />
                <Button Info children="문화시설" />
                <Button Info children="자전거수리" />
              </StyledHorizonTable>
            </Box>
            <img width="50px" src={WeatherBtn} />
          </Box>
        </Box>
        {/* 하단 버튼 */}
        <BottomBtn>
          <Button MediumPink onClick={handleClose}>
            {cancel}
          </Button>
          {accept && (
            <Button MediumWhite onClick={handleAction} autoFocus>
              {accept}
            </Button>
          )}
        </BottomBtn>
      </Box>
    </Dialog>
  );
};
