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
        {/* <Avatar
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
        /> */}
        <img width="45%" src={`/images/medal${rank.ranking}.png`} />
        <StyledText text={rank.nickname} weight="bold" />
        <img src={`/images/rank${rank.ranking}.png`} />
      </Box>
    );
  else return <Spinner />;
};
