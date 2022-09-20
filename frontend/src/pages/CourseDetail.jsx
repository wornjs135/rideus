import { Box } from "grommet";
import React, { useState } from "react";
import { Map } from "react-kakao-maps-sdk";
import { StyledText } from "../components/Common";
import Star from "../assets/images/star_review.png";
import StarBlank from "../assets/images/star_review_blank.png";
import Bookmark from "../assets/images/bookmark.png";
import Button from "../components/Button";
import { CourseReviewRank } from "../components/CourseReviewRank";
export const CourseDetail = () => {
  const [open, setOpen] = useState(true);
  return (
    <Box align="center" width="100%">
      <Box direction="row" justify="center">
        <StyledText text="마포점-정서진" size="24px" weight="bold" />
      </Box>
      <Box width="90%" height="60vh">
        <Map
          center={{ lng: 127.002158, lat: 37.512847 }}
          isPanto={true}
          style={{ borderRadius: "25px", width: "100%", height: "100%" }}
        ></Map>
      </Box>
      <Box direction="row" justify="between" width="90%">
        <Box direction="row" align="center">
          <StyledText text="4.0" />
          <Box direction="row">
            <img src={Star} alt="" />
            <img src={Star} alt="" />
            <img src={Star} alt="" />
            <img src={Star} alt="" />
            <img src={StarBlank} alt="" />
          </Box>
        </Box>
        <Box direction="row" align="center">
          <Button children="북마크" />
          <img src={Bookmark} width="20px" height="20px" />
        </Box>
      </Box>
      <Button BigGreen children="주행 시작" />
      <CourseReviewRank
        open={open}
        onDismiss={() => {
          setOpen(false);
        }}
      />
    </Box>
  );
};
