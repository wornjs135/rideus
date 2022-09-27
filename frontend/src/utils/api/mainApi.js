import { API_SERVER, axios } from "./api";

const API_SERVER_POPULAR = API_SERVER + "/popular";

const instance = axios.create({
  baseURL: API_SERVER_POPULAR,
  headers: {
    contentType: "application/json",
  },
});

const authInstance = axios.create({
  baseURL: API_SERVER_POPULAR,
  headers: {
    contentType: "application/json",
    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
  },
});

// 메인화면에서 인기코스 가져오기
const getPopularCourses = async (success, fail) => {
  await instance.get("/course").then(success).catch(fail);
};

// 현 위치 기반 추천 코스
const getRecommendationCourseByLocation = async (lat, lng, success, fail) => {
  await authInstance.get(`/${lat}/${lng}`).then(success).catch(fail);
};

// 인기태그
const getPopularTags = async (success, fail) => {
  await instance.get("/tag").then(success).catch(fail);
};

export { getPopularCourses, getRecommendationCourseByLocation, getPopularTags };
