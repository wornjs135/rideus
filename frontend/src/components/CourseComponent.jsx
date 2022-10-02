import styled from "styled-components";
import { StarBox, StyledText } from "./Common.jsx";
import Star from "../assets/images/star.png";
import StarBlank from "../assets/images/star_blank.png";
import { Box, Spinner } from "grommet";
import { expectTimeHandle, timeHandle } from "../utils/util.js";
import { useNavigate } from "react-router-dom";
import NoNews from "../assets/images/news.png";
const CourseBox = styled.div`
  display: flex;
  flex-direction: column;
  position: relative;
  width: 141px;
  height: 133px;
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
  text-align: center;
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
          <StyledText text={course.courseName} weight="bold" color="#000000" />
          <StarBox starView={parseFloat(course.starAvg) * 20} />
          <Box align="end" gap="small" margin={{ top: "25px" }}>
            <StyledText
              text={"코스 길이 : " + course.distance + "km"}
              style={{
                backgroundColor: "#BDE0EF",
                borderRadius: "10px",
                // margin: "3px",
                fontSize: "12px",
                paddingLeft: "10px",
                paddingRight: "10px",
              }}
              weight="bold"
            />
            <StyledText
              text={"예상 시간 : " + expectTimeHandle(course.expectedTime)}
              style={{
                backgroundColor: "#F8F38F",
                borderRadius: "10px",
                fontSize: "12px",
                paddingLeft: "10px",
                paddingRight: "10px",
              }}
              weight="bold"
            />
            <StyledText
              text={
                course.start.split(" ")[0] + " " + course.start.split(" ")[1]
              }
              style={{
                backgroundColor: "#F4D4D4",
                borderRadius: "10px",
                fontSize: "12px",
                paddingLeft: "10px",
                paddingRight: "10px",
              }}
              weight="bold"
            />
          </Box>
        </CourseBox>
        {/* <Box direction="row" overflow="scroll" gap="medium">
          {course.tags &&
            course.tags.map((tag, idx) => {
              return <StyledText text={`#${tag.tagName}`} key={tag.tagId} />;
            })}
        </Box> */}
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
      <Box height="50%">
        <img
          src={news.image ? news.image : NoNews}
          alt=""
          style={{
            borderRadius: "15px 15px 0 0",
          }}
        />
      </Box>
      <Box align="center">
        <StyledText
          text={news.title}
          weight="bold"
          color="black"
          style={{
            width: "90%",
            height: "40px",
            overflow: "hidden",
            textOverflow: "ellipsis",
          }}
        />
      </Box>
    </NewsDiv>
  );
};
