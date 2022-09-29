import styled from "styled-components";
import { StarBox, StyledText } from "./Common.jsx";
import Star from "../assets/images/star.png";
import StarBlank from "../assets/images/star_blank.png";
import { Box, Spinner } from "grommet";
import { expectTimeHandle, timeHandle } from "../utils/util.js";
import { useNavigate } from "react-router-dom";

const CourseBox = styled.div`
  display: flex;
  flex-direction: column;
  position: relative;
  justify-content: space-between;
  width: 153px;
  height: 222px;
  margin-right: 15px;
  margin-bottom: 10px;
  margin-top: 5px;
  padding: 10px;
  padding-top: 14px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 4px 4px 4px -4px rgb(0 0 0 / 0.2);
`;

const NewsDiv = styled.div`
  position: relative;
  align-items: center;
  width: 153px;
  height: 222px;
  margin-right: 15px;
  margin-bottom: 10px;
  margin-top: 5px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 4px 4px 4px -4px rgb(0 0 0 / 0.2);
`;

// bookmarkId: null
// category: null
// courseId: "6333e98361656141c1e9179e"
// courseName: "남북"
// distance: 80.7
// expectedTime: 193
// finish: "경기도 하남시 풍산동"
// imageUrl: null
// likeCount: 0
// start: "경기도 하남시 풍산동"
// tags: null

export const BestCourse = ({ course }) => {
  const navigate = useNavigate();
  if (course)
    return (
      <>
        <CourseBox
          onClick={() => {
            navigate(`/courseDetail/${course.courseId}`);
          }}
        >
          <StyledText text={course.courseName} weight="bold" color="#439652" />
          <StarBox starView={parseFloat(course.starAvg) * 22.8} />
          <StyledText
            text={course.start + " - " + course.finish}
            color="#969696"
            style={{
              overflow: "hidden",
              wordBreak: "normal",
              whiteSpace: "normal",
            }}
          />
          <Box align="end">
            <StyledText text={"총 코스 길이 : " + course.distance + "km"} />
            <StyledText
              text={"예상 시간 : " + expectTimeHandle(course.expectedTime)}
            />
          </Box>
        </CourseBox>
        <Box direction="row" overflow="scroll" gap="medium">
          {course.tags &&
            course.tags.map((tag, idx) => {
              return <StyledText text={`#${tag.tagName}`} key={tag.tagId} />;
            })}
        </Box>
      </>
    );
  else return <Spinner />;
};

export const NewsBox = ({ news }) => {
  return (
    <NewsDiv
      onClick={() => {
        window.open(news.link, "_blank");
      }}
    >
      <Box>
        <img
          src={news.image}
          alt=""
          style={{
            borderRadius: "15px 15px 0 0",
          }}
        />
      </Box>
      <StyledText
        text={news.title}
        weight="bold"
        color="#439652"
        style={{
          width: "90%",
          height: "80px",
          overflow: "hidden",
          wordBreak: "normal",
          whiteSpace: "normal",
        }}
      />
    </NewsDiv>
  );
};
