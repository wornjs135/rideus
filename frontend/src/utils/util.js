import DensitySmallIcon from "@mui/icons-material/DensitySmall";
import RecommendIcon from "@mui/icons-material/Recommend";
import RestaurantIcon from "@mui/icons-material/Restaurant";
import LocalCafeIcon from "@mui/icons-material/LocalCafe";
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined";
import MuseumIcon from "@mui/icons-material/Museum";
import TourIcon from "@mui/icons-material/Tour";
import WcIcon from "@mui/icons-material/Wc";
import LocalConvenienceStoreIcon from "@mui/icons-material/LocalConvenienceStore";
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
  { name: "전체", icon: <DensitySmallIcon /> },
  { name: "관광명소", icon: <TourIcon /> },
  { name: "음식점", icon: <RestaurantIcon /> },
  { name: "카페", icon: <LocalCafeIcon /> },
  { name: "편의점", icon: <LocalConvenienceStoreIcon /> },
  { name: "자전거정비", icon: <SettingsOutlinedIcon /> },
  { name: "문화시설", icon: <MuseumIcon /> },
  { name: "공중화장실", icon: <WcIcon /> },
];

export const timeHandle = (time) => {
  if (time < 60) return `${time}"`;
  else if (time < 3600) return `${parseInt(time / 60)}' ${time % 60}"`;
  else return `${parseInt(time / 3600)}h ${parseInt((time % 3600) / 60)}'`;
};

export const expectTimeHandle = (time) => {
  if (time < 60) return `${time}분`;
  else return `${parseInt(time / 60)}시간 ${parseInt(time % 60)}분`;
};

export const distanceHandle = (dis) => {
  if (dis < 1000) return dis;
  else return parseFloat(dis / 1000.0).toFixed(2);
};

export const speedHandle = (dis, idle) => {
  return parseFloat(((dis * 3.6) / idle).toFixed(1));
};
