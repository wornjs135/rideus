import { API_SERVER, axios } from "./api";

const API_SERVER_NEAR = API_SERVER + "/near";

const instance = axios.create({
  baseURL: API_SERVER_NEAR,
  headers: {
    contentType: "application/json",
  },
});

// 코스 주변정보 가져오기

export { getCourseNearInfo };
