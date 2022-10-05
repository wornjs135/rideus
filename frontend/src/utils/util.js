import DensitySmallIcon from "@mui/icons-material/DensitySmall";
import RecommendIcon from "@mui/icons-material/Recommend";
import RestaurantIcon from "@mui/icons-material/Restaurant";
import LocalCafeIcon from "@mui/icons-material/LocalCafe";
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined";
import MuseumIcon from "@mui/icons-material/Museum";
import TourIcon from "@mui/icons-material/Tour";
import WcIcon from "@mui/icons-material/Wc";
import LocalConvenienceStoreIcon from "@mui/icons-material/LocalConvenienceStore";
import plant1 from "../assets/images/plant1.png";
import plant2 from "../assets/images/plant2.png";
import plant3 from "../assets/images/plant3.png";
import plant4 from "../assets/images/plant4.png";
import plant5 from "../assets/images/plant5.png";
import { StyledText } from "../components/Common";
import { Box } from "grommet";

export const tags = [
  { id: 1, searchTagName: "평지" },
  { id: 2, searchTagName: "업힐" },
  { id: 3, searchTagName: "다운힐" },
  { id: 4, searchTagName: "MTB 코스" },
  { id: 5, searchTagName: "한적함" },
  { id: 6, searchTagName: "붐빔" },
  { id: 7, searchTagName: "쾌적함" },
  { id: 8, searchTagName: "라이더 많음" },
  { id: 9, searchTagName: "신호없음" },
  { id: 10, searchTagName: "자전거 도로" },
  { id: 11, searchTagName: "도로 좋음" },
  { id: 12, searchTagName: "야간 주행" },
  { id: 13, searchTagName: "경치 좋음" },
  { id: 14, searchTagName: "강 주변" },
  { id: 15, searchTagName: "공원 근처" },
  { id: 16, searchTagName: "쉴 곳 많음" },
];

export const categorys = [
  { name: "전체", icon: <DensitySmallIcon /> },
  { name: "추천", icon: <RecommendIcon /> },
  { name: "관광명소", icon: <TourIcon /> },
  { name: "음식점", icon: <RestaurantIcon /> },
  { name: "카페", icon: <LocalCafeIcon /> },
  { name: "편의점", icon: <LocalConvenienceStoreIcon /> },
  { name: "자전거정비", icon: <SettingsOutlinedIcon /> },
  { name: "문화시설", icon: <MuseumIcon /> },
  { name: "공중화장실", icon: <WcIcon /> },
];

export const markerCategorys = [
  { name: "전체", icon: <DensitySmallIcon />, main: "#D6FFAD" },
  { name: "관광명소", icon: <TourIcon />, main: "#D6FFAD" },
  { name: "음식점", icon: <RestaurantIcon />, main: "#C0E7EC" },
  { name: "카페", icon: <LocalCafeIcon />, main: "#FFC95F" },
  { name: "편의점", icon: <LocalConvenienceStoreIcon />, main: "#FFFA85" },
  { name: "자전거정비", icon: <SettingsOutlinedIcon />, main: "#FF9999" },
  { name: "문화시설", icon: <MuseumIcon />, main: "#6499FF" },
  { name: "공중화장실", icon: <WcIcon />, main: "#B7B7B7" },
];

export const weathers = ["맑음", "강수", "구름 많음", "흐림"];

export const timeHandle = (time) => {
  if (time < 60) return `${time}s`;
  else if (time < 3600) return `${parseInt(time / 60)}m ${time % 60}s`;
  else return `${parseInt(time / 3600)}h ${parseInt((time % 3600) / 60)}m`;
};

export const timeHandle2 = (time) => {
  if (time < 60) return `${time}M`;
  else if (time < 3600) return `${parseInt(time / 60)}H ${time % 60}M`;
};

export const TimeBox = ({ time }) => {
  if (time < 60)
    return (
      <Box direction="row" align="end">
        <StyledText size="17px" text={time} weight="bold" />
        <StyledText size="13px" text="M" weight="bold" />
      </Box>
    );
  else
    return (
      <Box direction="row" align="end">
        <StyledText size="17px" text={parseInt(time / 60)} weight="bold" />
        <StyledText size="13px" text="H " weight="bold" />
        <Box width="6px" />
        <StyledText size="17px" text={time % 60} weight="bold" />
        <StyledText size="13px" text="M" weight="bold" />
      </Box>
    );
};

export const expectTimeHandle = (time) => {
  if (time < 60) return `${parseInt(time)}분`;
  else return `${parseInt(time / 60)}시간 ${parseInt(time % 60)}분`;
};

export const expectTimeHandle2 = (time) => {
  if (time < 60) return `${parseInt(time)}초`;
  else if (time < 3600)
    return `${parseInt(time / 60)}분 ${parseInt(time % 60)}초`;
  else
    return `${parseInt(time / 3600)}시간 ${parseInt(
      (time - parseInt(time / 3600) * 3600) / 60
    )}분`;
};

export const distanceHandle = (dis) => {
  if (dis < 1000) return dis;
  else return parseFloat(dis / 1000.0).toFixed(2);
};

export const speedHandle = (dis, idle) => {
  return parseFloat(((dis * 3.6) / idle).toFixed(1));
};

export const renameObjectKey = (objs, from, to) => {
  objs[to] = objs[from];
  delete objs[from];
  return objs;
};

export const convertDistanceToImg = (dis) => {
  if (dis >= 11059) return plant5;
  if (dis >= 8852) return plant4;
  if (dis >= 952) return plant3;
  if (dis >= 237) return plant2;
  return plant1;
};

export const convertDistanceToText = (dis) => {
  if (dis >= 11059) return "서울부터 뉴욕까지의 거리를";
  if (dis >= 9580) return "서울부터 LA까지의 거리를";
  if (dis >= 8852) return "서울부터 런던까지의 거리를";
  if (dis >= 1156) return "서울부터 도쿄까지의 거리를";
  if (dis >= 952) return "서울부터 베이징까지의 거리를";
  if (dis >= 455) return "서울부터 제주까지의 거리를";
  if (dis >= 325) return "서울부터 부산까지의 거리를";
  if (dis >= 237) return "서울부터 대구까지의 거리를";
  if (dis >= 140) return "서울부터 대전까지의 거리를";
  if (dis >= 52.4) return "서울부터 인천까지의 거리를";
  return "집 근처 거리를";
};
