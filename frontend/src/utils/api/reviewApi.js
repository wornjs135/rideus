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

authInstance.interceptors.request.use(function (config) {
      const token = localStorage.getItem("accessToken");
      if (token) {
        config.headers["Authorization"] = 'Bearer ' + token;
      } else {
          window.location.href = "/login";
      }
      return config;
    },
    function (error) {
      return Promise.reject(error);
    }
);

const authFormDataInstance = axios.create({
  baseURL: API_SERVER_REVIEW,
  headers: {
    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    contentType: "multipart/form-data",
  },
});

authFormDataInstance.interceptors.request.use(function (config) {
      const token = localStorage.getItem("accessToken");
      if (token) {
        config.headers["Authorization"] = 'Bearer ' + token;
      }
      return config;
    },
    function (error) {
      return Promise.reject(error);
    }
);

// 리뷰 작성(이미지 1정 포함)
const writeReview = async (formData, success, fail) => {
  await authFormDataInstance.post("/write", formData).then(success).catch(fail);
};

// 코스별 리뷰 목록
const getCourseAllReview = async (courseId, success, fail) => {
  await instance.get(`/all/${courseId}`).then(success).catch(fail);
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
