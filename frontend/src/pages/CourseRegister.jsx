import { Box } from "grommet";
import React, { useEffect, useState } from "react";
import { Map } from "react-kakao-maps-sdk";
import styled from "styled-components";
import CloseButton from "../assets/images/close.png";
import { StyledText } from "../components/Common";
import Star from "../assets/images/star_review.png";
import StarBlank from "../assets/images/star_review_blank.png";
import { useNavigate } from "react-router-dom";
import ImInput from "../assets/icons/imageInput.svg";
import { FlexBox, STextArea } from "../components/UserStyled";
import Button from "../components/Button";
import { tags as initTags } from "../utils/util";
import { TextField } from "@mui/material";
const HeaderDiv = styled.div`
  margin: 5px;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid black;
  padding: 5px;
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
  const [productDesc, setProductDesc] = useState("");
  const [tags, setTags] = useState([]);
  const [select, setSelect] = useState([]);

  useEffect(() => {
    setTags(initTags);
  }, []);
  return (
    <Box>
      {/* 헤더 박스 */}
      <HeaderBox
        goBack={() => {
          navigate("/");
        }}
      />
      {/* 바디 */}
      <Box align="center" margin={{ top: "20px" }}>
        {/* 지도, 코스 이름, 데이터 */}
        <Box direction="row" gap="small">
          <Box justify="around">
            {/* 기록 날짜 시작*/}
            <Box align="center" margin={{ bottom: "20px" }}>
              <StyledText text="2022.09.28 18:00" size="18px" />
            </Box>
            {/* 기록 날짜 끝*/}

            {/* 카카오맵 */}
            <Map
              center={{ lng: 127.002158, lat: 37.512847 }}
              isPanto={true}
              level={9}
              style={{ borderRadius: "10px", width: "240px", height: "380px" }}
            />
            <Box margin={{ top: "20px", bottom: "20px" }}>
              <StyledText text="주행 거리: 75.4km" />
              <StyledText text="주행 시간:2시간 15분" />
              <StyledText text="평균 속도: 25km/h" />
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
              color="lightgray"
              text="최대 1장"
              alignSelf="end"
            />
          </label>
          <input
            id="image"
            type="file"
            accept="image/jpg,image/png,image/jpeg,image/gif"
            onChange={() => {}}
            style={{
              display: "none",
            }}
          />
        </Box>
        {/* 사진 첨부 버튼 끝 */}
        {/* 텍스트아리아 시작 */}
        <Box margin="small">
          <TextField
            placeholder="코스 제목을 입력해주세요."
            label="코스 제목"
            size="small"
            onChange={(e) => setProductDesc(e.target.value)}
            value={productDesc}
          />
          <Box justify="end" direction="row">
            <div>{productDesc.length} / 300</div>
          </Box>
        </Box>
        {/* 텍스트 아리아 끝 */}
        {/* 등록 버튼 시작 */}
        <Box
          width="100%"
          background="#439652"
          style={{ position: "fixed", bottom: 0 }}
          align="center"
          height="32px"
        >
          <StyledText text="등록" color="white" size="20px" />
        </Box>
      </Box>
    </Box>
  );
};
