import { API_SERVER, axios } from "./api";

const API_SERVER_RIDE = API_SERVER + "/ride";

const authInstance = axios.create({
  baseURL: API_SERVER_RIDE,
  headers: {
    contentType: "application/json",
    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
  },
});

// 그룹라이딩 방 생성
const createGroupRoom = async (success, fail) => {
  await authInstance.post("/room").then(success).catch(fail);
};

// 주행 시작
const startRidding = async (success, fail) => {
  await authInstance.post("/start").then(success).catch(fail);
};

// 주행 중간중간에 좌표 리스트들 저장
const saveCoordinatesDuringRide = async (recordId, data, success, fail) => {
  await authInstance.post(`/save/${recordId}`, data).then(success).catch(fail);
};

// 주행 종료
const finishRidding = async (riddingType, data, success, fail) => {
  await authInstance
    .post(`/finish/${riddingType}`, data)
    .then(success)
    .catch(fail);
};

export {
  createGroupRoom,
  startRidding,
  saveCoordinatesDuringRide,
  finishRidding,
};
