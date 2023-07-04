import React from 'react';
import './App.css';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {Login} from "./layouts/Login";
import {Provider} from "react-redux";
import {Main} from "./layouts/Main";
import {store} from "./store/models";
import {PrivateComponent} from "./component/common/PrivateComponent";

export const App = () => {
    const router = createBrowserRouter([
        {
            path: "",
            element: (
                <PrivateComponent>
                    <Main/>
                </PrivateComponent>
            )
        },
        {
            path: "login",
            element: (
                <Login/>
            )
        }
    ])
    return (
        <Provider store={store}>
            <RouterProvider router={router}/>
        </Provider>
    )
}
