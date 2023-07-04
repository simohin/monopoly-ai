import React, {ReactNode} from "react";
import {useSelector} from "react-redux";
import {RootState} from "../../store/models";
import {Navigate} from "react-router-dom";

type Props = {
    children: ReactNode,
    redirectTo?: string
}

export const PrivateComponent: React.FC<Props> = (props) => {

    const authState = useSelector((state: RootState) => state.auth)
    const params = props.redirectTo ? '?redirect-to=' + props.redirectTo : ''

    return authState.token ? (<>{props.children}</>) : (<Navigate to={'/login' + params}/>)
}
