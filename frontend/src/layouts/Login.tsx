import {MainCenterContainer} from "../component/common/MainCenterContainer";
import {getToken} from "../api/auth";
import {Navigate, useSearchParams} from "react-router-dom";
import {useSelector} from "react-redux";
import {RootState} from "../store/models";
import React, {useEffect, useState} from "react";
import {AxiosError} from "axios";
import {Alert, Box, Button, TextField, Typography} from "@mui/material";

export const Login = () => {

    const [errors, setErrors] = useState<string[]>([])

    const [username, setUsername] = useState<string>()
    const [password, setPassword] = useState<string>()

    const [buttonDisabled, setButtonDisabled] = useState<boolean>(typeof username === 'undefined' && typeof password === 'undefined')

    useEffect(() => {
        setButtonDisabled((username || '').length <= 0 || (password || '').length <= 0)
    }, [username, password])

    const handleSubmit = () => {
        getToken({
            login: username || '',
            password: password || ''
        }).catch((e: AxiosError) => {
            errors.pop()
            switch (e.response?.status || 0) {
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

    const [searchParams] = useSearchParams()
    const redirectTo = searchParams.get('redirect-to') || '/'
    const authState = useSelector((state: RootState) => state.auth)

    return (typeof authState.token === "undefined") ? (
        <MainCenterContainer>
            <Box
                component={'form'}
                autoComplete={'off'}
                sx={{
                    display: 'flex',
                    flexGrow: 1,
                    flexDirection: 'column',
                    justifyContent: 'center',
                    alignItems: 'center',
                    minHeight: '90vh',
                    textAlign: 'center'
                }}
            >
                <Typography margin={'16px'} variant={'h2'}>Войти</Typography>
                <Box sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '8px',
                    mb: '16px',
                }}>
                    <TextField
                        onChange={e => setUsername(e.target.value)}
                        required
                        id="username"
                        label="Имя пользователя"
                    />
                    <TextField
                        onChange={e => setPassword(e.target.value)}
                        required
                        inputProps={{type: 'password'}}
                        id="password"
                        label="Пароль"
                    />
                    <Button
                        onClick={handleSubmit}
                        disabled={buttonDisabled}
                        variant={'contained'}
                    >
                        Войти</Button>
                </Box>
            </Box>

            <Box
                sx={{
                    gap: '8px',
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'center',
                    alignItems: 'center',
                    textAlign: 'center'
                }}
            >
                {errors.map(e => <Alert key={Math.floor(Math.random() * 9999)} severity="error">{e}</Alert>)}
            </Box>
        </MainCenterContainer>
    ) : (<Navigate to={redirectTo}/>)
}
