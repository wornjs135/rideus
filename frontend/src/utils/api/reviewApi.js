import { API_SERVER, axios } from "./api";

const API_SERVER_REVIEW = API_SERVER + "/review";

const instance = axios.create({
  baseURL: API_SERVER_REVIEW,
  headers: {
    contentType: "application/json",
  },
});

const authInstance = axios.create({
  baseURL: API_SERVER_REVIEW,
  headers: {
    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    contentType: "application/json",
  },
});

const authFormDataInstance = axios.create({
  baseURL: API_SERVER_REVIEW,
  headers: {
    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    contentType: "multipart/form-data",
  },
});

// 리뷰 작성(이미지 1정 포함)
const writeReview = async (formData, success, fail) => {
  await authFormDataInstance.post("/write", formData).then(success).catch(fail);
};

// 코스별 리뷰 목록
const getCourseAllReview = async (courseId, success, fail) => {
  await instance.get(`/${courseId}`).then(success).catch(fail);
};

// 리뷰 상세(courseId는 없어도 될거같긴함. 백엔드에서 수정해야함.)
const getReivewDetail = async (reviewId, success, fail) => {
  await instance.get(`/${reviewId}`).then(success).catch(fail);
};

// 리뷰 좋아요
const likeReview = async (data, success, fail) => {
  await authInstance.post("/click", data).then(success).catch(fail);
};

export { writeReview, getCourseAllReview, getReivewDetail, likeReview };
