import React, {useEffect, useState} from "react";
import {AxiosError} from "axios/index";
import {Alert, Box, Button, TextField, Typography} from "@mui/material";
import {Credentials} from "../../../api/types";

type Props = {
    titleText: string,
    buttonText: string,
    onSubmit: (credentials: Credentials) => Promise<void>
    onSuccess: () => void
}

type BadRequestResponse = {
    message: string
}

export const CredentialsInputForm: React.FC<Props> = (props) => {
    const [errors, setErrors] = useState<string[]>([])

    const [username, setUsername] = useState<string>()
    const [password, setPassword] = useState<string>()

    const [buttonDisabled, setButtonDisabled] = useState<boolean>(typeof username === 'undefined' && typeof password === 'undefined')

    useEffect(() => {
        setButtonDisabled((username || '').length <= 0 || (password || '').length <= 0)
    }, [username, password])

    const handleSubmit = () => {
        props.onSubmit({
            login: username || '',
            password: password || ''
        })
            .then(props.onSuccess)
            .catch((e: AxiosError) => {
                errors.pop()
                switch (e.response?.status || 0) {
                    case 400: {
                        setErrors(errors.concat([(e.response?.data as BadRequestResponse).message || 'Некорректный ввод']))
                        break;
                    }
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

    return (
        <>
            <Box
                component={'form'}
                autoComplete={'off'}
                sx={{
                    display: 'flex',
                    flexGrow: 1,
                    flexDirection: 'column',
                    justifyContent: 'center',
                    alignItems: 'center',
                    textAlign: 'center'
                }}
            >
                <Typography margin={'16px'} variant={'h2'}>{props.titleText}</Typography>
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
                        {props.buttonText}
                    </Button>
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
                {errors.map(e => <Alert key={1} onClick={() => setErrors([])} severity="error">{e}</Alert>)}
            </Box>
        </>
    )
}
