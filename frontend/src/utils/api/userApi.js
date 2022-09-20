import {API_SERVER, axios, checkToken} from "./api";

const API_SERVER_USER = API_SERVER + "user/";

const instance = axios.create({
    baseURL: API_SERVER_USER,
    headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        contentType: "application/json",
    },
});

instance.interceptors.request.use(checkToken);

const updateMoreInfo = async (data) => {
    const res = await instance.put("/info", data);
}

export {updateMoreInfo};