import {Alert, Button, Form, Input} from "antd";
import {MainCenterContainer} from "../component/common/MainCenterContainer";
import {getToken} from "../api/auth";
import {Navigate, useSearchParams} from "react-router-dom";
import {useSelector} from "react-redux";
import {RootState} from "../store/models";
import React, {useState} from "react";
import {AxiosError} from "axios";

export const Login = () => {


    const [errors, setErrors] = useState<String[]>([])
    const onFinish = (values: any) => {
        getToken({
            login: values.username,
            password: values.password
        }).catch((e: AxiosError) => {
            switch (e.response?.status) {
                case 401: {
                    setErrors(errors.concat(["Доступ запрещён"]))
                    break;
                }
                case 404: {
                    setErrors(errors.concat(["Пользователь не найден"]))
                    break;
                }
                default:
                    setErrors(errors.concat(["Что-то пошло не так"]))
            }
        })
    };

    const onFinishFailed = (errorInfo: any) => {
        console.log('Failed:', errorInfo);
    };

    const [searchParams] = useSearchParams()
    const redirectTo = searchParams.get('redirect-to') || '/'
    const authState = useSelector((state: RootState) => state.auth)

    return (typeof authState.token === "undefined") ? (
        <MainCenterContainer>
            {errors.map(e => (
                <Alert
                    style={{marginBottom: '8px'}}
                    message={e}
                    type="error"
                    closable
                />))}
            <Form
                name="basic"
                style={{marginTop: '16px'}}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                autoComplete="off"
            >
                <Form.Item
                    name="username"
                    rules={[{required: true, message: 'Имя пользователя обязательно'}]}
                >
                    <Input
                        placeholder="Имя пользователя"
                    />
                </Form.Item>

                <Form.Item
                    name="password"
                    rules={[{required: true, message: 'Пароль обязателен'}]}
                >
                    <Input.Password
                        placeholder="Пароль"
                    />
                </Form.Item>

                <Form.Item>
                    <Button style={{width: '100%'}} type="primary" htmlType="submit">
                        Войти
                    </Button>
                </Form.Item>
            </Form>
        </MainCenterContainer>
    ) : (<Navigate to={redirectTo}/>)
}
