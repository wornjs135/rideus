import { Box } from "grommet";
import React, { useEffect, useState } from "react";
import { Map, Polyline } from "react-kakao-maps-sdk";
import { StyledText } from "../components/Common";
import Stars from "../assets/images/stars.png";
import StarsBlank from "../assets/images/stars_blank.png";
import Bookmark from "../assets/images/bookmark.png";
import BookmarkBlank from "../assets/images/bookmark_blank.png";
import Button from "../components/Button";
import { CourseReviewRank } from "../components/CourseReviewRank";
import { MapDialog, RideDialog } from "../components/AlertDialog";
import { useLocation } from "react-router-dom";
import { latlng as courseData } from "../utils/data";
import { checkNickname } from "../utils/api/testApi";

export const CourseDetail = () => {
  const location = useLocation();
  const { courseName } = location.state;

  const [open, setOpen] = useState(true);
  const [start, setStart] = useState(false);
  const [openMap, setOpenMap] = useState(false);
  const [score, setScore] = useState("3.58");
  const [mapData, setMapData] = useState({
    latlng: [],
    center: { lng: 127.002158, lat: 37.512847 },
  });
  const [clicked, setClicked] = useState([false, false, false, false, false]);
  const starView = parseFloat(score) * 22.8;
  // const check = () => {
  //   checkNickname(
  //     "hi",
  //     (response) => {
  //       console.log(response);
  //     },
  //     (fail) => {
  //       console.log(fail);
  //     }
  //   );
  // };
  useEffect(() => {
    setMapData({
      latlng: courseData,
      center: { lng: 127.002158, lat: 37.512847 },
    });
  }, []);

  const handleStarClick = (index) => {
    let clickStates = [...clicked];
    for (let i = 0; i < 5; i++) {
      clickStates[i] = i <= index ? true : false;
    }
    setClicked(clickStates);
  };
  const array = [0, 1, 2, 3, 4];
  return (
    <Box align="center" width="100%">
      <Box direction="row" justify="center">
        <StyledText text={courseName} size="24px" weight="bold" />
      </Box>
      <Box
        width="90%"
        height="60vh"
        onClick={() => {
          setOpenMap(true);
        }}
      >
        <Map
          center={mapData.center}
          isPanto={true}
          style={{ borderRadius: "25px", width: "100%", height: "100%" }}
        >
          <Polyline
            path={[mapData.latlng]}
            strokeWeight={5} // 선의 두께 입니다
            strokeColor={"#030ff1"} // 선의 색깔입니다
            strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle={"solid"} // 선의 스타일입니다
          />
        </Map>
      </Box>
      <Box direction="row" justify="between" width="90%">
        <Box direction="row" align="center">
          <StyledText text={score} weight="bold" size="19px" />
          <Box style={{ position: "relative", marginTop: "10px" }}>
            <Box
              // align="center"
              style={{
                width: starView,
                marginLeft: "15px",
                height: "28px",
                overflow: "hidden",
              }}
            >
              <img
                className="pointOfStar"
                alt="별"
                src={Stars}
                style={{
                  height: "28px",
                  width: "114px",
                }}
              />
            </Box>
            <img
              className="backgrdoundStar"
              alt="별"
              src={StarsBlank}
              style={{
                position: "absolute",
                marginLeft: "15px",
                width: "114px",
                height: "28px",
              }}
            />
          </Box>
        </Box>
        <Box direction="row" align="center">
          <Button
            children="북마크"
            // onClick={() => {
            //   check();
            // }}
          />
          <img src={Bookmark} width="20px" height="20px" />
        </Box>
      </Box>
      <Button
        BigGreen
        children="주행 시작"
        onClick={() => {
          setStart(true);
        }}
      />
      <CourseReviewRank
        open={open}
        onDismiss={() => {
          setOpen(false);
        }}
      />
      <RideDialog
        open={start}
        handleClose={() => {
          setStart(false);
        }}
        title={"마포점-정서진"}
      />
      <MapDialog
        type="detail"
        open={openMap}
        handleClose={() => {
          setOpenMap(false);
        }}
        handleAction={() => {
          setStart(true);
        }}
        map={
          <Map
            center={mapData.center}
            isPanto={true}
            style={{ width: "100%", height: "100%" }}
          >
            <Polyline
              path={[mapData.latlng]}
              strokeWeight={5} // 선의 두께 입니다
              strokeColor={"#030ff1"} // 선의 색깔입니다
              strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
              strokeStyle={"solid"} // 선의 스타일입니다
            />
          </Map>
        }
        cancel="뒤로가기"
        accept="주행시작"
        title={courseName}
      />
    </Box>
  );
};
