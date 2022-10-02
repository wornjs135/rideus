import {API_SERVER, axios} from "./api";

const API_SERVER_RECORD = API_SERVER + "/record";

const authInstance = axios.create({
    baseURL: API_SERVER_RECORD,
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

const getRecordWithGroup = async (data, success, fail) => {
    await authInstance.get(`/room/${data}`).then(success).catch(fail);
}

export {getRecordWithGroup}