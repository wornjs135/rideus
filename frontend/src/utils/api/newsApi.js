import { API_SERVER, axios } from "./api";

const API_SERVER_NEWS = API_SERVER + "/news";

const instance = axios.create({
  baseURL: API_SERVER_NEWS,
  headers: {
    contentType: "application/json",
  },
});

const getNews = async (success, fail) => {
  await instance.get().then(success).catch(fail);
};

export { getNews };
