import {createModel} from "@rematch/core";
import {RootModel} from "./models";

type AuthState = {
    token: string | undefined
}

export const auth = createModel<RootModel>()({
    state: {
        token: undefined
    } as AuthState,
    reducers: {
        set(state, payload) {
            return payload
        }
    },
    effects: (dispatch) => ({
        setToken(token: string) {
            dispatch.auth.set({token: token})
        },
        logout() {
            dispatch.auth.set({})
        }
    })
})
