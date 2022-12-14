import {API_SERVER, axios} from "./api";
import {useNavigate} from "react-router-dom";

const API_SERVER_USER = API_SERVER + "/member";

const authInstance = axios.create({
    baseURL: API_SERVER_USER,
    headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        contentType: "application/json",
    },
});

const instance = axios.create({
    baseURL: API_SERVER_USER,
    headers: {
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

const updateMoreInfo = async (data, success, fail) => {
    await authInstance.put("/info", data).then(success).catch(fail);
}

const checkDuplicateNickname = async (data, success, fail) => {
    await instance.get(`/check/${data}`).then(success).catch(fail);
}

const recentRide = async (success, fail) => {
    await authInstance.get("/recent").then(success).catch(fail);
};

const myInfo = async (success, fail) => {
    await authInstance.get("/me").then(success).catch(fail);
};

const myRides = async (success, fail) => {
    await authInstance.get("/recent/my-ride").then(success).catch(fail);
};

const getTotalRecord = async (success, fail) => {
    await authInstance.get("/total-record").then(success).catch(fail);
};


const getMyTag = async (success, fail) => {
    await authInstance.get("/my-tag").then(success).catch(fail);
};

export {updateMoreInfo, checkDuplicateNickname, recentRide, myInfo, myRides, getTotalRecord, getMyTag};