import React from "react";
import { useEffect, useState } from "react";
import { Box } from "grommet";
import { StyledText } from "../components/Common";
import Button from "../components/Button";
import { useGeolocated } from "react-geolocated";
import PlayBtn from "../assets/images/play.png";
import TotalBike from "../assets/images/totalRideBike.png";
import { Map, MapMarker, Polyline } from "react-kakao-maps-sdk";
export const Ride = () => {
  const [data, setData] = useState({
    latlng: [],
    center: { lng: 127.002158, lat: 37.512847 },
  });
  const { coords, isGeolocationAvailable, isGeolocationEnabled } =
    useGeolocated({
      positionOptions: {
        enableHighAccuracy: true,
        maximumAge: 0,
        timeout: Infinity,
      },
      watchPosition: true,
    });
  useEffect(() => {
    // let i = 0.000001;
    const timerId = setInterval(() => {
      if (isGeolocationAvailable && isGeolocationEnabled) {
        console.log(coords);
        const gps = {
          lat: coords.latitude,
          lng: coords.longitude,
        };
        console.log(gps);
        setData((prev) => {
          return {
            center: gps,
          };
        });
        // setI((prev) => {
        //   return prev + 0.0001;
        // });
      }
    }, 1000);
    return () => clearInterval(timerId);
  });

  return (
    <Box background="#439652">
      {/* 나만의 길 */}
      <Box align="center" margin={{ top: "30px", bottom: "12px" }}>
        <StyledText text="나만의 길" color="white" weight="bold" size="24px" />
      </Box>
      {/* 바디 부분 */}
      <Box
        align="center"
        justify="between"
        height="90vh"
        round={{ size: "large", corner: "top" }}
        background="#ffffff"
        pad={{ top: "20px" }}
        border={{ color: "#ffffff", size: "small", side: "top" }}
      >
        {/* 카카오맵 */}
        <Box
          style={{ width: "85%", height: "500px" }}
          onClick={() => {
            console.log("map clicked!!!");
          }}
        >
          <Map
            center={data.center}
            isPanto={true}
            style={{ borderRadius: "25px", width: "100%", height: "100%" }}
          ></Map>
        </Box>
        {/* 데이터 부분 시작 */}
        <Box direction="row" justify="between" width="85%">
          {/* 총 이동거리 시작 */}
          <Box direction="row" align="center">
            {/* 자전거 이미지 */}
            <img src={TotalBike} alt="" />
            {/* 총 이동거리 텍스트 */}
            <Box
              align="center"
              style={{ marginLeft: "10px", marginRight: "5px" }}
            >
              <StyledText text="21.3" size="40px" weight="bold" />
              <StyledText text="총 이동거리" color="#979797" size="10px" />
            </Box>
            <StyledText text="km" color="#979797" />
          </Box>
          {/* 총 이동거리 끝 */}
          {/* 상세 데이터 시작 */}
          <Box direction="row" align="center" gap="medium">
            {/* 주행시간 */}
            <Box align="center">
              <StyledText text="12:51" weight="bold" size="18px" />
              <StyledText text="주행 시간" size="10px" />
            </Box>
            {/* 평균 속도 */}
            <Box align="center">
              <StyledText text="21:05" weight="bold" size="18px" />
              <StyledText text="평균 속도" size="10px" />
            </Box>
            {/* 최고 속도 */}
            <Box align="center">
              <StyledText text="35:12" weight="bold" size="18px" />
              <StyledText text="최고 속도" size="10px" />
            </Box>
          </Box>
          {/* 상세데이터 끝 */}
        </Box>
        {/* 데이터 부분 끝 */}
        {/* 일시정지, 체크포인트 버튼 */}
        <Box width="90%">
          <Box direction="row">
            {/* 일시정지 버튼 */}
            <Button
              children={<img src={PlayBtn} />}
              Custom
              color="#439652"
              textColor="white"
              bWidth="20%"
              bHeight="57px"
            />
            {/* 체크 포인트 저장 버튼 */}
            <Button
              children="체크 포인트 저장"
              Custom
              color="#439652"
              textColor="white"
              bWidth="80%"
              bHeight="57px"
              fontSize="16px"
              fontWeight="bold"
            />
          </Box>
          {/* 주행 종료 버튼 */}
          <Box direction="row">
            <Button
              Custom
              color="#F29393"
              textColor="white"
              bWidth="100%"
              bHeight="57px"
              fontSize="16px"
              fontWeight="bold"
              children="주행 종료"
            />
          </Box>
        </Box>
      </Box>
    </Box>
  );
};
