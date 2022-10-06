import styled from "styled-components";
import { StarBox, StyledText } from "./Common.jsx";
import Star from "../assets/images/star.png";
import StarBlank from "../assets/images/star_blank.png";
import { Avatar, Box, Spinner } from "grommet";
import { expectTimeHandle, timeHandle, timeHandle2 } from "../utils/util.js";
import { useNavigate } from "react-router-dom";
import NoNews from "../assets/images/news.png";
import Clock from "../assets/icons/clock.svg";
import Flag from "../assets/icons/flag.svg";
import { motion } from "framer-motion";
import NoImage from "../assets/images/noimage.jpg";

const CourseBox = styled(motion.div)`
  display: flex;
  flex-direction: column;
  position: relative;
  width: 141px;
  height: 144px;
  margin-right: 15px;
  margin-bottom: 10px;
  margin-top: 5px;
  padding: 10px;
  padding-top: 14px;
  background-color: white;
  border-radius: 15px;
  justify-content: space-between;
  box-shadow: 4px 4px 4px -4px rgb(0 0 0 / 0.2);
`;

const NewsDiv = styled(motion.div)`
  display: flex;
  flex-direction: column;
  position: relative;
  width: 141px;
  height: 144px;
  margin-right: 15px;
  margin-bottom: 10px;
  margin-top: 5px;
  padding: 10px;
  padding-top: 14px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 4px 4px 4px -4px rgb(0 0 0 / 0.2);
`;

const Wrap = styled.div`
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: normal;
  font-weight: bold;
  word-break: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 2; // 원하는 라인수
  -webkit-box-orient: vertical;
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
          whileTap={{ scale: 1.2 }}
        >
          <Box gap="10px" align="center">
            <img
              src={course.imageUrl ? course.imageUrl : NoImage}
              width="140px"
              height="85px"
              style={{ borderRadius: "10px", objectFit: "cover" }}
            />
            <StyledText
              text={course.courseName}
              weight="bold"
              color="#000000"
              size="16px"
              style={{
                textAlign: "center",
                width: "90%",
                height: "20px",
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "nowrap",
                display: "block",
                weight: "bold",
              }}
            />
          </Box>
          {/* <Box margin={{ left: "30px" }}>
            <StarBox starView={parseFloat(course.starAvg) * 14} />
          </Box> */}
          <Box
            justify="end"
            direction="row"
            gap="small"
            margin={{ top: "10px" }}
            width="100%"
          >
            <Box
              gap="3px"
              direction="row"
              align="center"
              style={{
                fontSize: "12px",
                backgroundColor: "#F8F38F",
                borderRadius: "10px",
                // margin: "3px",
                padding: "0px 4px",
                fontWeight: "bold",
              }}
            >
              <img src={Clock} width="12px" height="12px" />
              {timeHandle2(course.expectedTime)}
            </Box>
            <Box
              gap="3px"
              direction="row"
              align="center"
              style={{
                fontSize: "12px",
                backgroundColor: "#BDE0EF",
                borderRadius: "10px",
                // margin: "3px",
                padding: "0px 4px",
                fontWeight: "bold",
              }}
            >
              <img src={Flag} width="12px" height="12px" />
              {course.distance + "km"}
            </Box>

            {/* <Box
              // align="center"
              gap="4px"
              
            >
             
            </Box>
            <Box
              gap="4px"

            >

            </Box> */}
            {/* <StyledText
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
            /> */}
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
      whileTap={{ scale: 1.2 }}
    >
      <Box height="50%" width="100%">
        <img
          src={news.image ? news.image : NoNews}
          alt=""
          width="100%"
          height="100%"
          style={{ borderRadius: "10px", objectFit: "cover" }}
        />
      </Box>
      <Box align="center" margin={{ top: "10px" }}>
        <Wrap
        // weight="bold"
        // color="black"
        // style={{
        //   fontFamily: "gwmd",
        //   width: "90%",
        //   overflow: "hidden",
        //   textOverflow: "ellipsis",
        //   whiteSpace: "normal",
        // }}
        >
          {news.title}
        </Wrap>
      </Box>
    </NewsDiv>
  );
};
