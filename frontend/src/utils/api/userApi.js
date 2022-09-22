import {API_SERVER, axios, SERVER_URL} from "./api";

const API_SERVER_USER = API_SERVER + "/member";

const instance = axios.create({
    baseURL: API_SERVER_USER,
    headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        contentType: "application/json",
    },
});

// instance.interceptors.request.use(checkToken);

const updateMoreInfo = async (data) => {
    const {status} = await instance.put("/info", data);
    return status;
}

const checkDuplicateNickname = async (data) => {
    let {data: res} = await instance.get(`/check/${data}`);
    return res;
}

export {updateMoreInfo,checkDuplicateNickname};