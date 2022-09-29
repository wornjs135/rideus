import React, {useEffect, useState} from "react";
import {Box, Spinner} from "grommet";
import bike1 from "../assets/images/bicycle.png";
import weather from "../assets/images/weather.png";
import {StyledText} from "../components/Common";
import {StyledHorizonTable} from "../components/HorizontalScrollBox";
import {MyPageCourse} from "../components/MyPageCourseComponent";
import {UserSettings} from "grommet-icons";
import {myRides, recentRide} from "../utils/api/userApi";
import {useSelector} from 'react-redux'
import {useNavigate} from "react-router-dom";
import {getBookmarkedCourses} from "../utils/api/bookmarkApi";

export const MyPage = () => {

    const navigate = useNavigate();
    const [recentRides, setRecentRides] = useState([]);
    const [myRideCourses, setMyRideCourses] = useState([]);
    const [bookmarkCourses, setbookmarkCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const user = useSelector(state => state.user.user.user);
    useEffect(() => {
        setLoading(true);
        recentRide((data) => {
            console.log(data);
            const {data: recent} = data;
            console.log(recent);
            setRecentRides(recent);
        });

        myRides((data) => {
            console.log(data);
            const {data: rides} = data;
            console.log(rides);
            setMyRideCourses(rides);
        });
        getBookmarkedCourses((data) => {
            console.log(data);
            const {data: rides} = data;
            console.log(rides);
            setbookmarkCourses(rides);
        }, () => {
        });
        console.log(user);
        setLoading(false);
    }, []);

    if (loading) return <Spinner/>;
    else return (<Box width="100vw" justify="center" margin="0 auto" gap="small">
            {/* 자전거사진, 멘트, 버튼 */}

            <Box align="center" justify="between">
                <Box direction="row" align="center" justify="around" style={{marginTop: "5vw"}}>
                    <Box direction={"column"}>
                        <UserSettings style={{marginBottom: "5vw"}} onClick={() => navigate("/profile")}/>
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
            {/* 최근 주행, 나만의 코스, 코스 북마크 */}
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
                        <StyledText text="최근 주행" weight="bold" size="18px"/>
                    </Box>
                    {/* 인기코스 리스트 */}
                    <Box direction="row" overflow="scroll">
                        <StyledHorizonTable>
                            {recentRides && recentRides.map((course, index, array) =>
                                <div key={index} className="card">
                                    <MyPageCourse course={course}/>
                                </div>)
                            }

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
                            {myRideCourses && myRideCourses.map((value, index, array) =>
                                <div key={index} className="card">
                                    <MyPageCourse course={value}/>
                                </div>)
                            }

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
                            {
                                bookmarkCourses && bookmarkCourses.map((value, index, array) =>
                                    <div className="card" key={index}>
                                        <MyPageCourse course={value}/>
                                    </div>
                                )
                            }


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
                            {/*<div className="card">*/}
                            {/*    <MyPageCourse/>*/}
                            {/*</div>*/}
                            {/*<div className="card">*/}
                            {/*    <MyPageCourse/>*/}
                            {/*</div>*/}
                            {/*<div className="card">*/}
                            {/*    <MyPageCourse/>*/}
                            {/*</div>*/}
                            {/*<div className="card">*/}
                            {/*    <MyPageCourse/>*/}
                            {/*</div>*/}
                            {/*<div className="card">*/}
                            {/*    <MyPageCourse/>*/}
                            {/*</div>*/}
                        </StyledHorizonTable>
                    </Box>
                </Box>
            </Box>
        </Box>
    )
};
