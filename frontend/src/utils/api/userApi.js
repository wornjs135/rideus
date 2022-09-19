import { API_SERVER, axios } from "./api";

const API_SERVER_USER = API_SERVER + "user/";

const instance = axios.create({
    baseURL: API_SERVER_USER,
    headers: {
        contentType: "application/json",
    },
});