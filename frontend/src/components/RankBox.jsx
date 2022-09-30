import { Avatar, Box } from "grommet";
import React from "react";
import { StyledText } from "./Common";
import Profile from "../assets/images/profile.png";
export const RankBox = ({ record }) => {
  return (
    <Box direction="row" justify="between" gap="small">
      <Box width="10%" justify="center">
        <StyledText text={record.ranking} />
      </Box>

      <Box direction="row" width="40%">
        <Avatar
          src={
            record.profileImageUrl === null ? Profile : record.profileImageUrl
          }
        />
        <StyledText text={record.nickname} />
      </Box>
      <Box width="20%" justify="center">
        <StyledText text={record.timeMinute} />
      </Box>
    </Box>
  );
};
