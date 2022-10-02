import { Avatar, Box } from "grommet";
import React, { useEffect, useState } from "react";
import { BottomSheet } from "react-spring-bottom-sheet";
import { StyledText } from "./Common";
import { RankProfile } from "./RankProfile";
import Profile from "../assets/images/profile.png";
import { RankBox } from "./RankBox";
import { ReviewBox } from "./ReviewBox";
export const CourseReviewRank = ({
  open,
  onDismiss,
  ranks,
  reviews,
  courseName,
}) => {
  const [value, setValue] = useState(0);
  const [data, setData] = useState([]);

  useEffect(() => {
    setData([
      { rank: 1, name: "김싸피", time: "2h 5m" },
      { rank: 2, name: "박삼성", time: "2h 13m" },
      { rank: 3, name: "이갤럭시", time: "2h 15m" },
      { rank: 4, name: "김싸피", time: "2h 5m" },
      { rank: 5, name: "박삼성", time: "2h 13m" },
      { rank: 6, name: "이갤럭시", time: "2h 15m" },
      { rank: 7, name: "김싸피", time: "2h 5m" },
      { rank: 8, name: "박삼성", time: "2h 13m" },
      { rank: 9, name: "이갤럭시", time: "2h 15m" },
      { rank: 10, name: "김싸피", time: "2h 5m" },
      { rank: 11, name: "박삼성", time: "2h 13m" },
      { rank: 12, name: "이갤럭시", time: "2h 15m" },
    ]);
  }, []);
  return (
    <BottomSheet
      style={{ zIndex: 1000, overflowY: "hidden", overflow: "hidden" }}
      open={open}
      blocking={false}
      scrollLocking={true}
      header={
        <Box
          direction="row"
          justify="center"
          width="100%"
          border={false}
          background="rgba(250, 250, 250, 0.93)"
        >
          <Box
            border={false}
            justify="center"
            align="center"
            background={value === 0 ? "#64CCBE" : "white"}
            width="50%"
            height="4vh"
            focusIndicator={false}
            onClick={() => {
              setValue(0);
            }}
          >
            <StyledText text="랭킹" color={value === 0 ? "white" : "black"} />
          </Box>
          <Box
            border={false}
            justify="center"
            align="center"
            background={value === 1 ? "#64CCBE" : "white"}
            width="50%"
            height="4vh"
            focusIndicator={false}
            onClick={() => {
              setValue(1);
            }}
          >
            <StyledText text="리뷰" color={value === 1 ? "white" : "black"} />
          </Box>
        </Box>
      }
      snapPoints={({ maxHeight }) => [maxHeight / 8, maxHeight * 0.6]}
    >
      <Box
        align="center"
        background="rgba(250, 250, 250, 0.93)"
        style={{
          overflowY: "hidden",
          overflow: "hidden",
        }}
        height={{ min: "30vh", max: "50vh" }}
      >
        <Box
          width="90%"
          align="center"
          pad="small"
          style={{ display: value === 0 ? "block" : "none" }}
          height="95%"
        >
          {ranks.length > 0 ? (
            <>
              <StyledText text="시간별 순위" weight="bold" size="16px" />
              <Box direction="row" justify="center" align="end">
                {ranks.length > 1 && <RankProfile record={ranks[1]} />}
                {ranks.length > 0 && <RankProfile record={ranks[0]} />}
                {ranks.length > 2 && <RankProfile record={ranks[2]} />}
              </Box>
              <div
                style={{
                  height: "35vh",
                  overflow: "scroll",
                }}
              >
                {ranks &&
                  ranks.map((d, idx) => {
                    return <RankBox record={d} key={idx} />;
                  })}
              </div>
            </>
          ) : (
            <StyledText
              text="랭킹이 없습니다."
              style={{ textAlign: "center" }}
            />
          )}
        </Box>

        <Box
          style={{ display: value === 1 ? "block" : "none" }}
          width="90%"
          align="center"
          pad="small"
          overflow="scroll"
          height="95%"
        >
          {reviews.length > 0 ? (
            reviews.map((r, idx) => {
              return (
                <ReviewBox
                  key={idx}
                  score={r.score}
                  starView={parseFloat(r.score * 22.8)}
                  review={r}
                  courseName={courseName}
                />
              );
            })
          ) : (
            <StyledText
              text="리뷰가 없습니다."
              style={{ textAlign: "center" }}
            />
          )}
        </Box>
      </Box>
    </BottomSheet>
  );
};
