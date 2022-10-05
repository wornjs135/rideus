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
      height="48px"
      style={{
        borderTop: "1px solid #F4F4F4",
        boxShadow:
          user.nickname === record.nickname ? "-5px 0px 0px 0px #64CCBE" : "",
        background:
          user.nickname === record.nickname
            ? "linear-gradient( to right, rgba(100, 204, 190, 0.26), white )"
            : "",
      }}
    >
      <Box direction="row" width="65%" align="center" gap="medium">
        <StyledText
          text={record.ranking}
          weight="bold"
          style={{ width: "15%", textAlign: "end" }}
        />
        <Box width="25%">
          <Avatar
            size="35px"
            src={
              record.profileImageUrl === null ? Profile : record.profileImageUrl
            }
          />
        </Box>

        <StyledText text={record.nickname} style={{ width: "75%" }} />
      </Box>
      <Box width="30%" justify="center" align="end">
        {type ? (
          type === "time" ? (
            <StyledText
              text={expectTimeHandle(record.totalTime)}
              weight="bold"
            />
          ) : type === "dis" ? (
            <StyledText
              text={parseFloat(record.totalDistance).toFixed(2) + "km"}
              weight="bold"
            />
          ) : (
            <StyledText text={record.speedBest + "km/h"} weight="bold" />
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
