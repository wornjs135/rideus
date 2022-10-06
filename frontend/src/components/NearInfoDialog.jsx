import { Dialog } from "@mui/material";
import { Box } from "grommet";
import React from "react";
import { useState } from "react";
import { Roadview } from "react-kakao-maps-sdk";
import { HeaderBox } from "./ChooseRideTypeBar";
import { StyledText } from "./Common";
import KakaoMap from "../assets/images/kakaomap.png";
export const NearInfoDialog = ({ open, info, handleClose }) => {
  const [show, setShow] = useState(true);
  return (
    <Dialog open={open} onClose={handleClose}>
      <Box align="center" pad="small">
        <HeaderBox goBack={handleClose} title={info.nearinfoCategory} />
        <Box justify="around" align="center" gap="medium">
          {/* 제목 */}
          {/* <StyledText text={title} size="20px" weight="bold" /> */}
          {/* 사진 */}
          {/* {img && <img src={img} width="75%" />} */}
          {/* 별점 */}
          {/* <StarBox score={score} starView={starView} /> */}
          {/* 내용 */}
          <Roadview // 로드뷰를 표시할 Container
            position={{
              // 지도의 중심좌표
              lat: info.nearinfoLat,
              lng: info.nearinfoLng,
              radius: 50,
            }}
            style={{
              // 지도의 크기
              width: "300px",
              height: "300px",
              display: show ? "block" : "none",
            }}
            onErrorGetNearestPanoId={() => setShow(false)}
          />

          <StyledText text={info.nearinfoName} />

          {/* <StyledText text={info.nearinfoCategory} /> */}
          {info.nearinfoTel ? (
            <StyledText text={info.nearinfoTel} />
          ) : (
            <StyledText text="전화번호가 없습니다." />
          )}

          <a href={info.nearinfoURL} target="_blank">
            <img src={KakaoMap} width="100px" />
          </a>
          {/* 태그 */}
          {/* 하단 버튼 */}
        </Box>
      </Box>
    </Dialog>
  );
};
