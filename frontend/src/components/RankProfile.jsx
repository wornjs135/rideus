import { Avatar, Box } from "grommet";
import React from "react";
import styled from "styled-components";
import First from "../assets/images/1st.png";
import Second from "../assets/images/2nd.png";
import Third from "../assets/images/3rd.png";
import Profile from "../assets/images/profile.png";
import { StyledText } from "./Common";
export const RankProfile = ({ record }) => {
  return (
    <Box margin="small" style={{ position: "relative" }}>
      <img
        src={
          record.ranking === 1 ? First : record.ranking === 2 ? Second : Third
        }
        style={{
          position: "absolute",
          marginLeft: record.ranking === 1 ? "45px" : "30px",
          display: "flex",
          flexDirection: "column",
        }}
      />
      <Avatar
        size={record.ranking === 1 ? "large" : "medium"}
        src={record.profileImageUrl !== null ? record.profileImageUrl : Profile}
      />
      <Box justify="center" align="center">
        <StyledText text={record.nickname} weight="bold" />
        <StyledText text={record.timeMinute} />
      </Box>
    </Box>
  );
};
