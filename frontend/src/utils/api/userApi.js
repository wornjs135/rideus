import {API_SERVER, axios} from "./api";

const API_SERVER_USER = API_SERVER + "/member";

const authInstance = axios.create({
    baseURL: API_SERVER_USER,
    headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        contentType: "application/json",
    },
});

// instance.interceptors.request.use(checkToken);

const updateMoreInfo = async (data, success, fail) => {
    await authInstance.put("/info", data).then(success).catch(fail);
}

const checkDuplicateNickname = async (data, success, fail) => {
    await authInstance.get(`/check/${data}`).then(success).catch(fail);
}

const recentRide = async (success, fail) => {
    await authInstance.get("/recent").then(success).catch(fail);
};

const myInfo = async (success, fail,config) => {
    await authInstance.get("/me",config).then(success).catch(fail);
};

const myRides = async (success, fail) => {
    await authInstance.get("/recent/my-ride").then(success).catch(fail);
};

const bookmarkedCourses = async (data, success, fail) => {
    await authInstance.get("/me").then(success).catch(fail);
};

export {updateMoreInfo, checkDuplicateNickname, recentRide, myInfo, myRides, bookmarkedCourses};