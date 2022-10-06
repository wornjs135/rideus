import { API_SERVER, axios } from "./api";

const API_SERVER_WEATHER = API_SERVER + "/weather";

const instance = axios.create({
  baseURL: API_SERVER_WEATHER,
  headers: {
    contentType: "application/json",
  },
});

const getWeather = async (params, success, fail) => {
  await instance.get(`/gps`, { params: params }).then(success).catch(fail);
};

export { getWeather };
