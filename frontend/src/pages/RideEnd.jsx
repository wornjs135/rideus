import { Box } from "grommet";
import React, { useState } from "react";
import { StyledText } from "../components/Common";
import TotalBike from "../assets/images/totalRideBike.png";
import { Map } from "react-kakao-maps-sdk";
import Button from "../components/Button";
import { useNavigate } from "react-router-dom";

export const RideEnd = () => {
  const navigate = useNavigate();

  const [data, setData] = useState({
    topSpeed: 35.12,
    avgSpeed: 21.05,
    nowTime: "12:51",
    totalDistance: 21.3,
  });
  const [mapData, setMapData] = useState({
    latlng: [],
    center: { lng: 127.002158, lat: 37.512847 },
  });
  return (
    <Box background="#439652" align="center">
      <StyledText
        text="나만의 길"
        color="white"
        size="20px"
        weight="bold"
        style={{ marginTop: "20px" }}
      />
      {/* 카카오맵 */}
      <Box width="70vw" height="50vh" margin={{ top: "20px", bottom: "20px" }}>
        <Map
          center={mapData.center}
          isPanto={true}
          style={{ borderRadius: "25px", width: "100%", height: "100%" }}
        ></Map>
      </Box>
      {/* 하단 데이터 부분 */}
      <Box
        align="center"
        justify="between"
        width="100%"
        round={{ size: "large", corner: "top" }}
        background="#FFBB00"
        border={{ color: "#FFBB00", size: "small", side: "top" }}
      >
        {/* 주행 기록 데이터 */}
        <Box direction="row" justify="between" width="85%">
          {/* 총 이동거리 시작 */}
          <Box direction="row" align="center">
            {/* 자전거 이미지 */}
            {/* 총 이동거리 텍스트 */}
            <Box
              align="center"
              style={{ marginLeft: "10px", marginRight: "5px" }}
            >
              <StyledText
                text={data.totalDistance}
                size="40px"
                weight="bold"
                color="white"
              />
              <StyledText text="총 이동거리" size="10px" color="white" />
            </Box>
            <StyledText text="km" color="white" />
          </Box>
          {/* 총 이동거리 끝 */}
          {/* 상세 데이터 시작 */}
          <Box direction="row" align="center" gap="medium">
            {/* 주행시간 */}
            <Box align="center">
              <StyledText
                text={data.nowTime}
                weight="bold"
                size="18px"
                color="white"
              />
              <StyledText text="주행 시간" size="10px" color="white" />
            </Box>
            {/* 평균 속도 */}
            <Box align="center">
              <StyledText
                text={data.avgSpeed}
                weight="bold"
                size="18px"
                color="white"
              />
              <StyledText text="평균 속도" size="10px" color="white" />
            </Box>
            {/* 최고 속도 */}
            <Box align="center">
              <StyledText
                text={data.topSpeed}
                weight="bold"
                size="18px"
                color="white"
              />
              <StyledText text="최고 속도" size="10px" color="white" />
            </Box>
          </Box>
          {/* 상세데이터 끝 */}
        </Box>
        {/* 상세 데이터 박스 끝 */}
        <Box
          width="100%"
          background="white"
          align="center"
          margin={{ top: "15px" }}
          pad={{ top: "30px" }}
        >
          <Box width="80%" justify="around">
            <Box align="start">
              <StyledText text="즐거운 시간 보내셨나요?" size="20px" />
            </Box>
            <Button BigGreen children="나만의 길 공유하기" />
            <Button
              BigWhite
              children="나중에 할께요 / 건너뛰기"
              onClick={() => {
                navigate("/");
              }}
            />
          </Box>
        </Box>
      </Box>
    </Box>
  );
};
