import React from "react";
import { Box } from "grommet";
import bike1 from "../assets/images/bicycle.png";
import bike2 from "../assets/images/bicycle2.png";
import Button from "../components/Button";
import { StyledText } from "../components/common";
export const Main = () => {
  return (
    <Box width="100vw" justify="center" margin="0 auto" gap="small">
      {/* 자전거사진, 멘트, 버튼 */}
      <Box align="center" justify="between">
        <Box direction="row" align="center">
          <img src={bike1} />
          <img src={bike2} />
        </Box>
        <Box>즐거운 자전거 여행, 달려볼까요?</Box>
        <Button BigGreen>RIDE!</Button>
      </Box>
      {/* 인기코스, 월간코스, 인기태그 */}
      <Box
        align="center"
        justify="between"
        round
        height="400px"
        background="#F3F3F3"
        border={{ color: "#F3F3F3", size: "small", side: "all" }}
      >
        {/* 인기코스 */}
        <Box align="start">
          <StyledText text="인기 코스" weight="bold" />
        </Box>
      </Box>
    </Box>
  );
};
