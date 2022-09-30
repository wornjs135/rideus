import React, { useState } from "react";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Button from "./Button";
import { Button as GBtn } from "grommet";
import { Button as MBtn, ThemeProvider } from "@mui/material";
import { Box } from "grommet";
import styled from "styled-components";
import { CourseMap, StarBox, StyledText } from "./Common";
import WeatherBtn from "../assets/images/weather.png";
import SoloBtn from "../assets/images/solo.png";
import GroupBtn from "../assets/images/group.png";
import { StyledHorizonTable } from "./HorizontalScrollBox";
import { useNavigate } from "react-router-dom";
import { ChooseSoloGroupBar, HeaderBox } from "./ChooseRideTypeBar";
import { BootstrapButton, ExitButton, WhiteButton } from "./Buttons";
import { Map, MapMarker, Polyline, useMap } from "react-kakao-maps-sdk";
import { categorys, markerCategorys } from "../utils/util";
import { NearInfoDialog } from "./NearInfoDialog";
import { theme } from "../pages/CourseList";
export const AlertDialog = ({
  open,
  handleClose,
  handleAction,
  title,
  desc,
  cancel,
  accept,
  register,
}) => {
  return (
    <Dialog
      open={open}
      onClose={handleClose}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
    >
      <DialogTitle id="alert-dialog-title">{title}</DialogTitle>
      <DialogContent>
        <DialogContentText id="alert-dialog-description">
          {desc}
        </DialogContentText>
      </DialogContent>

      <Box direction="row" justify="center">
        <Button SmallWhite onClick={handleClose}>
          {cancel}
        </Button>
        {accept && (
          <Button
            SmallPink={register ? false : true}
            SmallGreen={register ? true : false}
            onClick={handleAction}
            autoFocus
          >
            {accept}
          </Button>
        )}
      </Box>
    </Dialog>
  );
};

export const RideDialog = ({ open, handleClose, title }) => {
  const navigate = useNavigate();
  return (
    <Dialog
      open={open}
      onClose={handleClose}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
    >
      <Box direction="row" justify="center" width="100%" pad="15px" gap="small">
        <GBtn
          color="#64CCBE"
          onClick={() => {
            navigate("/ride", {
              state: {
                courseName: title,
                rideType: "solo",
                courseType: "course",
              },
            });
          }}
          children={
            <Box
              width="145px"
              height="140px"
              align="center"
              justify="between"
              background="#64CCBE"
              style={{ borderRadius: "8px" }}
              pad="small"
            >
              <img src={SoloBtn} width="100px" />
              <StyledText
                text="혼자 타기"
                color="white"
                weight="bold"
                size="18px"
              />
            </Box>
          }
        />
        <GBtn
          onClick={() => {
            navigate("/ride", {
              state: {
                courseName: title,
                rideType: "group",
                courseType: "course",
              },
            });
          }}
          children={
            <Box
              width="145px"
              align="center"
              height="140px"
              justify="between"
              background="#64CCBE"
              style={{ borderRadius: "8px" }}
              pad="small"
            >
              <img src={GroupBtn} width="100px" />
              <StyledText
                text="같이 타기"
                color="white"
                weight="bold"
                size="18px"
              />
            </Box>
          }
        />
      </Box>
    </Dialog>
  );
};

const BottomBtn = styled.div`
  position: absolute;
  width: 100%;
  z-index: 5;
  bottom: 0;
  display: flex;
  justify-content: space-around;
`;

const TopBanner = styled.div`
  position: absolute;
  width: 100%;
  z-index: 5;
  top: 0;
  display: flex;
  margin-top: 15px;
`;

export const MapDialog = ({
  type,
  open,
  handleClose,
  handleAction,
  title,
  map,
  cancel,
  accept,
  bottom,
  course,
  nearInfos,
}) => {
  const [op, setOp] = useState(false);
  const [selected, setSelected] = useState(0);
  const EventMarkerContainer = ({ position, content, info }) => {
    const map = useMap();
    const [isVisible, setIsVisible] = useState(false);

    return (
      <>
        <MapMarker
          position={position} // 마커를 표시할 위치
          // @ts-ignore
          image={{
            src: `/icons/marker${markerCategorys.findIndex(
              (i) => i.name === info.nearinfoCategory
            )}.svg`,
            size: {
              width: 29,
              height: 41,
            }, // 마커이미지의 크기입니다
          }}
          onClick={(marker) => {
            map.panTo(marker.getPosition());
            setTimeout(function () {
              setIsVisible(true);
            }, 200);
          }}
        ></MapMarker>
        {isVisible && (
          <NearInfoDialog
            handleClose={() => {
              setIsVisible(false);
            }}
            info={info}
            open={isVisible}
          />
        )}
      </>
    );
  };
  return (
    <Dialog fullScreen open={open} onClose={handleClose}>
      <Box width="100vw" height="100vh" align="center">
        {map ? (
          map
        ) : (
          <Map
            center={course.coordinates[0]}
            isPanto={true}
            style={{ borderRadius: "25px", width: "100%", height: "100%" }}
          >
            {course.coordinates && (
              <Polyline
                path={[course.coordinates ? course.coordinates : []]}
                strokeWeight={5} // 선의 두께 입니다
                strokeColor={"#030ff1"} // 선의 색깔입니다
                strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                strokeStyle={"solid"} // 선의 스타일입니다
              />
            )}
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
            {nearInfos.data
              .filter((near) => {
                if (selected === 0) {
                  return near;
                } else if (near.key.includes(markerCategorys[selected].name)) {
                  return near;
                }
              })
              .map((near, idxCat) => {
                if (near.arr.length > 0)
                  return near.arr.map((info, idx) =>
                    idx % 2 === 0 ? (
                      <EventMarkerContainer
                        position={{
                          lat: info.nearinfoLat,
                          lng: info.nearinfoLng,
                        }}
                        key={idx}
                        info={info}
                      ></EventMarkerContainer>
                    ) : null
                  );
              })}
          </Map>
        )}

        {/* 상단 바 */}
        <Box
          align="center"
          style={{
            position: "absolute",
            width: "100%",
            zIndex: "5",
            top: "0",
            display: "flex",
            marginTop: "15px",
          }}
        >
          <Box width="100%" align="center">
            {/* 코스 이름 */}
            <Box
              round={{ size: "small" }}
              width="90%"
              height="43px"
              background={"#64CCBE"}
              align="center"
              justify="center"
            >
              <StyledText text={title} color="white" size="20px" />
            </Box>
            {type === "detail" && (
              <Box width="100%" align="center">
                <Box
                  direction="row"
                  justify="start"
                  overflow="scroll"
                  margin="medium"
                  height="70px"
                >
                  <ThemeProvider theme={theme}>
                    <StyledHorizonTable>
                      {markerCategorys.map((cat, idx) => {
                        return (
                          <MBtn
                            key={idx}
                            variant="contained"
                            color={selected === idx ? "active" : "deactive"}
                            onClick={() => {
                              setSelected(idx);
                            }}
                            style={{
                              fontWeight: "bold",
                              width: "55px",
                              height: "55px",
                              borderRadius: "10px",
                              marginRight: "10px",
                            }}
                          >
                            <Box align="center">
                              {cat.icon}
                              {cat.name}
                            </Box>
                          </MBtn>
                        );
                      })}
                    </StyledHorizonTable>
                  </ThemeProvider>
                </Box>
                <img width="50px" src={WeatherBtn} onClick={() => {}} />
              </Box>
            )}
          </Box>
        </Box>
        {/* 하단 버튼 */}
        <BottomBtn>
          <WhiteButton onClick={handleClose} children={cancel} />
          {/* {accept && type === "detail" ? (
            <BootstrapButton onClick={handleAction} children={accept} />
          ) : (
            <ExitButton onClick={handleAction} children={accept} />
          )} */}
        </BottomBtn>
        {bottom && (
          <ChooseSoloGroupBar
            open={op}
            onDismiss={() => {
              setOp(false);
            }}
            title={course.courseName}
            coordinates={course.coordinates}
            checkPoints={course.checkpoints}
          />
        )}
      </Box>
    </Dialog>
  );
};

export const ReviewDialog = ({
  open,
  handleClose,
  title,
  desc,
  course,
  cancel,
  score,
  starView,
  tags,
  img,
}) => {
  return (
    <Dialog open={open} onClose={handleClose}>
      <Box width="85vw" align="center" pad="small">
        <HeaderBox goBack={handleClose} title={course.courseName} />
        <Box width="75%" justify="around" align="center">
          {/* 제목 */}
          <StyledText text={title} size="20px" weight="bold" />
          {/* 사진 */}
          {img && <img src={img} width="75%" />}
          {/* 별점 */}
          <StarBox score={score} starView={starView} />
          {/* 내용 */}
          <StyledText text={desc} />
          {/* 태그 */}
          <Box direction="row" overflow="scroll">
            {/* arrays.map */}
            {tags.map((t, idx) => {
              return <StyledText text={"#" + t.tagName} key={idx} />;
            })}
          </Box>
          <CourseMap course={course} width="100%" height="40vh" />
          {/* 하단 버튼 */}
          {/* <ExitButton onClick={handleClose}>{cancel}</ExitButton> */}
        </Box>
      </Box>
    </Dialog>
  );
};

export const WeatherDialog = ({
  open,
  handleClose,
  title,
  desc,
  course,
  cancel,
  score,
  starView,
  tags,
}) => {
  return (
    <Dialog open={open} onClose={handleClose}>
      <Box width="85vw" align="center" pad="small">
        <Box width="75%" justify="around" align="center">
          {/* 제목 */}
          <StyledText text={title} size="20px" weight="bold" />
          {/* 별점 */}
          <StarBox score={score} starView={starView} />
          {/* 내용 */}
          <StyledText text={desc} />
          {/* 태그 */}
          <Box direction="row">
            {/* arrays.map */}
            {tags.map((t, idx) => {
              return <StyledText text={"#" + t.searchTagName} key={idx} />;
            })}
          </Box>
          <CourseMap course={course} width="100%" height="40vh" />
          {/* 하단 버튼 */}
          <ExitButton onClick={handleClose}>{cancel}</ExitButton>
        </Box>
      </Box>
    </Dialog>
  );
};
