import React from "react";
import {Box} from "grommet";
import bike1 from "../assets/images/bicycle.png";
import weather from "../assets/images/weather.png";
import {StyledText} from "../components/Common";
import {StyledHorizonTable} from "../components/HorizontalScrollBox";
import {MyPageCourse} from "../components/MyPageCourseComponent";
import {UserSettings} from "grommet-icons";

export const MyPage = () => {
    return <Box width="100vw" justify="center" margin="0 auto" gap="small">
        {/* 자전거사진, 멘트, 버튼 */}
        <Box align="center" justify="between">
            <Box direction="row" align="center" justify="around" style={{marginTop: "5vw"}}>
                <Box direction={"column"}>
                    <UserSettings style={{marginBottom: "5vw"}}/>
                    <img src={bike1}/>
                </Box>
                <Box>
                    <Box direction="row" style={{marginBottom: "5vw", marginLeft: "auto"}}>
                        <Box style={{marginRight: "5vw"}}>
                            <StyledText text="42h 27m" weight="bold" size="15px"
                                        style={{textAlign: "right", display: "inline"}}/>
                            <StyledText
                                color="#4B4B4B"
                                text="675km"
                                weight="bold"
                                size="15px"
                                style={{textAlign: "right", display: "inline"}}
                            />
                        </Box>
                        <img src={weather} width={50}/>
                    </Box>
                    <Box justify={"end"}>
                        <StyledText text="SSAFY KIM님은 현재" weight="bold" size="18px"
                                    style={{textAlign: "right", display: "inline"}}></StyledText>
                        <StyledText text="서울에서 부산까지의 거리를" weight="bold" size="18px"
                                    style={{textAlign: "right", display: "inline"}}></StyledText>
                        <StyledText text="왕복했어요!" weight="bold" size="18px"
                                    style={{textAlign: "right", display: "inline"}}></StyledText>
                    </Box>
                </Box>
            </Box>


        </Box>
        {/* 인기코스, 월간코스, 인기태그 */}
        <Box
            align="center"
            justify="between"
            round
            background="#F3F3F3"
            border={{color: "#F3F3F3", size: "small", side: "all"}}
        >
            {/* 인기코스 */}
            <Box align="start" width="100%" gap="large" margin="large">
                <Box pad={{left: "20px"}}>
                    <StyledText text="RECENT RIDE" weight="bold" size="18px"/>
                </Box>
                {/* 인기코스 리스트 */}
                <Box direction="row" overflow="scroll">
                    <StyledHorizonTable>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                    </StyledHorizonTable>
                </Box>
            </Box>
            {/* 월간자전거 */}
            <Box align="start" width="100%" gap="large" margin="large">
                <Box pad={{left: "20px"}}>
                    <StyledText text="나만의 코스" weight="bold" size="18px"/>
                </Box>
                {/* 월간자전거 리스트 */}
                <Box direction="row" overflow="scroll">
                    <StyledHorizonTable>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                    </StyledHorizonTable>
                </Box>
            </Box>
            {/* 인기태그 */}
            <Box align="start" width="100%" gap="large" margin="large">
                <Box pad={{left: "20px"}}>
                    <StyledText text="코스 북마크" weight="bold" size="18px"/>
                </Box>
                {/* 인기태그 리스트 */}
                {/* 월간자전거 리스트 */}
                <Box direction="row" overflow="scroll">
                    <StyledHorizonTable>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                    </StyledHorizonTable>
                </Box>
            </Box>
            {/* 인기태그 */}
            <Box align="start" width="100%" gap="large" margin="large">
                <Box pad={{left: "20px"}}>
                    <StyledText text="MY RIDE" weight="bold" size="18px"/>
                </Box>
                {/* 인기태그 리스트 */}
                {/* 월간자전거 리스트 */}
                <Box direction="row" overflow="scroll">
                    <StyledHorizonTable>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                        <div className="card">
                            <MyPageCourse/>
                        </div>
                    </StyledHorizonTable>
                </Box>
            </Box>
        </Box>
    </Box>;
};
