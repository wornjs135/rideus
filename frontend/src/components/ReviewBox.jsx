import { Box } from "grommet";
import React, { useState } from "react";
import Profile from "../assets/images/profile.png";
import MoreBtn from "../assets/images/more.png";
import { StarBox, StyledText } from "./Common";
import Stars from "../assets/images/stars.png";
import StarsBlank from "../assets/images/stars_blank.png";
import { latlng } from "../utils/data";
import { ReviewDialog } from "./AlertDialog";
export const ReviewBox = ({ score, starView }) => {
  const [open, setOpen] = useState(false);
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
      <Box
        direction="row"
        justify="start"
        align="start"
        gap="small"
        focusIndicator={false}
        onClick={() => {
          setOpen(true);
        }}
      >
        <img src={Profile} width="30px" />
        <Box justify="center" align="start">
          <StyledText text="배인수" />
          <StyledText text="오르막길 오르다 죽을 뻔..." />
          <Box direction="row">
            <StyledText text="#완만" />
            <StyledText text="#가파름" />
            <StyledText text="#시원함" />
          </Box>
          <StarBox score={score} starView={starView} />
        </Box>
      </Box>
      <img src={MoreBtn} style={{ marginRight: "9px" }} />
      <ReviewDialog
        open={open}
        handleClose={() => {
          setOpen(false);
        }}
        title="마포점-정서진 코스"
        desc="오르막길 오르다 죽을뻔 했지만 가나다라 마바사가 너무 멋져서 좋았어요!"
        course={latlng}
        cancel={"닫기"}
        score={score}
        starView={starView}
        tags={[
          { id: 6, searchTagName: "붐빔" },
          { id: 7, searchTagName: "쾌적함" },
          { id: 8, searchTagName: "라이더 많음" },
        ]}
      />
    </Box>
  );
};
