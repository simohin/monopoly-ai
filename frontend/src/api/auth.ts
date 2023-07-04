import {baseClient} from "./client";
import {LoginRequest, TokenResponse} from "./types";
import {store} from "../store/models";

export const getToken = async (credentials: LoginRequest) => {
    return baseClient.post('/auth/tokens', credentials)
        .then(r => r.data as TokenResponse)
        .then(r => store.dispatch.auth.setToken(r.token))
}
