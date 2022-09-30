import { Box } from "grommet";
import React, { useState } from "react";
import { StyledText } from "../components/Common";
import TotalBike from "../assets/images/totalRideBike.png";
import { Map, Polyline } from "react-kakao-maps-sdk";
import Button from "../components/Button";
import { useLocation, useNavigate } from "react-router-dom";
import { distanceHandle, timeHandle } from "../utils/util";

export const RideEnd = () => {
  const navigate = useNavigate();
  const location = useLocation();
  // courseData: {
  //   latlng: mapData.latlng,
  //   topSpeed: data.topSpeed,
  //   avgSpeed: data.avgSpeed,
  //   nowTime: data.nowTime,
  //   totalDistance: data.totalDistance,
  // },

  // state: {
  //   courseName: courseName,
  //   courseType: courseType,
  //   courseData: {
  //   courseName: courseName,
  //     latlng: mapData.latlng,
  //     topSpeed: data.topSpeed,
  //     avgSpeed: data.avgSpeed,
  //     nowTime: nowTime,
  //     totalDistance: data.totalDistance,
  //   },
  // },
  const { courseType, courseData } = location.state;
  const [data, setData] = useState(courseData);
  const [mapData, setMapData] = useState({
    latlng: [],
    center: { lng: 127.002158, lat: 37.512847 },
  });
  return (
    <Box background="#439652" align="center" justify="around">
      <StyledText
        text={courseData.courseName}
        color="white"
        size="20px"
        weight="bold"
        style={{ marginTop: "20px" }}
      />
      {/* 카카오맵 */}
      <Box width="80vw" height="40vh" margin={{ top: "20px", bottom: "20px" }}>
        <Map
          center={courseData.latlng[0]}
          isPanto={true}
          style={{ borderRadius: "25px", width: "100%", height: "100%" }}
        >
          {courseData.latlng && (
            <Polyline
              path={[courseData.latlng]}
              strokeWeight={5} // 선의 두께 입니다
              strokeColor={"#030ff1"} // 선의 색깔입니다
              strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
              strokeStyle={"solid"} // 선의 스타일입니다
            />
          )}
        </Map>
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
                text={distanceHandle(data.totalDistance)}
                size="40px"
                weight="bold"
                color="white"
              />
              <StyledText text="총 이동거리" size="10px" color="white" />
            </Box>
            <StyledText
              text={data.totalDistance > 1000 ? "km" : "m"}
              color="white"
            />
          </Box>
          {/* 총 이동거리 끝 */}
          {/* 상세 데이터 시작 */}
          <Box direction="row" align="center" gap="medium">
            {/* 주행시간 */}
            <Box align="center">
              <StyledText
                text={timeHandle(data.nowTime)}
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
            <Button
              BigGreen
              children={
                courseType === "my" ? "나만의 길 공유하기" : "리뷰 쓰기"
              }
              onClick={() => {
                courseType === "my"
                  ? navigate("/registerCourse", {
                      state: { courseData: courseData },
                    })
                  : navigate("/registerReview", {
                      state: { courseData: courseData },
                    });
              }}
            />
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
