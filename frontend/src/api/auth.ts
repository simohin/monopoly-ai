import {baseClient} from "./client";
import {Credentials, TokenResponse} from "./types";
import {store} from "../store/models";

export const getToken = async (credentials: Credentials) => {
    return baseClient.post('/auth/tokens', credentials)
        .then(r => r.data as TokenResponse)
        .then(r => store.dispatch.auth.setToken(r.token))
}
export const register = async (credentials: Credentials) => {
    return baseClient.post('/auth/users', credentials)
        .then(() => getToken(credentials))
}
