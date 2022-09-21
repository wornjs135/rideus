import { Avatar, Box } from "grommet";
import React from "react";
import { StyledText } from "./Common";
import Profile from "../assets/images/profile.png";
export const RankBox = ({ record }) => {
  return (
    <Box direction="row" justify="around">
      <Box direction="row" justify="center">
        <StyledText text={record.rank} />
        <Avatar src={record.profile === undefined ? Profile : record.profile} />
        <StyledText text={record.name} />
      </Box>
      <StyledText text={record.time} />
    </Box>
  );
};
