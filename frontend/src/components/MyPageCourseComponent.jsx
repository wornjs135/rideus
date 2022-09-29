import styled from "styled-components";
import {StyledText} from "./Common";
import {Box} from "grommet";
import {Notes} from "grommet-icons"

const CourseBox = styled.div`
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

export const MyPageCourse = ({course}) => {
    return (
        <CourseBox>
            <div style={{display: "flex", flexDirection: "column"}}>
                <div style={{display: "flex", justifyContent: "flex-start"}}>
                    <div>
                        <StyledText text={course?.courseName} weight="bold" size={"18px"}
                                    color="#439652"/>
                    </div>
                </div>
                <div>
                    <StyledText text={`${course?.startedLocation} - ${course?.finishedLocation}`} color="#969696"
                    />
                </div>
            </div>
            <div direction="row" style={{display: "flex", justifyContent: "space-between"}}>
                <Box>
                    <Notes/>
                </Box>
                <Box align={"end"}>
                    <StyledText text={`${course?.distance}km`}/>
                    <StyledText text={`${parseInt(course?.expectedTime / 60)}h ${course?.expectedTime % 60}m`}/>
                </Box>
            </div>
        </CourseBox>
    );
};
