import {API_SERVER, axios, checkToken} from "./api";
import {SERVER_URL} from "../data";

const API_SERVER_USER = SERVER_URL + "/member";

const instance = axios.create({
    baseURL: API_SERVER_USER,
    headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        contentType: "application/json",
    },
});

instance.interceptors.request.use(checkToken);

const updateMoreInfo = async (data) => {
    const {status} = await instance.put("/info", data);
    return status;
}

const checkDuplicateNickname = async (data) => {
    let {data: res} = await instance.get(`/check/${data}`);
    return res;
}

export {updateMoreInfo,checkDuplicateNickname};