import {register} from "../api/auth";
import React from "react";
import {CredentialsInputForm} from "../component/common/form/CredentialsInputForm";

type Props = {
    onSuccess: () => void
}

export const Register: React.FC<Props> = (props) => (
    <CredentialsInputForm
        titleText={'Создайте пользователя'}
        buttonText={'Зарегистрироваться'}
        onSubmit={register}
        onSuccess={props.onSuccess}
    />
)
