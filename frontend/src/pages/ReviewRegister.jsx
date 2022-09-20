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
      <StyledText size="20px" weight="bold" text="리뷰 쓰기" />
      <BackButton onClick={goBack}>
        <img src={CloseButton} />
      </BackButton>
    </HeaderDiv>
  );
};

export const ReviewRegister = () => {
  const navigate = useNavigate();
  const [productDesc, setProductDesc] = useState("");
  const [tags, setTags] = useState([]);
  const [select, setSelect] = useState([]);

  useEffect(() => {
    setTags([
      { id: 1, searchTagName: "완만" },
      { id: 2, searchTagName: "가파름" },
      { id: 3, searchTagName: "한적함" },
      { id: 4, searchTagName: "붐빔" },
      { id: 5, searchTagName: "쾌적함" },
      { id: 6, searchTagName: "벌레많음" },
      { id: 7, searchTagName: "사람많음" },
      { id: 8, searchTagName: "도로상태좋음" },
      { id: 9, searchTagName: "최악" },
      { id: 10, searchTagName: "실망함" },
    ]);
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
          {/* 카카오맵 */}
          <Map
            center={{ lng: 127.002158, lat: 37.512847 }}
            isPanto={true}
            level={9}
            style={{ borderRadius: "10px", width: "120px", height: "120px" }}
          />
          <Box justify="around">
            <StyledText text="한강 종주 자전거 길" size="18px" />
            <Box direction="row">
              <StyledText text="거리: 75.4km" />
              <StyledText text="시간:2시간 15분" />
            </Box>
            <StyledText text="마포구, 서울" />
          </Box>
        </Box>
        {/* 지도, 코스 이름, 데이터 끝 */}
        {/* 별점 입력 시작 */}
        <Box margin={{ top: "20px" }}>
          <StyledText text="코스 어떠셨나요?" size="20px" />
          <Box direction="row">
            <img src={Star} alt="" />
            <img src={Star} alt="" />
            <img src={Star} alt="" />
            <img src={Star} alt="" />
            <img src={StarBlank} alt="" />
          </Box>
        </Box>
        {/* 별점 입력 끝 */}
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
              text="최대 3장"
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
          <STextArea
            placeholder="코스에 대한 리뷰를 작성해주세요!"
            onChange={(e) => setProductDesc(e.target.value)}
            value={productDesc}
          />
          <Box justify="end" direction="row">
            <div>{productDesc.length} / 300</div>
          </Box>
        </Box>
        {/* 텍스트 아리아 끝 */}
        {/* 태그 시작 */}
        <Box margin={{ top: "20px", left: "15px" }}>
          <StyledText text="Tag" size="14px" weight="bold" />
          <FlexBox Column_C>
            <FlexBox
              Row_S
              style={{
                flexWrap: "wrap",
                padding: "6px 10px",
                width: "88vw",
              }}
            >
              {tags.map((tag, idx) => (
                <Button
                  key={idx}
                  onClick={() => {
                    !select.some((v) => v.id === tag.id)
                      ? setSelect((select) => [...select, tag])
                      : setSelect(
                          select.filter((Button) => Button.id !== tag.id)
                        );
                  }}
                  TagGray={!select.some((v) => v.id === tag.id) ? true : false}
                  TagGreen={select.some((v) => v.id === tag.id) ? true : false}
                  style={{
                    margin: "6px 3px ",
                    wordWrap: "break-word",
                    minWidth: "22%",
                    padding: "5px 3px",
                  }}
                >
                  #{tag.searchTagName}
                </Button>
              ))}
            </FlexBox>
          </FlexBox>
        </Box>
        {/* 태그 끝 */}
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
