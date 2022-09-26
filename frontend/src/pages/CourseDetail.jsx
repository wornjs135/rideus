import { Box, Spinner } from "grommet";
import React, { useEffect, useState } from "react";
import { Map, MapMarker, Polyline } from "react-kakao-maps-sdk";
import { CourseMap, StarBox, StyledText } from "../components/Common";
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
import { BootstrapButton } from "../components/Buttons";

export const CourseDetail = () => {
  const location = useLocation();
  const { courseName } = location.state;

  const [open, setOpen] = useState(true);
  const [start, setStart] = useState(false);
  const [openMap, setOpenMap] = useState(false);
  const [bmk, setBmk] = useState(false);
  const [score, setScore] = useState("5");
  const [mapData, setMapData] = useState({
    latlng: [],
    center: { lng: 127.002158, lat: 37.512847 },
  });
  const [clicked, setClicked] = useState([false, false, false, false, false]);
  const starView = parseFloat(score) * 22.8;
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
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
    if (loading) {
      setMapData({
        latlng: courseData,
        center: { lng: 127.002158, lat: 37.512847 },
      });
      setReviews([
        { score: score },
        { score: score },
        { score: score },
        { score: score },
        { score: score },
        { score: score },
        { score: score },
        { score: score },
        { score: score },
        { score: score },
        { score: score },
      ]);
      setLoading(false);
    }
  }, []);

  const handleStarClick = (index) => {
    let clickStates = [...clicked];
    for (let i = 0; i < 5; i++) {
      clickStates[i] = i <= index ? true : false;
    }
    setClicked(clickStates);
  };
  const array = [0, 1, 2, 3, 4];
  if (loading) return <Spinner />;
  else
    return (
      <Box align="center" width="100%" height="86%">
        <Box direction="row" justify="center">
          <StyledText text={courseName} size="24px" weight="bold" />
        </Box>
        <Box
          width="90%"
          height="35vh"
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
            <MapMarker position={mapData.latlng[0]}>
              <div style={{ color: "#000" }}>시작점</div>
            </MapMarker>
            {mapData.latlng[0].lat ===
              mapData.latlng[mapData.latlng.length - 1].lat &&
            mapData.latlng[0].lng ===
              mapData.latlng[mapData.latlng.length - 1].lng ? (
              <MapMarker position={mapData.latlng[0]}>
                <div style={{ color: "#000" }}>시작, 종점</div>
              </MapMarker>
            ) : (
              <MapMarker position={mapData.latlng[mapData.latlng.length - 1]}>
                <div style={{ color: "#000" }}>종점</div>
              </MapMarker>
            )}
          </Map>
        </Box>
        <Box direction="row" justify="between" width="90%">
          <StarBox score={score} starView={starView} />
          <Box direction="row" align="center">
            {/* <Button
            children="북마크"
            // onClick={() => {
            //   check();
            // }}
          /> */}
            <img
              src={bmk ? Bookmark : BookmarkBlank}
              width="25px"
              height="25px"
              onClick={() => {
                if (bmk === true) setBmk(false);
                else setBmk(true);
              }}
            />
          </Box>
        </Box>

        <Box></Box>
        <BootstrapButton
          onClick={() => {
            setStart(true);
          }}
        >
          주행 시작
        </BootstrapButton>

        <CourseReviewRank
          open={open}
          onDismiss={() => {
            setOpen(false);
          }}
          reviews={reviews}
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
            <CourseMap
              course={mapData.latlng}
              width={"100%"}
              height={"100%"}
              marker1={
                <MapMarker position={mapData.latlng[0]}>
                  <div style={{ color: "#000" }}>시작점</div>
                </MapMarker>
              }
              marker={
                mapData.latlng[0].lat ===
                  mapData.latlng[mapData.latlng.length - 1].lat &&
                mapData.latlng[0].lng ===
                  mapData.latlng[mapData.latlng.length - 1].lng ? (
                  <MapMarker position={mapData.latlng[0]}>
                    <div style={{ color: "#000" }}>시작, 종점</div>
                  </MapMarker>
                ) : (
                  <MapMarker
                    position={mapData.latlng[mapData.latlng.length - 1]}
                  >
                    <div style={{ color: "#000" }}>종점</div>
                  </MapMarker>
                )
              }
              infoMarkers={mapData.latlng.map((m, idx) => {
                return <MapMarker position={mapData.latlng[0]}></MapMarker>;
              })}
            />
          }
          cancel="뒤로가기"
          accept="주행시작"
          title={courseName}
        />
      </Box>
    );
};
