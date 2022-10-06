import styled from "styled-components";
import { StyledText } from "./Common";
import { Box } from "grommet";
import { Notes } from "grommet-icons";
import {
  distanceHandle,
  distanceHandleM,
  expectTimeHandle,
  expectTimeHandle2,
} from "../utils/util";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";

const CourseBox = styled(motion.div)`
  flex-direction: column;
  display: flex;
  justify-content: space-between;
  width: 130px;
  height: 130px;
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
  word-break: break-word;

  display: -webkit-box;
  -webkit-line-clamp: 2; // 원하는 라인수
  -webkit-box-orient: vertical;
`;

export const MyPageCourse = ({ course, nav }) => {
  const navigate = useNavigate();
  return (
    <CourseBox onClick={nav} whileTap={{ scale: 1.2 }}>
      <div style={{ display: "flex", flexDirection: "column" }}>
        <div style={{ display: "flex", justifyContent: "flex-start" }}>
          <Wrap>
            <StyledText
              text={course?.courseName}
              weight="bold"
              color="#000000"
            />
          </Wrap>
        </div>

        <Box align="end" gap="small" margin={{ top: "2vw" }}>
          <Notes />
        </Box>
      </div>
      <div align="end" gap="small" margin={{ top: "25px" }}>
        <Box align={"end"}>
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
          {/*<StyledText text={`${course?.distance}km`} />*/}
          {/*<StyledText*/}
          {/*  text={`${parseInt(course?.expectedTime / 60)}h ${*/}
          {/*    course?.expectedTime % 60*/}
          {/*  }m`}*/}
          {/*/>*/}
        </Box>
      </div>
    </CourseBox>
  );
};
