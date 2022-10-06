import styled from "styled-components";
import { StyledText } from "./Common";
import { Box } from "grommet";
import { Notes } from "grommet-icons";
import {
    distanceHandle,
    distanceHandleM,
    expectTimeHandle,
    expectTimeHandle2, timeHandle,
    timeHandle2,
} from "../utils/util";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import Clock from "../assets/icons/clock.svg";
import Flag from "../assets/icons/flag.svg";
import Mark from "../assets/icons/mark.svg";

const CourseBox = styled(motion.div)`
  flex-direction: column;
  display: flex;
  justify-content: space-between;
  width: 130px;
  height: 140px;
  margin-right: 15px;
  margin-bottom: 10px;
  margin-top: 5px;

  padding: 10px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 4px 4px 4px -4px rgb(0 0 0 / 0.2);
`;

export const Wrap = styled.div`
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: normal;
  word-break: break-word;
  font-weight: bold;
  color: #000000;
  display: -webkit-box;
  -webkit-line-clamp: 1; // 원하는 라인수
  -webkit-box-orient: vertical;
`;

export const MyPageCourse = ({ course, nav, type }) => {
  const navigate = useNavigate();
  return (
    <CourseBox onClick={nav} whileTap={{ scale: 1.2 }}>
      <div style={{ display: "flex", flexDirection: "column" }}>
        <div style={{ display: "flex", justifyContent: "flex-start" }}>
          <Wrap>{course?.courseName}</Wrap>
        </div>

        <Box align="end" gap="small" margin={{ top: "2vw" }}>
          <Notes />
        </Box>
      </div>
      <Box align="end" gap="small" margin={{ top: "10px" }}>
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
          {type !== "bookmark" ? timeHandle(course?.expectedTime) : timeHandle2(course?.expectedTime)}
        </Box>
        <Box
          gap="3px"
          align="center"
          direction="row"
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
          {course?.distance.toFixed(2)+"km"}
        </Box>
        {course.startedLocation && (
          <Box
            gap="3px"
            direction="row"
            align="center"
            style={{
              fontSize: "12px",
              backgroundColor: "#F4D4D4",
              borderRadius: "10px",
              // margin: "3px",
              padding: "0px 4px",
              fontWeight: "bold",
            }}
          >
            <img src={Mark} width="12px" height="12px" />
            {course.startedLocation &&
              course?.startedLocation.split(" ")[0] +
                " " +
                course?.startedLocation.split(" ")[1]}
          </Box>
        )}
      </Box>

      {/* <Box align={"end"}>
          <StyledText
            text={distanceHandleM(course?.distance) + " 주행"}
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
            text={expectTimeHandle2(course?.expectedTime) + " 주행"}
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
              course.startedLocation &&
              course?.startedLocation.split(" ")[0] +
                " " +
                course?.startedLocation.split(" ")[1]
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
        </Box> */}
    </CourseBox>
  );
};
