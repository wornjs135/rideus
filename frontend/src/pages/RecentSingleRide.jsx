import React, {useEffect,useState} from "react";
import {Box, Spinner} from "grommet";
import {RadarGraph} from "../components/RadarGraph";
import {StyledText} from "../components/Common";
import {RankBox} from "../components/RankBox";
import {RankProfile} from "../components/RankProfile";
import {useNavigate, useParams} from "react-router-dom";
import {getRecordSingle, getRecordWithGroup} from "../utils/api/recordApi";
import {expectTimeHandle, speedHandle, timeHandle} from "../utils/util";
import {Map, MapMarker, Polyline} from "react-kakao-maps-sdk";
import {motion} from "framer-motion";
import TimeTracking from "../assets/images/time-tracking.png";
import Around from "../assets/images/around.png";

export const RecentSingleRide = () => {
    const navigate = useNavigate();
    const [singleRide, setSingleRide] = useState([]);
    const [loading, setLoading] = useState(true);
    const [course, setCourse] = useState({});
    const {recordId} = useParams();
    useEffect(()=> {

        getRecordSingle(recordId,(data) => {
            console.log(data);
            const {data: record} = data;
            setSingleRide(record);
            setCourse(record);
            setLoading(false);
        })
        console.log(recordId);
    },[]);


    if (loading) return <Spinner />;
    else return (<Box width="100vw" justify="center" margin="0 auto" gap="small">

        <Box pad={{left: "20px"}} style={{marginTop: "5vw"}} onClick={() => navigate(-1)}>
            <StyledText text="Back" weight="bold" size="18px"/>
        </Box>

        <Box justify={"center"} style={{marginBottom:"5px"}}>
            <StyledText text={singleRide.courseName} weight={"bold"} size={"18px"}
                        style={{display: "block", textAlign: "center",fontFamily:"gwtt"}}/>
        </Box>

        <Box
            width="100%"
            pad={{ left: "30px", right: "20px" }}
            direction="row"
            align="end"
            gap="small"
        >
            <img
                src={TimeTracking}
                height="24px"
                style={{
                    objectFit: "cover",
                }}
            />
            <StyledText
                text="내 기록"
                size="18px"
                style={{
                    fontFamily: "gwtt",
                }}
            />
        </Box>
        <hr width={"85%"} style={{border: "solid 1px black"}}/>

        <Box direction={"row"} justify={"evenly"} style={{marginBottom:"10px"}}>
            <div>
                <StyledText text={"주행 시간"} style={{textAlign: "center",fontFamily:"gwtt"}}/>
                <StyledText text={timeHandle(singleRide.recordTime)} weight="bold"
                            style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
            </div>
            <div>
                <StyledText text={"평균 주행 속도"} style={{fontFamily:"gwtt"}}/>
                <StyledText text={singleRide.recordSpeedAvg.toFixed(2) + "km/h"} weight="bold"
                            style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
            </div>
            <div>
                <StyledText text={"최고 속도"} style={{fontFamily:"gwtt"}}/>
                <StyledText text={singleRide.recordSpeedBest.toFixed(2) + "km/h"} weight="bold"
                            style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
            </div>
        </Box>
        <Box
            width="100%"
            pad={{ left: "30px", right: "20px" }}
            direction="row"
            align="end"
            gap="small"
        >
            <img
                src={Around}
                height="24px"
                style={{
                    objectFit: "cover",
                }}
            />
            <StyledText
                text="주행한 코스"
                size="18px"
                style={{
                    fontFamily: "gwtt",
                }}
            />
        </Box>
        <hr width={"85%"} style={{border: "solid 1px black"}}/>

        <Box height={"50vh"} align={"center"}>
            <div
                style={{
                    width: "90%",
                    height: "100%",
                }}
                // onClick={() => {
                //     setOpenMap(true);
                // }}
            >
                {course && (
                    <Map
                        center={
                            course.coordinates
                                ? course.coordinates[0]
                                : { lng: 127.002158, lat: 37.512847 }
                        }
                        isPanto={true}
                        style={{ borderRadius: "25px", width: "100%", height: "100%" }}
                    >
                        <Polyline
                            path={[course.coordinates ? course.coordinates : []]}
                            strokeWeight={5} // 선의 두께 입니다
                            strokeColor={"#030ff1"} // 선의 색깔입니다
                            strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                            strokeStyle={"solid"} // 선의 스타일입니다
                        />

                        <MapMarker
                            position={
                                course.coordinates
                                    ? course.coordinates[0]
                                    : { lng: 127.002158, lat: 37.512847 }
                            }
                            image={{
                                src: `/images/start.png`,
                                size: {
                                    width: 29,
                                    height: 41,
                                }, // 마커이미지의 크기입니다
                            }}
                        ></MapMarker>
                        {course.coordinates &&
                        course.coordinates[0].lat ===
                        course.coordinates[course.coordinates.length - 1].lat &&
                        course.coordinates[0].lng ===
                        course.coordinates[course.coordinates.length - 1].lng ? (
                            <MapMarker
                                position={course.coordinates[0]}
                                image={{
                                    src: `/images/start.png`,
                                    size: {
                                        width: 29,
                                        height: 41,
                                    }, // 마커이미지의 크기입니다
                                }}
                            ></MapMarker>
                        ) : (
                            <MapMarker
                                position={
                                    course.coordinates
                                        ? course.coordinates[course.coordinates.length - 1]
                                        : []
                                }
                                image={{
                                    src: `/images/end.png`,
                                    size: {
                                        width: 29,
                                        height: 41,
                                    }, // 마커이미지의 크기입니다
                                }}
                            ></MapMarker>
                        )}
                    </Map>
                )}
            </div>
        </Box>
        {/*<hr width={"85%"} style={{border: "solid 1px black"}}/>*/}
        {/*<Box pad={{left: "20px"}}>*/}
        {/*    <StyledText text="RANKING" weight="bold" size="18px"/>*/}
        {/*</Box>*/}
        {/*<Box>*/}
        {/*    <Box direction="row" justify="center" align="end">*/}
        {/*        /!*<RankProfile record={data[1]}/>*!/*/}
        {/*        /!*<RankProfile record={data[0]}/>*!/*/}
        {/*        /!*<RankProfile record={data[2]}/>*!/*/}
        {/*    </Box>*/}
        {/*</Box>*/}

    </Box>);
};
