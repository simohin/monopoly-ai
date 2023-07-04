import {init, Models, RematchDispatch, RematchRootState} from "@rematch/core";
import {ExtraModelsFromLoading} from '@rematch/loading'
import persistPlugin from "@rematch/persist";
import storage from "redux-persist/lib/storage";
import {auth} from "./auth";

export interface RootModel extends Models<RootModel> {
    auth: typeof auth
}

const models: RootModel = {auth}

const persistConfig = {
    key: "root",
    storage,
};
export const store = init<RootModel, ExtraModelsFromLoading<RootModel>>({
    models,
    plugins: [persistPlugin(persistConfig)]
})

export type Store = typeof store
export type Dispatch = RematchDispatch<RootModel>
export type RootState = RematchRootState<RootModel>
