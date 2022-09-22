import { Avatar, Box } from "grommet";
import React from "react";
import { StyledText } from "./Common";
import Profile from "../assets/images/profile.png";
export const RankBox = ({ record }) => {
  return (
    <Box direction="row" justify="between" gap="small">
      <Box width="10%" justify="center">
        <StyledText text={record.rank} />
      </Box>

      <Box direction="row" width="40%">
        <Avatar src={record.profile === undefined ? Profile : record.profile} />
        <StyledText text={record.name} />
      </Box>
      <Box width="20%" justify="center">
        <StyledText text={record.time} />
      </Box>
    </Box>
  );
};
