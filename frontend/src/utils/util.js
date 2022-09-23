const util = () => {};

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

export const timeHandle = (time) => {
  if (time < 60) return `${time}초`;
  else if (time < 3600) return `${parseInt(time / 60)}분 ${time % 60}초`;
  else return `${parseInt(time / 3600)}시간 ${parseInt((time % 3600) / 60)}분`;
};

export const distanceHandle = (dis) => {
  if (dis < 1000) return dis;
  else return parseFloat(dis / 1000.0).toFixed(2);
};

export const speedHandle = (dis, idle) => {
  return parseFloat(((dis * 3.6) / idle).toFixed(1));
};
