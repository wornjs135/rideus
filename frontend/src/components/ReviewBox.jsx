import { Box } from "grommet";
import React, { useState } from "react";
import Profile from "../assets/images/profile.png";
import MoreBtn from "../assets/images/more.png";
import { StarBox, StyledText } from "./Common";
import Stars from "../assets/images/stars.png";
import StarsBlank from "../assets/images/stars_blank.png";
import { latlng } from "../utils/data";
import { ReviewDialog } from "./AlertDialog";
export const ReviewBox = ({ review, score, starView }) => {
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
          <img src={Profile} width="30px" />
          <Box justify="center" align="start">
            <StyledText text={review.memberId} />
            <StyledText text={review.content} />
            <Box direction="row">
              {review.tags.map((tag) => {
                return <StyledText text={`#${tag.tagName}`} key={tag.tagId} />;
              })}
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
          desc={review.content}
          course={latlng}
          cancel={"닫기"}
          score={score}
          starView={starView}
          tags={review.tags}
        />
      </Box>
    );
};
