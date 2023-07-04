import axios from "axios";
import {BASE_URL} from "../config";
import {store} from "../store/models";

const baseConfig = {
    baseURL: BASE_URL
}

const secureConfig = {
    ...baseConfig,
    headers: {
        "Authorization": `Bearer ${store.getState().auth.token}`
    }
}

export const baseClient = axios.create(baseConfig)
export const secureClient = axios.create(secureConfig)
