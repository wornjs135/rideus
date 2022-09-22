import { Box } from "grommet";
import React from "react";
import Profile from "../assets/images/profile.png";
import MoreBtn from "../assets/images/more.png";
import { StyledText } from "./Common";
export const ReviewBox = () => {
  return (
    <Box
      direction="row"
      justify="between"
      align="center"
      background="white"
      round="medium"
      margin={{ top: "10px" }}
      pad="small"
    >
      <Box direction="row" justify="start" align="start" gap="small">
        <img src={Profile} width="30px" />
        <Box justify="center" align="start">
          <StyledText text="배인수" />
          <StyledText text="오르막길 오르다 죽을 뻔..." />
          <Box direction="row">
            <StyledText text="#완만" />
            <StyledText text="#가파름" />
            <StyledText text="#시원함" />
          </Box>
          <StyledText text="♥️ x 123️" />
        </Box>
      </Box>
      <img src={MoreBtn} style={{ marginRight: "9px" }} />
    </Box>
  );
};
