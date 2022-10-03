import { Box } from "grommet";
import React, { useEffect, useState } from "react";
import { Map, Polyline } from "react-kakao-maps-sdk";
import styled from "styled-components";
import CloseButton from "../assets/images/close.png";
import { StyledText } from "../components/Common";
import Star from "../assets/images/star_review.png";
import StarBlank from "../assets/images/star_review_blank.png";
import { useLocation, useNavigate } from "react-router-dom";
import ImInput from "../assets/icons/imageInput.svg";
import { FlexBox, STextArea } from "../components/UserStyled";
import Button from "../components/Button";
import { expectTimeHandle, tags as initTags } from "../utils/util";
import { TextField } from "@mui/material";
import { BootstrapButton, RegisterButton } from "../components/Buttons";
import { AlertDialog } from "../components/AlertDialog";
import { addCourse } from "../utils/api/courseApi";
const HeaderDiv = styled.div`
  margin: 5px;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid black;
  padding: 5px;
  width: 100%;
`;

const BackButton = styled.button`
  background: none;
  font-size: 12px;
  font-family: Noto Sans KR, sans-serif;
  border: 0px;
  width: 10vw;
`;

export const ImageBtn = styled.img`
  background: #fff;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 10px;
  &:hover {
    background: rgb(77, 77, 77);
    color: #fff;
  }
`;

const HeaderBox = ({ goBack }) => {
  return (
    <HeaderDiv>
      <div style={{ width: "10vw" }}></div>
      <StyledText size="20px" weight="bold" text="코스 등록" />
      <BackButton onClick={goBack}>
        <img src={CloseButton} />
      </BackButton>
    </HeaderDiv>
  );
};

export const CourseRegister = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [courseTitle, setCourseTitle] = useState("");
  const [tags, setTags] = useState([]);
  const [select, setSelect] = useState([]);
  const [open, setOpen] = useState(false);
  const [notValid, setNotValid] = useState(false);
  const [image, setImage] = useState();
  const { courseData } = location.state;
  const [exit, setExit] = useState(false);
  const isValied = () => {
    if (courseTitle === "" || image === undefined) return false;
    else return true;
  };

  const handleText = (text) => {
    //  console.log(text);
    const MAX_LENGTH = 20;
    if (text.length <= MAX_LENGTH) setCourseTitle(text);
  };
  const handleImageUpload = (e) => {
    const fileArr = e.target.files;
    // console.log(fileArr[0]);
    setImage(fileArr[0]);
  };
  const handleRegister = () => {
    // let score = clicked.filter(Boolean).length;
    const request = {
      recordId: courseData.recordId,
      courseName: courseTitle,
    };
    const formData = new FormData();
    formData.append("image", image);
    const blob = new Blob([JSON.stringify(request)], {
      type: "application/json",
    });
    formData.append("inputMap", blob);
    addCourse(
      formData,
      (response) => {
        console.log(response);
        navigate(`/mypage`);
      },
      (fail) => {
        console.log(fail);
      }
    );
  };

  useEffect(() => {
    setTags(initTags);
  }, []);
  return (
    <Box width="100%" align="center">
      {/* 헤더 박스 */}
      <HeaderBox
        goBack={() => {
          setExit(true);
        }}
      />
      {/* 바디 */}
      <Box width="90%" align="center" margin={{ top: "20px" }}>
        {/* 지도, 코스 이름, 데이터 */}
        <Box width="80%" direction="row" gap="small">
          <Box width="100%" justify="around">
            {/* 기록 날짜 시작*/}
            <Box align="center" margin={{ bottom: "20px" }}>
              <StyledText text="나만의 코스" size="18px" />
            </Box>
            {/* 기록 날짜 끝*/}

            {/* 카카오맵 */}
            <Map
              center={courseData.latlng[parseInt(courseData.latlng.length / 2)]}
              isPanto={true}
              level={9}
              style={{ borderRadius: "10px", width: "100%", height: "380px" }}
            >
              <Polyline
                path={[courseData.latlng]}
                strokeWeight={3} // 선의 두께 입니다
                strokeColor={"#030ff1"} // 선의 색깔입니다
                strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                strokeStyle={"solid"} // 선의 스타일입니다
              />
            </Map>

            <Box margin={{ top: "20px", bottom: "20px" }}>
              <StyledText text={`주행 거리: ${courseData.totalDistance}km`} />
              <StyledText
                text={`주행 시간: ${expectTimeHandle(
                  courseData.totalDistance
                )}`}
              />
              <StyledText text={`평균 속도: ${courseData.avgSpeed}km/h`} />
            </Box>
          </Box>
        </Box>
        {/* 지도, 코스 이름, 데이터 끝 */}
        {/* 사진 첨부 버튼 시작 */}
        <Box width="80%" align="end">
          <label
            htmlFor="image"
            style={{
              display: "flex",
              alignContent: "end",
            }}
          >
            <ImageBtn src={ImInput} />
            <StyledText
              size="10px"
              color={image ? "black" : "lightgray"}
              text={image ? "첨부 완료" : "최대 1장"}
              alignSelf="end"
            />
          </label>
          <input
            id="image"
            type="file"
            accept="image/jpg,image/png,image/jpeg,image/gif"
            style={{
              display: "none",
            }}
            onChange={handleImageUpload}
          />
        </Box>
        {/* 사진 첨부 버튼 끝 */}
        {/* 텍스트아리아 시작 */}
        <Box margin="small" width="80%">
          <TextField
            placeholder="코스 제목을 입력해주세요."
            label="코스 제목"
            size="small"
            onChange={(e) => handleText(e.target.value)}
            value={courseTitle}
          />
          <Box justify="end" direction="row">
            <div>{courseTitle.length} / 20</div>
          </Box>
        </Box>
        {/* 텍스트 아리아 끝 */}
        {/* 등록 버튼 시작 */}
        <RegisterButton
          onClick={() => {
            if (isValied()) setOpen(true);
            else setNotValid(true);
          }}
        >
          <StyledText text="등록" size="18px" color="white" weight="bold" />
        </RegisterButton>
        <AlertDialog
          open={open}
          handleClose={() => {
            setOpen(false);
          }}
          handleAction={handleRegister}
          title="코스 등록"
          desc="코스를 등록하시겠습니까?"
          cancel="취소"
          accept="등록"
          register
        />

        <AlertDialog
          open={notValid}
          handleClose={() => {
            setNotValid(false);
          }}
          title="코스 등록"
          desc="모든 정보를 입력하세요!"
          cancel="닫기"
        />
        <AlertDialog
          open={exit}
          handleClose={() => {
            setExit(false);
          }}
          handleAction={() => {
            navigate("/");
          }}
          title="등록 취소"
          desc="코스 등록을 취소하시겠습니까?"
          accept="나가기"
          cancel="닫기"
        />
      </Box>
    </Box>
  );
};
