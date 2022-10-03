import { Avatar, Box, Spinner } from "grommet";
import React from "react";
import { StyledText } from "./Common";
import { expectTimeHandle, distanceHandle } from "../utils/util";

export const RankBike = ({ type, rank }) => {
  if (rank)
    return (
      <Box
        width="30%"
        align="center"
        margin={{
          top:
            rank.ranking === 1 ? "100px" : rank.ranking === 2 ? "50px" : "0px",
        }}
        gap="small"
      >
        <Avatar
          src={rank.profileImageUrl}
          size="40px"
          style={{
            borderWidth: "3px",
            borderStyle: "solid",
            borderColor:
              rank.ranking === 1
                ? "#FFD700"
                : rank.ranking === 2
                ? "#C0C0C0"
                : "#CD7F32",
          }}
        />
        <StyledText text={rank.nickname} weight="bold" />
        <StyledText
          text={
            type === "time"
              ? expectTimeHandle(rank.totalTime)
              : type === "dis"
              ? distanceHandle(rank.totalDistance) + "km"
              : rank.speedBest + "km/h"
          }
        />
        <img src={`/images/rank${rank.ranking}.png`} />
      </Box>
    );
  else return <Spinner />;
};
