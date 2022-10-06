import { API_SERVER, axios } from "./api";

const API_SERVER_RANK = API_SERVER + "/rank";

const instance = axios.create({
  baseURL: API_SERVER_RANK,
  headers: {
    contentType: "application/json",
  },
});

const authInstance = axios.create({
  baseURL: API_SERVER_RANK,
  headers: {
    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    contentType: "application/json",
  },
});

// 전체 랭킹(시간)
const getTotalRankTime = async (success, fail) => {
  await instance.get("/total/time").then(success).catch(fail);
};

// 전체 랭킹(거리)
const getTotalRankDistance = async (success, fail) => {
  await instance.get("/total/distance").then(success).catch(fail);
};

// 전체 랭킹(최고 속도)
const getTotalRankBestSpeed = async (success, fail) => {
  await instance.get("/total/speed").then(success).catch(fail);
};

// 코스별 랭킹(시간순, 빨리 주행한 순)
const getCourseRankTime = async (cousreId, success, fail) => {
  await instance.get(`/course/${cousreId}`).then(success).catch(fail);
};

// 개인 랭킹(시간순) + 상위 3명
const getUserRankTime = async (success, fail) => {
  await authInstance.get("/member/time").then(success).catch(fail);
};

// 개인 랭킹(거리순) + 상위 3명
const getUserRankDistance = async (success, fail) => {
  await authInstance.get("/member/distance").then(success).catch(fail);
};

// 개인 랭킹(속도순) + 상위 3명
const getUserRankSpeed = async (success, fail) => {
  await authInstance.get("/member/speed").then(success).catch(fail);
};

export {
  getTotalRankTime,
  getTotalRankDistance,
  getTotalRankBestSpeed,
  getCourseRankTime,
  getUserRankTime,
  getUserRankDistance,
  getUserRankSpeed,
};
