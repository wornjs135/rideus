import React, { useCallback, useContext } from "react";
import { useEffect, useState } from "react";
import { Box } from "grommet";
import { StyledText } from "../components/Common";
import Button from "../components/Button";
import { useGeolocated } from "react-geolocated";
import PlayBtn from "../assets/images/play.png";
import PauseBtn from "../assets/images/pause.png";
import TotalBike from "../assets/images/totalRideBike.png";
import { Map, MapMarker, Polyline } from "react-kakao-maps-sdk";
import { AlertDialog, MapDialog } from "../components/AlertDialog";
import {
  useLocation,
  useNavigate,
  UNSAFE_NavigationContext as NavigationContext,
} from "react-router-dom";
import history from "../utils/history.js";
import { latlng } from "../utils/data";
import useWatchLocation from "../hooks/watchLocationHook";
import { distanceHandle, speedHandle, timeHandle } from "../utils/util";
import { ExitButton, PauseButton } from "../components/Buttons";

const geolocationOptions = {
  enableHighAccuracy: false,
  timeout: 500, // 1 min (1000 ms * 60 sec * 1 minute = 60 000ms)
  maximumAge: 0, // 24 hour
};

export const Ride = () => {
  const locations = useLocation();
  const [nowTime, setNowTime] = useState(0);
  const [mapData, setMapData] = useState({
    latlng: [],
    center: { lng: 127.002158, lat: 37.512847 },
  });

  const [data, setData] = useState({
    topSpeed: 0,
    avgSpeed: 0,
    totalDistance: 0,
  });

  // 코스 이름, 싱글 or 그룹, 추천코스 or 나만의 코스
  const { courseName, rideType, courseType, coordinates, checkPoints } =
    locations.state;
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [openMap, setOpenMap] = useState(false);
  const [riding, setRiding] = useState(true);
  const [when, setWhen] = useState(true);
  const [lastLocation, setLastLocation] = useState(null);
  const { coords, isGeolocationAvailable, isGeolocationEnabled } =
    useGeolocated({
      positionOptions: {
        enableHighAccuracy: true,
        maximumAge: 0,
        timeout: 500,
      },
      watchPosition: true,
    });
  // const { location, cancelLocationWatch, error } =
  //   useWatchLocation(geolocationOptions);
  const preventClose = (e) => {
    e.preventDefault();
    e.returnValue = "";
  };

  function getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2) {
    var R = 6371; // Radius of the earth in km
    var dLat = deg2rad(lat2 - lat1); // deg2rad below
    var dLon = deg2rad(lon2 - lon1);
    var a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(deg2rad(lat1)) *
        Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) *
        Math.sin(dLon / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c; // Distance in km
    d = Math.round(d * 1000);
    return d;
  }

  function deg2rad(deg) {
    return deg * (Math.PI / 180);
  }

  // function getDistance(lat1, lon1, lat2, lon2) {
  //   if (lat1 == lat2 && lon1 == lon2) return 0;

  //   var radLat1 = (Math.PI * lat1) / 180;
  //   var radLat2 = (Math.PI * lat2) / 180;
  //   var theta = lon1 - lon2;
  //   var radTheta = (Math.PI * theta) / 180;
  //   var dist =
  //     Math.sin(radLat1) * Math.sin(radLat2) +
  //     Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);
  //   if (dist > 1) dist = 1;

  //   dist = Math.acos(dist);
  //   dist = (dist * 180) / Math.PI;
  //   dist = dist * 60 * 1.1515 * 1.609344 * 1000;
  //   // dist = dist * 6371;
  //   if (dist < 100) dist = Math.round(dist / 10) * 10;
  //   else dist = Math.round(dist / 100) * 100;

  //   return dist;
  // }

  function useBlocker(blocker, when = true) {
    const { navigator } = useContext(NavigationContext);

    useEffect(() => {
      if (!when) {
        return;
      }
      const unblock = navigator.block((tx) => {
        const autoUnblockingTx = {
          ...tx,
          retry() {
            // Automatically unblock the transition so it can play all the way
            // through before retrying it. T O D O: Figure out how to re-enable
            // this block if the transition is cancelled for some reason.
            unblock();
            tx.retry();
          },
        };

        blocker(autoUnblockingTx);
      });

      // eslint-disable-next-line consistent-return
      return unblock;
    }, [navigator, blocker]);
  }
  const [confirmedNavigation, setConfirmedNavigation] = useState(false);

  const [i, setI] = useState(0);

  const handleBlockedNavigation = useCallback(
    (tx) => {
      if (!confirmedNavigation && tx.location.pathname !== locations.pathname) {
        confirmNavigation();
        setOpen(true);
        setLastLocation(tx);
        return false;
      }
      return true;
    },
    [confirmedNavigation, locations.pathname]
  );
  const confirmNavigation = useCallback(() => {
    setOpen(false);
    setWhen(false);
    setConfirmedNavigation(true);
  }, []);

  const unconfirmNavigation = useCallback(() => {
    setOpen(false);
    setWhen(true);
    setConfirmedNavigation(false);
  }, []);
  let idle = 1;
  useEffect(() => {
    // console.log("hello");
    // let i = 0.000001;
    window.addEventListener("beforeunload", preventClose);
    // let i = 0;
    // setNowTime(0);
    const rideId = setInterval(() => {
      if (riding && isGeolocationAvailable && isGeolocationEnabled) {
        console.log("location : ", coords);

        const gps = {
          lat: coords.latitude,
          lng: coords.longitude,
        };

        console.log("gps : ", gps);

        setMapData((prev) => {
          return {
            center: gps,
            latlng: [...prev.latlng, gps],
          };
        });

        if (mapData.latlng.length > 1) {
          console.log("data : ", data);

          let dis = getDistanceFromLatLonInKm(
            mapData.latlng.at(-1).lat,
            mapData.latlng.at(-1).lng,
            gps.lat,
            gps.lng
          );
          console.log("dis: ", dis);
          if (dis > 0) {
            setData((prev) => ({
              topSpeed: Math.max(prev.topSpeed, speedHandle(dis, idle)),
              avgSpeed: (prev.avgSpeed + speedHandle(dis, idle)) / 2,
              totalDistance: prev.totalDistance + dis,
            }));
            idle = 1;
          } else {
            idle = idle + 1;
            setData((prev) => ({
              topSpeed: prev.topSpeed,
              avgSpeed: prev.avgSpeed,
              totalDistance: prev.totalDistance,
            }));
          }
        }

        // setI((prev) => {
        //   return prev + 0.001;
        // });
      } else {
        // idle = idle + 1;
        setData((prev) => {
          return {
            topSpeed: prev.topSpeed,
            avgSpeed: prev.avgSpeed,
            totalDistance: prev.totalDistance,
          };
        });
      }
      // setI((prev) => {
      //   return prev + 0.0001;
      // });
      console.log(mapData.latlng);
    }, 1000);

    const timerId = setTimeout(() => {
      setNowTime((prev) => prev + 1);
    }, 1000);

    return () => {
      clearTimeout(timerId);
      clearInterval(rideId);
      // cancelLocationWatch();
      window.removeEventListener("beforeunload", preventClose);
    };
  });
  useBlocker(handleBlockedNavigation, when);

  return (
    <Box background="#439652">
      {/* 나만의 길 */}
      <Box align="center" margin={{ top: "30px", bottom: "12px" }}>
        <StyledText text={courseName} color="white" weight="bold" size="24px" />
      </Box>
      {/* 바디 부분 */}
      <Box
        align="center"
        height="90vh"
        round={{ size: "large", corner: "top" }}
        background="#ffffff"
        pad={{ top: "20px", bottom: "20px" }}
        border={{ color: "#ffffff", size: "small", side: "top" }}
      >
        {/* 카카오맵 */}
        <Box
          style={{ width: "85%", height: "40vh" }}
          onClick={() => {
            confirmNavigation();
            setOpenMap(true);
          }}
        >
          <Map
            center={mapData.center}
            isPanto={true}
            style={{ borderRadius: "25px", width: "100%", height: "100%" }}
          >
            {mapData.latlng && (
              <Polyline
                path={[mapData.latlng]}
                strokeWeight={5} // 선의 두께 입니다
                strokeColor={"#030ff1"} // 선의 색깔입니다
                strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                strokeStyle={"solid"} // 선의 스타일입니다
              />
            )}
            {coordinates && (
              <Polyline
                path={[coordinates]}
                strokeWeight={5} // 선의 두께 입니다
                strokeColor={"#5b60b8"} // 선의 색깔입니다
                strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                strokeStyle={"solid"} // 선의 스타일입니다
              />
            )}
            {checkPoints &&
              checkPoints.map((m, idx) => {
                return <MapMarker position={m} key={idx}></MapMarker>;
              })}
          </Map>
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
              <StyledText
                text={distanceHandle(data.totalDistance)}
                size="40px"
                weight="bold"
              />
              <StyledText text="총 이동거리" color="#979797" size="10px" />
            </Box>
            <StyledText
              text={data.totalDistance > 1000 ? "km" : "m"}
              color="#979797"
            />
          </Box>
          {/* 총 이동거리 끝 */}
          {/* 상세 데이터 시작 */}
          <Box direction="row" align="center" gap="medium">
            {/* 주행시간 */}
            <Box align="center">
              <StyledText
                text={timeHandle(nowTime)}
                weight="bold"
                size="18px"
              />
              <StyledText text="주행 시간" size="10px" />
            </Box>
            {/* 평균 속도 */}
            <Box align="center">
              <StyledText text={data.avgSpeed} weight="bold" size="18px" />
              <StyledText text="평균 속도" size="10px" />
            </Box>
            {/* 최고 속도 */}
            <Box align="center">
              <StyledText text={data.topSpeed} weight="bold" size="18px" />
              <StyledText text="최고 속도" size="10px" />
            </Box>
          </Box>
          {/* 상세데이터 끝 */}
        </Box>
        {/* 데이터 부분 끝 */}
        {/* 일시정지, 체크포인트 버튼 */}
        <Box width="90%">
          <Box direction="row" justify="center">
            {/* 일시정지 버튼 */}
            <PauseButton
              onClick={() => {
                if (riding === true) setRiding(false);
                else setRiding(true);
              }}
            >
              <img
                src={riding ? PlayBtn : PauseBtn}
                width="25px"
                height="25px"
              />
              <StyledText text="일시정지" color="white" />
            </PauseButton>
            {/* 체크 포인트 저장 버튼 */}
            <ExitButton
              onClick={() => {
                confirmNavigation();
                setOpen(true);
              }}
            >
              주행 종료
            </ExitButton>
          </Box>
          {/* 주행 종료 버튼 */}
          <Box direction="row"></Box>
        </Box>
      </Box>
      <MapDialog
        type="riding"
        open={openMap}
        handleClose={() => {
          unconfirmNavigation();
          setOpenMap(false);
        }}
        handleAction={() => {
          setOpen(true);
        }}
        map={
          <Map
            center={mapData.center}
            isPanto={true}
            style={{ width: "100%", height: "100%" }}
          >
            {mapData.latlng && (
              <Polyline
                path={[mapData.latlng]}
                strokeWeight={5} // 선의 두께 입니다
                strokeColor={"#030ff1"} // 선의 색깔입니다
                strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                strokeStyle={"solid"} // 선의 스타일입니다
              />
            )}
            {coordinates && (
              <Polyline
                path={[coordinates]}
                strokeWeight={5} // 선의 두께 입니다
                strokeColor={"#5b60b8"} // 선의 색깔입니다
                strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                strokeStyle={"solid"} // 선의 스타일입니다
              />
            )}
            {checkPoints &&
              checkPoints.map((m, idx) => {
                return <MapMarker position={m} key={idx}></MapMarker>;
              })}
          </Map>
        }
        cancel="뒤로가기"
        accept="주행종료"
        title={courseName}
      />
      <AlertDialog
        open={open}
        handleClose={() => {
          unconfirmNavigation();
          setOpen(false);
        }}
        handleAction={() => {
          // useBlocker(handleBlockedNavigation, false);
          navigate("/rideEnd", {
            state: {
              courseName: courseName,
              courseType: courseType,
              courseData: {
                latlng: mapData.latlng,
                topSpeed: data.topSpeed,
                avgSpeed: data.avgSpeed,
                nowTime: nowTime,
                totalDistance: data.totalDistance,
              },
            },
          });
        }}
        title="주행 종료"
        desc="주행을 종료하시겠습니까?"
        cancel="취소"
        accept="종료"
      />
    </Box>
  );
};
