import { Avatar, Box } from "grommet";
import React from "react";
import { StyledText } from "./Common";
import Profile from "../assets/images/profile.png";
import { distanceHandle, expectTimeHandle } from "../utils/util";
import { useSelector } from "react-redux";

export const RankBox = ({ record, type }) => {
  const user = useSelector((state) => state.user.user.user);

  return (
    <Box
      direction="row"
      justify="between"
      style={{
        borderTop: "1px solid #F4F4F4",
        boxShadow:
          user.nickname === record.nickname ? "-5px 0px 0px 0px #64CCBE" : "",
        background:
          user.nickname === record.nickname
            ? "linear-gradient( to right, rgba(100, 204, 190, 0.26), white )"
            : "",
      }}
      pad="small"
    >
      <Box direction="row" width="40%" align="center" gap="medium">
        <StyledText text={record.ranking} weight="bold" />
        <Avatar
          size="34px"
          src={
            record.profileImageUrl === null ? Profile : record.profileImageUrl
          }
        />
        <StyledText text={record.nickname} />
      </Box>
      <Box width="25%" justify="center" align="end">
        {type ? (
          type === "time" ? (
            <StyledText
              text={expectTimeHandle(record.totalTime)}
              weight="bold"
            />
          ) : type === "dis" ? (
            <StyledText
              text={distanceHandle(record.totalDistance) + "km"}
              weight="bold"
            />
          ) : (
            <StyledText text={"dd"} weight="bold" />
          )
        ) : (
          <StyledText
            text={expectTimeHandle(record.timeMinute)}
            weight="bold"
          />
        )}
      </Box>
    </Box>
  );
};
