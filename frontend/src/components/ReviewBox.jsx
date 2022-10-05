import { Avatar, Box } from "grommet";
import React, { useState } from "react";
import Profile from "../assets/images/profile.png";
import MoreBtn from "../assets/images/more.png";
import { StarBox, StyledText } from "./Common";
import Stars from "../assets/images/stars.png";
import StarsBlank from "../assets/images/stars_blank.png";
import { latlng } from "../utils/data";
import { ReviewDialog } from "./AlertDialog";
export const ReviewBox = ({ review, score, starView, courseName }) => {
  const [open, setOpen] = useState(false);
  if (review)
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
          <Avatar src={Profile} size="30px" />
          <Box justify="center" align="start">
            <StyledText text={review.memberNickname} />
            <StyledText text={review.content} />
            {/* <Box direction="row" gap="3px">
              {review.tags.map((tag) => {
                return (
                  <StyledText
                    text={`#${tag.tagName}`}
                    size="11px"
                    key={tag.tagId}
                  />
                );
              })}
            </Box> */}
            <StarBox
              score={score}
              starView={parseFloat(score).toFixed(2) * 16}
            />
          </Box>
        </Box>
        <img src={MoreBtn} style={{ marginRight: "9px" }} />
        <ReviewDialog
          open={open}
          handleClose={() => {
            setOpen(false);
          }}
          title={courseName}
          desc={review.content}
          img={review.imageUrl}
          course={latlng}
          cancel={"닫기"}
          score={score}
          starView={starView}
          tags={review.tags}
        />
      </Box>
    );
};
