import React, {useEffect, useState} from "react";
import {Box, Spinner} from "grommet";
import {RadarGraph} from "../components/RadarGraph";
import {StyledText} from "../components/Common";
import {RankBox} from "../components/RankBox";
import {RankProfile} from "../components/RankProfile";
import {useNavigate, useParams} from "react-router-dom";
import {getRecordWithGroup} from "../utils/api/recordApi";
import {expectTimeHandle, speedHandle} from "../utils/util";
import TimeTracking from "../assets/images/time-tracking.png";
import Satisfaction from "../assets/images/satisfaction.png";
import RankTitle from "../assets/images/rankTitle.png";

export const RecentGroupRide = () => {
    const navigate = useNavigate();
    const [groupRide, setGroupRide] = useState([]);
    const [loading, setLoading] = useState(true);
    const {recordId} = useParams();
    useEffect(() => {

        getRecordWithGroup(recordId, (data) => {
            console.log(data);
            const {data: rec} = data;
            setGroupRide(rec);
            // console.log(rec.records);
            // console.log(rec.records.map(d => d.nickname));
            setLoading(false);
        });
        console.log(recordId);
    }, []);


    if (loading) return <Spinner/>;
    else return (<Box width="100vw" justify="center" margin="0 auto" gap="small">

        <Box pad={{left: "20px"}} style={{marginTop: "5vw"}} onClick={() => navigate(-1)}>
            <StyledText text="Back" weight="bold" size="18px"/>
        </Box>

        <Box justify={"center"} gap={"xlarge"} style={{marginBottom: "5px"}}>
            <StyledText text={groupRide.courseName} weight={"bold"} size={"18px"}
                        style={{display: "block", textAlign: "center", fontFamily: "gwtt"}}/>
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
        <Box direction={"row"} justify={"evenly"} style={{marginBottom:"20px"}}>
            <div>
                <StyledText text={"주행 시간"} style={{textAlign: "center", fontFamily: "gwtt"}}/>
                <StyledText text={expectTimeHandle(groupRide.recordTimeMinute)} weight="bold"
                            style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
            </div>
            <div>
                <StyledText text={"평균 주행 속도"} style={{fontFamily: "gwtt"}}/>
                <StyledText text={groupRide.recordSpeedAvg + "km/h"} weight="bold"
                            style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
            </div>
            <div>
                <StyledText text={"최고 속도"} style={{fontFamily: "gwtt"}}/>
                <StyledText text={groupRide.recordSpeedBest + "km/h"} weight="bold"
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
                src={Satisfaction}
                height="24px"
                style={{
                    objectFit: "cover",
                }}
            />
            <StyledText
                text="그래프 통계"
                size="18px"
                style={{
                    fontFamily: "gwtt",
                }}
            />
        </Box>
        <hr width={"85%"} style={{border: "solid 1px black"}}/>
        <Box height={"300px"} style={{marginBottom:"20px"}}>
            <RadarGraph data={groupRide.records.map((data) => ({
                nickname: data.nickname, 시간: data.recordTimeMinute,
                최고속력: data.recordSpeedBest,
                평균속력: data.recordSpeedAvg,
                총거리: data.totalDistance
            }))}/>
        </Box>
        <Box
            width="100%"
            pad={{ left: "30px", right: "20px" }}
            direction="row"
            align="end"
            gap="small"
        >
            <img
                src={RankTitle}
                height="24px"
                style={{
                    objectFit: "cover",
                }}
            />
            <StyledText
                text="거리순 랭킹"
                size="18px"
                style={{
                    fontFamily: "gwtt",
                }}
            />
        </Box>
        <hr width={"85%"} style={{border: "solid 1px black"}}/>
        <Box align={"center"}>
        <Box style={{
            width:"90%",
            overflow: "auto",
            height: "45vh",
            padding: "0px 10px",
        }}>
            {groupRide.records &&
                groupRide.records.map((d, idx) => {
                    return <RankBox record={d} key={idx} type={"dis"}/>;
                })}
        </Box>
        </Box>

    </Box>);
};
