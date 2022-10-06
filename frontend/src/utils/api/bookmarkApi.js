import {API_SERVER, axios} from "./api";
import {useNavigate} from "react-router-dom";

const API_SERVER_BOOKMARK = API_SERVER + "/bookmark";

const authInstance = axios.create({
    baseURL: API_SERVER_BOOKMARK,
    headers: {
        contentType: "application/json",
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
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

// 북마크 생성
const makeBookmark = async (data, success, fail) => {
    await authInstance.post(`/course/${data}`).then(success).catch(fail);
};

// 북마크 제거
const deleteBookmark = async (data, success, fail) => {
    await authInstance.delete(`/${data}`).then(success).catch(fail);
};

// 북마크된 코스 리스트 가져옴
const getBookmarkedCourses = async (success, fail) => {
    await authInstance.get(`/course`).then(success).catch(fail);
};

export {makeBookmark, deleteBookmark, getBookmarkedCourses};