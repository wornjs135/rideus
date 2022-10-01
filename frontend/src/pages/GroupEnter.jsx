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
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { latlng as courseData } from "../utils/data";
import { checkNickname } from "../utils/api/testApi";
import { BootstrapButton } from "../components/Buttons";
import { ChooseSoloGroupBar } from "../components/ChooseRideTypeBar";
import { getCourseDetail } from "../utils/api/courseApi";
import { getCourseRankTime } from "../utils/api/rankApi";
import { expectTimeHandle } from "../utils/util";
import styled from "styled-components";

import BackBtn from "../assets/images/backButton.png";
import { getCourseNearInfo } from "../utils/api/nearApi";
import { getCourseAllReview } from "../utils/api/reviewApi";
import { deleteBookmark, makeBookmark } from "../utils/api/bookmarkApi";
import { getGroupRoomInfo, startRidding } from "../utils/api/rideApi";
import { BackButton, Blank } from "./CourseDetail";
export const GroupEnter = () => {
  const navigate = useNavigate();
  const [open, setOpen] = useState(true);
  const [start, setStart] = useState(false);
  const [openMap, setOpenMap] = useState(false);
  const [bmk, setBmk] = useState(false);
  const [course, setCourse] = useState({});
  const [starView, setStartView] = useState(0.0);
  const [loading, setLoading] = useState(true);
  const [roomInfo, setRoomInfo] = useState();
  let getParameter = (key) => {
    return new URLSearchParams(window.location.search).get(key);
  };

  const courseId = getParameter("courseId");
  const rideRoomId = getParameter("rideRoomId");
  const nickname = getParameter("nickname");
  useEffect(() => {
    console.log(courseId, rideRoomId);
    if (loading) {
      getCourseDetail(
        courseId,
        (response) => {
          console.log("courseData : ", response);
          setCourse(response.data);
          setBmk(response.data.bookmarkId !== null ? true : false);
          setStartView(parseFloat(response.data.starAvg) * 20);
          setLoading(false);
        },
        (fail) => {
          console.log(fail);
          setLoading(false);
        }
      );
      getGroupRoomInfo(
        rideRoomId,
        (response) => {
          console.log("group:", response);
          setRoomInfo(response.data);
        },
        (fail) => {
          console.log(fail);
          setLoading(false);
        }
      );
    }
    return () => {
      setLoading(false);
    };
  }, []);

  const refreshCourseData = () => {
    getCourseDetail(
      courseId,
      (response) => {
        console.log("courseData : ", response);
        setCourse(response.data);
        setBmk(response.data.bookmarkId !== null ? true : false);
        setStartView(parseFloat(response.data.starAvg) * 22.8);
      },
      (fail) => {
        console.log(fail);
        setLoading(false);
      }
    );
  };

  const handleBookmark = () => {
    if (bmk) {
      deleteBookmark(
        course.bookmarkId,
        (response) => {
          console.log(response);
          refreshCourseData();
        },
        (fail) => {
          console.log(fail);
        }
      );
    } else {
      makeBookmark(
        course.courseId,
        (response) => {
          console.log(response);
          refreshCourseData();
        },
        (fail) => {
          console.log(fail);
        }
      );
    }
  };
  if (loading) return <Spinner />;
  else
    return (
      <Box align="center" width="100%" height="86%" alignSelf="center">
        <Box
          direction="row"
          justify="between"
          width="90%"
          margin={{ bottom: "10px" }}
        >
          <BackButton
            onClick={() => {
              navigate("/");
            }}
          >
            <img src={BackBtn} alt="" />
          </BackButton>
          <StyledText text={course.courseName} size="24px" weight="bold" />
          <Blank />
        </Box>
        <Box
          width="90%"
          height="35vh"
          onClick={() => {
            setOpenMap(true);
          }}
        >
          {course && (
            <Map
              center={
                course.coordinates
                  ? course.coordinates[0]
                  : { lng: 127.002158, lat: 37.512847 }
              }
              isPanto={true}
              style={{ borderRadius: "25px", width: "100%", height: "100%" }}
            >
              <Polyline
                path={[course.coordinates ? course.coordinates : []]}
                strokeWeight={5} // 선의 두께 입니다
                strokeColor={"#030ff1"} // 선의 색깔입니다
                strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                strokeStyle={"solid"} // 선의 스타일입니다
              />

              <MapMarker
                position={
                  course.coordinates
                    ? course.coordinates[0]
                    : { lng: 127.002158, lat: 37.512847 }
                }
              >
                <div style={{ color: "#000" }}>시작점</div>
              </MapMarker>
              {course.coordinates &&
              course.coordinates[0].lat ===
                course.coordinates[course.coordinates.length - 1].lat &&
              course.coordinates[0].lng ===
                course.coordinates[course.coordinates.length - 1].lng ? (
                <MapMarker position={course.coordinates[0]}>
                  <div style={{ color: "#000" }}>시작, 종점</div>
                </MapMarker>
              ) : (
                <MapMarker
                  position={
                    course.coordinates
                      ? course.coordinates[course.coordinates.length - 1]
                      : []
                  }
                >
                  <div style={{ color: "#000" }}>종점</div>
                </MapMarker>
              )}
            </Map>
          )}
        </Box>
        <Box direction="row" justify="between" width="90%">
          <StarBox score={course.starAvg} starView={starView} />
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
                handleBookmark();
              }}
            />
            <StyledText text={course.likeCount} />
          </Box>
        </Box>

        <Box gap="small" align="end" width="90%">
          <StyledText
            text={"코스 길이 : " + course.distance + "km"}
            style={{
              backgroundColor: "#BDE0EF",
              borderRadius: "10px",
              // margin: "3px",
              fontSize: "12px",
              paddingLeft: "10px",
              paddingRight: "10px",
            }}
            weight="bold"
          />
          <StyledText
            text={"예상 시간 : " + expectTimeHandle(course.expectedTime)}
            style={{
              backgroundColor: "#F8F38F",
              borderRadius: "10px",
              fontSize: "12px",
              paddingLeft: "10px",
              paddingRight: "10px",
            }}
            weight="bold"
          />
          <StyledText
            text={course.start.split(" ")[0] + " " + course.start.split(" ")[1]}
            style={{
              backgroundColor: "#F4D4D4",
              borderRadius: "10px",
              fontSize: "12px",
              paddingLeft: "10px",
              paddingRight: "10px",
            }}
            weight="bold"
          />
        </Box>
        <Box
          width="90%"
          pad="medium"
          margin={{ top: "10px", bottom: "10px" }}
          style={{
            borderRadius: "10px",
            border: "2px solid #64CCBE",
            fontWeight: "bold",
            position: "relative",
          }}
        >
          <StyledText
            text={nickname + "님의 그룹"}
            weight="bold"
            style={{
              background: "white",
              position: "absolute",
              zIndex: 500,
              top: -14,
              padding: "0 5px",
            }}
          />
          {roomInfo
            .map((m) => {
              return m.nickname;
            })
            .join(" ")}
        </Box>
        <BootstrapButton
          onClick={() => {
            startRidding(
              (response2) => {
                console.log(response2);
                navigate("/ride", {
                  state: {
                    courseName: course.courseName,
                    rideType: "group",
                    courseType: "course",
                    recordId: response2.data.recordId,
                    coordinates: course.coordinates,
                    checkPoints: course.checkpoints,
                    courseId: courseId,
                    roomInfo: {
                      courseId: courseId,
                      nickname: nickname,
                      rideRoomId: rideRoomId,
                    },
                  },
                });
              },
              (fail) => {
                console.log(fail);
              }
            );
          }}
        >
          같이 주행하기
        </BootstrapButton>
      </Box>
    );
};
