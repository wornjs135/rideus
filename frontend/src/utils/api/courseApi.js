import { API_SERVER, axios } from "./api";

const API_SERVER_COURSE = API_SERVER + "/course";

const authInstance = axios.create({
  baseURL: API_SERVER_COURSE,
  headers: {
    contentType: "application/json",
    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
  },
});

// 리뷰 태그 기반 코스 추천 리스트
const getRecommendationCourses = async (success, fail) => {
  await authInstance.get("/recommendation").then(success).catch(fail);
};

// 추천 코스 리스트 조회
const getAllCourse = async (success, fail) => {
  await authInstance.get("/").then(success).catch(fail);
};

// 추천 코스 상세 조회
const getCourseDetail = async (courseId, success, fail) => {
  await authInstance.get(`/${courseId}`).then(success).catch(fail);
};

// 코스 검색
const searchCourse = async (keyword, success, fail) => {
  await authInstance.get(`/search/${keyword}`).then(success).catch(fail);
};

// 코스 추가 (사용자가 탄 코스 추가하는 경우)
const addCourse = async (data, success, fail) => {
  await authInstance.post("/add", data).then(success).catch(fail);
};

export {
  getRecommendationCourses,
  getAllCourse,
  getCourseDetail,
  searchCourse,
  addCourse,
};
