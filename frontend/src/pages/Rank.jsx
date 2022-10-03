import { textAlign } from "@mui/system";
import { Box, Grommet, Spinner, Tab, Tabs } from "grommet";
import React, { useEffect, useState } from "react";
import { StyledText } from "../components/Common";
import { RankBike } from "../components/RankBike";
import { RankBox } from "../components/RankBox";
import {
  getTotalRankBestSpeed,
  getTotalRankDistance,
  getTotalRankTime,
  getUserRankDistance,
  getUserRankSpeed,
  getUserRankTime,
} from "../utils/api/rankApi";

const theme = {
  tab: {
    active: { color: "#black", background: undefined },
    color: "#D7D7D7",

    border: {
      side: "bottom",
      size: "small",
      color: {
        dark: "accent-1",
        light: "#D7D7D7",
      },
      active: {
        color: {
          dark: "white",
          light: "#64CCBE",
        },
      },
      hover: {
        color: {
          dark: "white",
          light: "black",
        },
        // extend: undefined,
      },
    },
  },
};

export const Rank = () => {
  const [loading, setLoading] = useState(true);
  const [timeRanks, setTimeRanks] = useState([]);
  const [disRanks, setDisRanks] = useState([]);
  const [speedRanks, setSpeedRanks] = useState([]);
  const [type, setType] = useState("time");
  useEffect(() => {
    if (loading) {
      getTotalRankTime(
        (response) => {
          console.log("time : ", response);
          setTimeRanks(response.data);
        },
        (fail) => {
          console.log(fail);
        }
      );
      getTotalRankDistance(
        (response) => {
          console.log("dis : ", response);
          setDisRanks(response.data);
        },
        (fail) => {
          console.log(fail);
        }
      );
      getTotalRankBestSpeed(
        (response) => {
          console.log("speed : ", response);
          setSpeedRanks(response.data);
        },
        (fail) => {
          console.log(fail);
        }
      );
    }
    setLoading(false);
    return () => {
      setLoading(false);
    };
  }, []);
  if (loading) return <Spinner />;
  else
    return (
      <Box width="100%" align="center">
        <StyledText
          text="RANKING"
          size="20px"
          weight="bold"
          style={{ marginTop: "15px", marginBottom: "15px" }}
        />

        <Box
          width="100%"
          align="center"
          background="#E0F7F4"
          round={{ corner: "top", size: "large" }}
          pad={{ top: "15px" }}
        >
          {/* 123등 박스 */}
          {timeRanks && (
            <Box width="90%" direction="row" justify="center">
              {/* 1등 */}
              <RankBike
                rank={
                  type === "time"
                    ? timeRanks[0]
                    : type === "dis"
                    ? disRanks[0]
                    : speedRanks[0]
                }
                type={type}
              />
              {/* 2등 */}
              <RankBike
                rank={
                  type === "time"
                    ? timeRanks[1]
                    : type === "dis"
                    ? disRanks[1]
                    : speedRanks[1]
                }
                type={type}
              />
              {/* 3등 */}
              <RankBike
                rank={
                  type === "time"
                    ? timeRanks[2]
                    : type === "dis"
                    ? disRanks[2]
                    : speedRanks[2]
                }
                type={type}
              />
            </Box>
          )}

          {/* 나머지 사람들 */}
          <Box
            width="90%"
            background="#FFFFFF"
            round={{ corner: "top", size: "large" }}
          >
            {/* 탭 */}
            <Box margin={{ top: "15px" }}>
              <Grommet theme={theme}>
                <Tabs>
                  <Tab
                    title="시간"
                    style={{
                      fontWeight: "bold",
                      fontSize: "16px",
                      width: "100px",
                      textAlign: "center",
                    }}
                    onClick={() => {
                      setType("time");
                    }}
                  >
                    <Box pad="medium" overflow="scroll" height="45vh">
                      {timeRanks &&
                        timeRanks.map((d, idx) => {
                          return <RankBox record={d} key={idx} type="time" />;
                        })}
                    </Box>
                  </Tab>
                  <Tab
                    title="거리"
                    style={{
                      fontWeight: "bold",
                      fontSize: "16px",
                      width: "100px",
                      textAlign: "center",
                    }}
                    onClick={() => {
                      setType("dis");
                    }}
                  >
                    <Box pad="medium" overflow="scroll" height="45vh">
                      {disRanks &&
                        disRanks.map((d, idx) => {
                          return <RankBox record={d} key={idx} type="dis" />;
                        })}
                    </Box>
                  </Tab>
                  <Tab
                    title="평균 속력"
                    style={{
                      fontWeight: "bold",
                      fontSize: "16px",
                      width: "100px",
                      textAlign: "center",
                    }}
                    onClick={() => {
                      setType("speed");
                    }}
                  >
                    <Box pad="medium" overflow="scroll" height="45vh">
                      {speedRanks &&
                        speedRanks.map((d, idx) => {
                          return <RankBox record={d} key={idx} type="speed" />;
                        })}
                    </Box>
                  </Tab>
                </Tabs>
              </Grommet>
            </Box>
          </Box>
        </Box>
      </Box>
    );
};
