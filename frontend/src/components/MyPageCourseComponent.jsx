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
                        <StyledText text="강촌길" weight="bold" size={"18px"}
                                    color="#439652"/>
                    </div>
                </div>
                <div>
                    <StyledText text="경강교 - 강촌유원" color="#969696"
                    />
                </div>
            </div>
            <div direction="row" style={{display: "flex", justifyContent: "space-between"}}>
                <Box>
                    <Notes/>
                </Box>
                <Box align={"end"}>
                    <StyledText text="21km"/>
                    <StyledText text="1h 20m"/>
                </Box>
            </div>
        </CourseBox>
    );
};
