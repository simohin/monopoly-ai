import {getToken} from "../api/auth";
import React from "react";
import {CredentialsInputForm} from "../component/common/form/CredentialsInputForm";

type Props = {
    onSuccess: () => void
}

export const Login: React.FC<Props> = (props) => (
    <CredentialsInputForm
        titleText={'Добро пожаловать!'}
        buttonText={'Войти'}
        onSubmit={getToken}
        onSuccess={props.onSuccess}
    />
)
