import React, { useEffect, useState } from "react";
import { Avatar, Box, Spinner } from "grommet";
import bike1 from "../assets/images/bicycle.png";
import { StyledText } from "../components/Common";
import { StyledHorizonTable } from "../components/HorizontalScrollBox";
import { MyPageCourse } from "../components/MyPageCourseComponent";
import { UserSettings } from "grommet-icons";
import {
  getMyTag,
  getTotalRecord,
  myRides,
  recentRide,
} from "../utils/api/userApi";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { getBookmarkedCourses } from "../utils/api/bookmarkApi";
import {
  convertDistanceToImg,
  convertDistanceToText,
  distanceHandleM,
  expectTimeHandle,
  expectTimeHandle2,
  renameObjectKey,
} from "../utils/util";
import { motion } from "framer-motion";
import { container } from "./Main";
import RecentRide from "../assets/images/recentRide.png";
import MyRide from "../assets/images/myRide2.png";
import BmkCourse from "../assets/images/bmkCourse.png";
import MyTag from "../assets/images/myTag.png";

export const MyPage = () => {
  const navigate = useNavigate();
  const [recentRides, setRecentRides] = useState([]);
  const [myRideCourses, setMyRideCourses] = useState([]);
  const [bookmarkCourses, setbookmarkCourses] = useState([]);
  const [myTag, setMyTag] = useState([]);
  const [totalRecord, setTotalRecord] = useState([]);
  const [loading, setLoading] = useState(true);
  const user = useSelector((state) => state.user.user.user);
  useEffect(() => {
    setLoading(true);
    recentRide((data) => {
      console.log(data);
      const { data: recent } = data;
      console.log("recent : ", recent);
      setRecentRides(recent);
    });

    myRides((data) => {
      console.log(data);
      const { data: rides } = data;
      console.log("my : ", rides);
      setMyRideCourses(rides);
    });
    getBookmarkedCourses(
      (data) => {
        console.log(data);
        const { data: rides } = data;
        console.log("bmk : ", rides);
        setbookmarkCourses(rides);
      },
      () => {}
    );
    getMyTag((data) => {
      console.log(data);
      const { data: tags } = data;
      setMyTag(tags);
    });
    getTotalRecord((data) => {
      console.log("totalRecord : ", data);
      const { data: record } = data;
      setTotalRecord(record);
    });

    console.log(user);
    setLoading(false);
  }, []);

  if (loading) return <Spinner />;
  else
    return (
      <motion.div
        initial="hidden"
        animate="visible"
        variants={container}
        style={{
          width: "100vw",
          justify: "center",
          margin: "0 auto",
        }}
      >
        {/* 자전거사진, 멘트, 버튼 */}

        <Box align="center" justify="between" width="100%">
          <Box
            width="100%"
            direction="row"
            align="center"
            justify="around"
            style={{ marginTop: "5vw", marginBottom: "5vw" }}
          >
            <Box direction={"column"} align={"left"} justify={"around"}>
              <UserSettings
                style={{ marginBottom: "8vw" }}
                onClick={() => navigate("/profile")}
              />
              <Avatar src={user.profileImageUrl} />
            </Box>
            <Box>
              <Box
                direction="row"
                style={{ marginBottom: "5vw", marginLeft: "auto" }}
              >
                <Box style={{ marginRight: "5vw" }} justify="end">
                  <StyledText
                    text={expectTimeHandle2(totalRecord?.total_time)}
                    weight="bold"
                    size="15px"
                    style={{ textAlign: "right", display: "inline" }}
                  />
                  <StyledText
                    color="#4B4B4B"
                    text={`${parseFloat(totalRecord?.total_distance).toFixed(
                      2
                    )}km`}
                    weight="bold"
                    size="15px"
                    style={{ textAlign: "right", display: "inline" }}
                  />
                </Box>
                <img
                  src={convertDistanceToImg(totalRecord?.total_distance)}
                  width={50}
                />
              </Box>
              <Box justify="end">
                <StyledText
                  text={`${user?.nickname}님은 현재`}
                  weight="bold"
                  size="18px"
                  style={{ textAlign: "right", display: "inline" }}
                ></StyledText>
                <StyledText
                  text={convertDistanceToText(totalRecord.total_distance)}
                  weight="bold"
                  size="18px"
                  style={{ textAlign: "right", display: "inline" }}
                ></StyledText>
                <StyledText
                  text="왕복했어요!"
                  weight="bold"
                  size="18px"
                  style={{ textAlign: "right", display: "inline" }}
                ></StyledText>
              </Box>
            </Box>
          </Box>
        </Box>
        {/* 최근 주행, 나만의 코스, 코스 북마크 */}
        <Box
          align="center"
          justify="between"
          round
          background="#e3f6f4"
          border={{ color: "#F3F3F3", size: "small", side: "all" }}
        >
          {/* 인기코스 */}
          <Box align="start" width="100%" margin={{ top: "large" }}>
            <Box
              width="100%"
              pad={{ left: "30px", right: "20px" }}
              direction="row"
              align="end"
              gap="small"
            >
              <img
                src={RecentRide}
                height="24px"
                style={{
                  objectFit: "cover",
                }}
              />
              <StyledText
                text="최근 주행"
                size="18px"
                style={{
                  fontFamily: "gwtt",
                }}
              />
            </Box>
            {/* 인기코스 리스트 */}
            <Box direction="row" overflow="scroll">
              <StyledHorizonTable>
                {recentRides &&
                  recentRides.map((course, index, array) => (
                    <div key={index} className="card">
                      <MyPageCourse
                        course={course}
                        nav={
                          course.shared
                            ? () => {
                                course.single ?
                                    navigate(`/recent-single/${course.recordId}`) : navigate(`/recent-group/${course.roomId}`);
                              }
                            : () => {
                                navigate("/registerCourse");
                              }
                        }
                      />
                    </div>
                  ))}
              </StyledHorizonTable>
            </Box>
          </Box>
          {/* 나만의 코스 */}
          <Box align="start" width="100%" margin={{ top: "large" }}>
            <Box
              width="100%"
              pad={{ left: "30px", right: "20px" }}
              direction="row"
              align="end"
              gap="small"
            >
              <img
                src={MyRide}
                height="24px"
                style={{
                  objectFit: "cover",
                }}
              />
              <StyledText
                text="나만의 주행"
                size="18px"
                style={{
                  fontFamily: "gwtt",
                }}
              />
            </Box>
            {/* 나만의 코스 리스트 */}
            <Box direction="row" overflow="scroll">
              <StyledHorizonTable>
                {myRideCourses &&
                  myRideCourses.map((course, index, array) => (
                    <div key={index} className="card">
                      <MyPageCourse
                        course={course}
                        nav={
                          course.shared
                            ? () => {
                                navigate(`/courseDetail/${course.roomId}`);
                              }
                            : () => {
                                renameObjectKey(
                                  course,
                                  "distance",
                                  "totalDistance"
                                );
                                navigate("/registerCourse", {
                                  state: { courseData: course },
                                });
                              }
                        }
                      />
                    </div>
                  ))}
              </StyledHorizonTable>
            </Box>
          </Box>
          {/* 인기태그 */}
          <Box align="start" width="100%" margin={{ top: "large" }}>
            <Box
              width="100%"
              pad={{ left: "30px", right: "20px" }}
              direction="row"
              align="end"
              gap="small"
            >
              <img
                src={BmkCourse}
                height="24px"
                style={{
                  objectFit: "cover",
                }}
              />
              <StyledText
                text="북마크 코스"
                size="18px"
                style={{
                  fontFamily: "gwtt",
                }}
              />
            </Box>
            {/* 인기태그 리스트 */}
            {/* 코스 북마크 리스트 */}
            <Box direction="row" overflow="scroll">
              <StyledHorizonTable>
                {bookmarkCourses &&
                  bookmarkCourses.map((value, index, array) => (
                    <div className="card" key={index}>
                      <MyPageCourse type={"bookmark"}
                        course={value}
                        nav={() => {
                          navigate(`/courseDetail/${value.courseId}`);
                        }}
                      />
                    </div>
                  ))}
              </StyledHorizonTable>
            </Box>
          </Box>
          {/* 인기태그 */}
          <Box align="start" width="100%" margin={{ top: "large" }}>
            <Box
              width="100%"
              pad={{ left: "30px", right: "20px" }}
              direction="row"
              align="end"
              gap="small"
            >
              <img
                src={MyTag}
                height="24px"
                style={{
                  objectFit: "cover",
                }}
              />
              <StyledText
                text="마이 태그"
                size="18px"
                style={{
                  fontFamily: "gwtt",
                }}
              />
            </Box>
            {/* 인기태그 리스트 */}
            <Box
              direction="row"
              overflow="scroll"
              margin="medium"
              pad={{ left: "20px", bottom: "70px" }}
            >
              {myTag &&
                myTag.map((tag, idx) => {
                  return (
                    <StyledText
                      text={`#${tag.tagName}`}
                      key={idx}
                      color="#64CCBE"
                      weight="bold"
                      style={{
                        color: "white",
                        backgroundColor: "#64ccbe",
                        padding: "8px",
                        fontSize: "11px",
                        margin: "0px 8px 12px 0px",
                        border: "none",
                        borderRadius: "16px",
                      }}
                    />
                  );
                })}
            </Box>
          </Box>
        </Box>
      </motion.div>
    );
};
