import React, {useEffect,useState} from "react";
import {Box, Spinner} from "grommet";
import {RadarGraph} from "../components/RadarGraph";
import {StyledText} from "../components/Common";
import {RankBox} from "../components/RankBox";
import {RankProfile} from "../components/RankProfile";
import {useNavigate, useParams} from "react-router-dom";
import {getRecordWithGroup} from "../utils/api/recordApi";
import {expectTimeHandle, speedHandle} from "../utils/util";

export const RecentGroupRide = () => {
    const navigate = useNavigate();
    const [groupRide, setGroupRide] = useState([]);
    const [loading, setLoading] = useState(true);
    const {recordId} = useParams();
    useEffect(()=> {

        getRecordWithGroup(recordId,(data)=> {
            console.log(data);
            const {data:rec} = data;
            setGroupRide(rec);
            // console.log(rec.records);
            // console.log(rec.records.map(d => d.nickname));
            setLoading(false);
        });
        console.log(recordId);
    },[]);


    if (loading) return <Spinner />;
    else return (<Box width="100vw" justify="center" margin="0 auto" gap="small">

        <Box pad={{left: "20px"}} style={{marginTop: "5vw"}} onClick={() => navigate(-1)}>
            <StyledText text="Back" weight="bold" size="18px"/>
        </Box>

        <Box justify={"center"}>
            <StyledText text={"마포점 - 정서진"} weight={"bold"} size={"18px"}
                        style={{display: "block", textAlign: "center"}}/>
        </Box>

        <Box>
            <StyledText text={"그룹 roomId"} weight={"bold"}
                        style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
        </Box>

        <Box pad={{left: "20px"}}>
            <StyledText text="MY RECORD" weight="bold" size="18px"/>
        </Box>
        <Box direction={"row"} justify={"evenly"}>
            <div>
                <StyledText text={"주행 시간"} style={{textAlign: "center"}}/>
                <StyledText text={expectTimeHandle(groupRide.recordTimeMinute)} weight="bold"
                            style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
            </div>
            <div>
                <StyledText text={"평균 주행 속도"}/>
                <StyledText text={groupRide.recordSpeedAvg + "km/h"} weight="bold"
                            style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
            </div>
            <div>
                <StyledText text={"최고 속도"}/>
                <StyledText text={groupRide.recordSpeedBest + "km/h"} weight="bold"
                            style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
            </div>
        </Box>
        <hr width={"85%"} style={{border: "solid 1px black"}}/>
        <Box pad={{left: "20px"}}>
            <StyledText text="STATISTICS" weight="bold" size="18px"/>
        </Box>
        <Box height={"300px"}>
            <RadarGraph data={groupRide.records}/>
        </Box>
        <hr width={"85%"} style={{border: "solid 1px black"}}/>
        <Box pad={{left: "20px"}}>
            <StyledText text="RANKING" weight="bold" size="18px"/>
        </Box>
        <Box>
            <Box direction="row" justify="center" align="end">
                {/*<RankProfile record={data[1]}/>*/}
                {/*<RankProfile record={data[0]}/>*/}
                {/*<RankProfile record={data[2]}/>*/}
            </Box>
        </Box>

    </Box>);
};
