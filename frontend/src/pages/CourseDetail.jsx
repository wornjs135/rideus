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

export const BackButton = styled.button`
  background: none;
  font-size: 12px;
  font-family: Noto Sans KR, sans-serif;
  border: 0px;
  width: 10vw;
  margin-top: 10px;
`;

export const Blank = styled.div`
  background: none;
  font-size: 12px;
  font-family: Noto Sans KR, sans-serif;
  border: 0px;
  width: 10vw;
  margin-top: 10px;
`;
//코스 구조
// bookmarkId: null
// category: "음식점"
// checkpoints: (9) [{…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}]
// coordinates: (797) [{…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, …]
// courseId: "632fb9d60cb15c6cbefa488a"
// courseName: "반포GS-남북 코스"
// distance: 30.7
// expectedTime: 74
// finish: "서울특별시 서초구 잠원동"
// imageUrl: null
// likeCount: 0
// starAvg: 0
// start: "서울특별시 서초구 잠원동"
// tags: null

// 랭크 구조
// [
//   {
//     "memberId": 0,
//     "nickname": "string",
//     "profileImageUrl": "string",
//     "ranking": 0,
//     "timeMinute": 0
//   }
// ]

export const CourseDetail = () => {
  const { courseId } = useParams();

  const [open, setOpen] = useState(true);
  const [start, setStart] = useState(false);
  const [openMap, setOpenMap] = useState(false);
  const [bmk, setBmk] = useState(false);
  const [score, setScore] = useState("5");
  const [course, setCourse] = useState({});
  const [mapData, setMapData] = useState({
    latlng: [],
    center: { lng: 127.002158, lat: 37.512847 },
  });
  const [clicked, setClicked] = useState([false, false, false, false, false]);
  const [starView, setStartView] = useState(0.0);
  const [reviews, setReviews] = useState([]);
  const [ranks, setRanks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [nearInfos, setNearInfos] = useState({
    data: [
      { key: "관광명소", arr: [] },
      { key: "음식점", arr: [] },
      { key: "카페", arr: [] },
      { key: "편의점", arr: [] },
      { key: "자전거정비", arr: [] },
      { key: "문화시설", arr: [] },
      { key: "공중화장실", arr: [] },
    ],
  });
  const navigate = useNavigate();
  useEffect(() => {
    if (loading) {
      nearInfos.data.map((n, idx) =>
        getCourseNearInfo(
          { category: n.key, courseId: courseId },
          (response) => {
            console.log(n.key + "nearInfo : ", response);
            nearInfos.data[idx].arr = response.data;
            setNearInfos({ ...nearInfos });
          },
          (fail) => {
            console.log(fail);
            // setLoading(false);
          }
        )
      );

      getCourseDetail(
        courseId,
        (response) => {
          console.log("courseData : ", response);
          setCourse(response.data);
          setBmk(response.data.bookmarkId !== null ? true : false);
          setStartView(parseFloat(response.data.starAvg) * 22.8);

          getCourseAllReview(
            courseId,
            (response) => {
              console.log("reviews : ", response);
              setReviews(response.data);
              setLoading(false);
            },
            (fail) => {
              console.log(fail);
            }
          );
          getCourseRankTime(
            courseId,
            (response) => {
              console.log("ranks : ", response);
              setRanks(response.data);
              setLoading(false);
            },
            (fail) => {
              console.log(fail);
            }
          );
          setLoading(false);
        },
        (fail) => {
          console.log(fail);
          setLoading(false);
        }
      );

      // getCourseNearInfo(
      //   { category: "편의점", courseId: courseId },
      //   (response) => {
      //     console.log(response);
      //   },
      //   (fail) => {
      //     console.log(fail);
      //   }
      // );
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
      <Box align="center" width="100%" height="86%">
        <Box
          direction="row"
          justify="between"
          width="100%"
          margin={{ bottom: "10px" }}
        >
          <BackButton
            onClick={() => {
              navigate("/courseList");
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

        <Box>
          <StyledText text={course.start + " - " + course.finish} />
          <Box direction="row" align="end" gap="small">
            <StyledText text="거리 : " weight="bold" size="16px" />
            <StyledText text={`${course.distance} km`} />
          </Box>
          <Box direction="row" align="end" gap="small">
            <StyledText text="예상 시간 : " weight="bold" size="16px" />
            <StyledText text={expectTimeHandle(course.expectedTime)} />
          </Box>
        </Box>
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
          ranks={ranks}
          reviews={reviews}
        />
        <ChooseSoloGroupBar
          open={start}
          onDismiss={() => {
            setStart(false);
          }}
          title={course.courseName}
          coordinates={course.coordinates}
          checkPoints={course.checkpoints}
          courseId={course.courseId}
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
          bottom={true}
          course={course}
          cancel="뒤로가기"
          accept="주행시작"
          title={course.courseName}
          nearInfos={nearInfos}
        />
      </Box>
    );
};
