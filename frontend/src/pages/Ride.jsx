import React, { useCallback, useContext } from "react";
import { useEffect, useState } from "react";
import { Box } from "grommet";
import { StyledText } from "../components/Common";
import Button from "../components/Button";
import { useGeolocated } from "react-geolocated";
import PlayBtn from "../assets/images/play.png";
import PauseBtn from "../assets/images/pause.png";
import LinkBtn from "../assets/images/link.png";
import TotalBike from "../assets/images/totalRideBike.png";
import GroupBike from "../assets/images/groupBike.png";
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
import SockJS from "sockjs-client";
import * as StompJs from "@stomp/stompjs";
import { finishRidding, saveCoordinatesDuringRide } from "../utils/api/rideApi";
import { TextField, ThemeProvider } from "@mui/material";
import { theme } from "./CourseList";

// const geolocationOptions = {
//   enableHighAccuracy: false,
//   timeout: 500, // 1 min (1000 ms * 60 sec * 1 minute = 60 000ms)
//   maximumAge: 0, // 24 hour
// };
var client = null;
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
  const {
    courseName,
    rideType,
    courseType,
    coordinates,
    checkPoints,
    recordId,
    courseId,
    roomInfo,
  } = locations.state;

  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [openMap, setOpenMap] = useState(false);
  const [riding, setRiding] = useState(true);
  const [when, setWhen] = useState(true);
  const [rideMembers, setRideMembers] = useState({ members: [] });
  const [lastLocation, setLastLocation] = useState(null);
  const [confirmedNavigation, setConfirmedNavigation] = useState(false);
  const { coords, isGeolocationAvailable, isGeolocationEnabled } =
    useGeolocated({
      positionOptions: {
        enableHighAccuracy: true,
        maximumAge: 0,
        timeout: 500,
      },
      watchPosition: true,
    });

  // 닫기 방지
  const preventClose = (e) => {
    e.preventDefault();
    e.returnValue = "";
  };

  // 웹소켓 구독
  const subscribe = () => {
    if (client != null) {
      console.log("subs!!!!!!!!!");
      client.subscribe("/sub/ride/room/" + roomInfo.rideRoomId, (response) => {
        console.log(response);
        const data = JSON.parse(response.body);
        rideMembers.members[data.memberId] = data;
        setRideMembers({ ...rideMembers });
      });
    }
  };

  //웹소켓 위치 발행
  const publishLocation = (lat, lng) => {
    if (client != null) {
      client.publish({
        destination: "/pub/ride/group",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("accessToken"),
        },
        body: JSON.stringify({
          messageType: "CURRENT_POSITION",
          rideRoomId: roomInfo.rideRoomId,
          lat: lat,
          lng: lng,
        }),
      });
    }
  };

  //웹소켓 초기화
  const initSocketClient = () => {
    client = new StompJs.Client({
      brokerURL: "wss://j7a603.p.ssafy.io/api/ws-stomp",
      connectHeaders: {
        Authorization: "Bearer " + localStorage.getItem("accessToken"),
      },
      webSocketFactory: () => {
        return SockJS("https://j7a603.p.ssafy.io/api/ws-stomp");
      },
      debug: (str) => {
        console.log("stomp debug!!!", str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,

      onStompError: (frame) => {
        // Will be invoked in case of error encountered at Broker
        // Bad login/passcode typically will cause an error
        // Complaint brokers will set `message` header with a brief message. Body may contain details.
        // Compliant brokers will terminate the connection after any error
        console.log("Broker reported error: " + frame.headers["message"]);
        console.log("Additional details: " + frame.body);
        // client.deactivate();
      },
    });

    // 웹소켓 초기 연결
    client.onConnect = (frame) => {
      console.log("client init !!! ", frame);
      if (client != null)
        client.publish({
          destination: "/pub/ride/group",
          headers: {
            Authorization: "Bearer " + localStorage.getItem("accessToken"),
          },
          body: JSON.stringify({
            messageType: "ENTER",
            rideRoomId: roomInfo.rideRoomId,
          }),
        });
      subscribe();
    };

    client.activate();
  };

  // 웹소켓 연결해제
  const disConnect = () => {
    if (client != null) {
      if (client.connected) client.deactivate();
    }
  };

  // 두 좌표간 거리 계산
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

  // 각도를 라디안으로 변환
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

  // 뒤로가기 방지
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

  // 이동 방지
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

  // 방지 해제
  const confirmNavigation = useCallback(() => {
    setOpen(false);
    setWhen(false);
    setConfirmedNavigation(true);
  }, []);

  // 주행 종료 핸들
  const handleRideFinish = () => {
    saveCoordinatesDuringRide(
      recordId,
      {
        coordinates: mapData.latlng,
      },
      (response) => {
        console.log(response);
      },
      (fail) => {
        console.log(fail);
      }
    ).then(
      finishRidding(
        rideType,
        {
          courseId: courseId === undefined ? null : courseId,
          distance: data.totalDistance,
          recordId: recordId,
          rideRoomId: roomInfo === undefined ? null : roomInfo.rideRoomId,
          speedAvg: data.avgSpeed,
          speedBest: data.topSpeed,
          time: nowTime,
          timeMinute: parseInt(nowTime / 60),
        },
        (response) => {
          console.log(response);
          // // 나만의 코스 주행
          // if (courseType === "my") {

          // }
          // // 추천 코스 주행
          // else if (courseType === "course") {

          // }
        },
        (fail) => {
          console.log(fail);
        }
      ).then(
        navigate("/rideEnd", {
          state: {
            courseType: courseType,
            courseData: {
              recordId: recordId,
              courseId: courseId,
              courseName: courseName,
              latlng: mapData.latlng,
              topSpeed: data.topSpeed,
              avgSpeed: data.avgSpeed,
              nowTime: nowTime,
              totalDistance: data.totalDistance,
            },
          },
        })
      )
    );
  };

  // 나가기 방지 다시 적용
  const unconfirmNavigation = useCallback(() => {
    setOpen(false);
    setWhen(true);
    setConfirmedNavigation(false);
  }, []);
  let idle = 1;

  // 그룹 주행 공유하기
  const sharePage = () => {
    window.navigator.share({
      title: `RideUs - ${roomInfo.nickname}님의 그룹 주행`,
      text: `${courseName}`,
      url: `https://j7a603.p.ssafy.io/groupRide?courseId=${courseId}&rideRoomId=${roomInfo.rideRoomId}&nickname=${roomInfo.nickname}`,
    });
  };

  // 시간 핸들 useEffect
  useEffect(() => {
    const timerId = setInterval(() => {
      setNowTime((prev) => prev + 1);
    }, 1000);

    return () => {
      clearInterval(timerId);
      // cancelLocationWatch();
      // window.removeEventListener("beforeunload", preventClose);
    };
  });

  useEffect(() => {
    if (rideType === "group") {
      initSocketClient();
    }

    return () => {
      if (rideType === "group") {
        disConnect();
      }
    };
  }, []);

  // 거리, 데이터 핸들 useEffect
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
        // 이전이랑 위치가 같을 때
        if (
          mapData.latlng.length > 0 &&
          mapData.latlng.at(-1).lat === gps.lat &&
          mapData.latlng.at(-1).lng === gps.lng
        ) {
          idle = idle + 1;
        } else {
          setMapData((prev) => {
            return {
              center: gps,
              latlng: [...prev.latlng, gps],
            };
          });
          // 위치가 1개 초과로 저장되었을 때 거리 계산
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
            }
            // idle = 1;
          }
        }

        // setI((prev) => {
        //   return prev + 0.001;
        // });
        // 웹소켓 발행
        if (client != null && rideType === "group") {
          publishLocation(gps.lat, gps.lng);
        }
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
      // console.log(mapData.latlng);
    }, 1000);

    return () => {
      clearInterval(rideId);
      // cancelLocationWatch();

      window.removeEventListener("beforeunload", preventClose);
    };
  });
  useBlocker(handleBlockedNavigation, when);

  return (
    <Box background="#64CCBE">
      {/* 나만의 길 */}
      <Box
        align="center"
        margin={{ top: "30px", bottom: "12px" }}
        direction="row"
        justify="around"
      >
        <Box width="50px"></Box>
        <Box>
          <StyledText
            text={courseName}
            color="white"
            weight="bold"
            size="24px"
          />
        </Box>
        <Box width="50px">
          {rideType === "group" && (
            <img
              src={LinkBtn}
              width="29px"
              height="29px"
              onClick={() => {
                sharePage();
              }}
            />
          )}
        </Box>
      </Box>
      {rideType === "group" && (
        <Box align="center" margin={{ bottom: "12px" }}>
          <StyledText
            text={roomInfo.nickname + "님의 그룹"}
            color="white"
            weight="bold"
            size="16px"
          />
        </Box>
      )}

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
            {rideMembers &&
              rideMembers.members.map((member, idx) => {
                return (
                  <MapMarker
                    position={{ lat: member.lat, lng: member.lng }}
                    key={idx}
                  >
                    {" "}
                    <div style={{ color: "#000" }}>{member.nickname}</div>
                  </MapMarker>
                );
              })}
          </Map>
        </Box>
        {/* 데이터 부분 시작 */}
        <Box direction="row" justify="between" width="85%">
          {/* 총 이동거리 시작 */}
          <Box direction="row" align="center">
            {/* 자전거 이미지 */}
            <img src={rideType === "single" ? TotalBike : GroupBike} alt="" />
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
              <StyledText
                text={parseFloat(data.avgSpeed).toFixed(1)}
                weight="bold"
                size="18px"
              />
              <StyledText text="평균 속도" size="10px" />
            </Box>
            {/* 최고 속도 */}
            <Box align="center">
              <StyledText
                text={parseFloat(data.topSpeed).toFixed(1)}
                weight="bold"
                size="18px"
              />
              <StyledText text="최고 속도" size="10px" />
            </Box>
          </Box>
          {/* 상세데이터 끝 */}
        </Box>
        {/* 데이터 부분 끝 */}
        {/* 주행 중인 사람들 */}
        {rideType === "group" && (
          <Box
            width="90%"
            pad="medium"
            margin={{ top: "20px", bottom: "10px" }}
            style={{
              borderRadius: "10px",
              border: "2px solid #64CCBE",
              fontWeight: "bold",
              position: "relative",
            }}
          >
            <StyledText
              text={roomInfo.nickname + "님의 그룹"}
              weight="bold"
              style={{
                background: "white",
                position: "absolute",
                zIndex: 500,
                top: -14,
                padding: "0 5px",
              }}
            />
            {rideMembers.members
              .map((m) => {
                return m.nickname;
              })
              .join(" ")}
          </Box>
        )}
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
            {rideMembers &&
              rideMembers.members.map((member, idx) => {
                return (
                  <MapMarker
                    position={{ lat: member.lat, lng: member.lng }}
                    key={idx}
                  >
                    <div style={{ color: "#000" }}>{member.nickname}</div>
                  </MapMarker>
                );
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
          handleRideFinish();
        }}
        title="주행 종료"
        desc="주행을 종료하시겠습니까?"
        cancel="취소"
        accept="종료"
      />
    </Box>
  );
};
