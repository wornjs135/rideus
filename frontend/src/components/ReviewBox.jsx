import { Avatar, Box } from "grommet";
import React, { useState } from "react";
import Profile from "../assets/images/profile.png";
import MoreBtn from "../assets/images/more.png";
import { StarBox, StyledText } from "./Common";
import Stars from "../assets/images/stars.png";
import StarsBlank from "../assets/images/stars_blank.png";
import { latlng } from "../utils/data";
import { ReviewDialog } from "./AlertDialog";
import { motion } from "framer-motion";
import { httpToHttps } from "../utils/util";
export const ReviewBox = ({ review, score, starView, courseName }) => {
  const [open, setOpen] = useState(false);
  if (review) {
    // httpToHttps(review.memberProfileImage);
    return (
      <motion.div whileTap={{ scale: 1.2 }}>
        <Box
          direction="row"
          justify="between"
          align="center"
          background="white"
          round="medium"
          margin={{ top: "10px" }}
          pad="small"
          focusIndicator={false}
          onClick={() => {
            setOpen(true);
          }}
        >
          <Box
            direction="row"
            justify="start"
            align="start"
            gap="small"
            focusIndicator={false}
          >
            <Avatar src={httpToHttps(review.memberProfileImage)} size="30px" />
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
            reviewId={review.reviewId}
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
      </motion.div>
    );
  }
};
