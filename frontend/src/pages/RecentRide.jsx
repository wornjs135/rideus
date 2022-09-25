import React from "react";
import {Box} from "grommet";
import {RadarGraph} from "../components/RadarGraph";
import {StyledText} from "../components/Common";
import {RankBox} from "../components/RankBox";
import {RankProfile} from "../components/RankProfile";

export const RecentRide = () => {

    const data = [
        {rank: 1, name: "김싸피", time: "2h 5m"},
        {rank: 2, name: "박삼성", time: "2h 13m"},
        {rank: 3, name: "이갤럭시", time: "2h 15m"},
        {rank: 4, name: "김싸피", time: "2h 5m"},
        {rank: 5, name: "박삼성", time: "2h 13m"},
        {rank: 6, name: "이갤럭시", time: "2h 15m"},
        {rank: 7, name: "김싸피", time: "2h 5m"},
        {rank: 8, name: "박삼성", time: "2h 13m"},
        {rank: 9, name: "이갤럭시", time: "2h 15m"},
        {rank: 10, name: "김싸피", time: "2h 5m"},
        {rank: 11, name: "박삼성", time: "2h 13m"},
        {rank: 12, name: "이갤럭시", time: "2h 15m"},
    ];

    return <Box width="100vw" justify="center" margin="0 auto" gap="small">

        <Box pad={{left: "20px"}} style={{marginTop: "5vw"}}>
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
                <StyledText text={"2h 10m"} weight="bold"
                            style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
            </div>
            <div>
                <StyledText text={"평균 주행 속도"}/>
                <StyledText text={"30km/h"} weight="bold"
                            style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
            </div>
            <div>
                <StyledText text={"최고 속도"}/>
                <StyledText text={"42km/h"} weight="bold"
                            style={{display: "block", textAlign: "center", marginTop: "3vw"}}/>
            </div>
        </Box>
        <hr width={"85%"} style={{border: "solid 1px black"}}/>
        <Box pad={{left: "20px"}}>
            <StyledText text="STATISTICS" weight="bold" size="18px"/>
        </Box>
        <Box height={"300px"}>
            <RadarGraph data={[
                {
                    "taste": "fruity",
                    "chardonay": 82,
                    "carmenere": 99,
                    "syrah": 95
                },
                {
                    "taste": "bitter",
                    "chardonay": 20,
                    "carmenere": 111,
                    "syrah": 40
                },
                {
                    "taste": "heavy",
                    "chardonay": 59,
                    "carmenere": 113,
                    "syrah": 25
                },
                {
                    "taste": "strong",
                    "chardonay": 114,
                    "carmenere": 96,
                    "syrah": 111
                },
                {
                    "taste": "sunny",
                    "chardonay": 37,
                    "carmenere": 56,
                    "syrah": 46
                }
            ]}/>
        </Box>
        <hr width={"85%"} style={{border: "solid 1px black"}}/>
        <Box pad={{left: "20px"}}>
            <StyledText text="RANKING" weight="bold" size="18px"/>
        </Box>
        <Box>
            <Box direction="row" justify="center" align="end">
                <RankProfile record={data[1]}/>
                <RankProfile record={data[0]}/>
                <RankProfile record={data[2]}/>
            </Box>
        </Box>

    </Box>
};
