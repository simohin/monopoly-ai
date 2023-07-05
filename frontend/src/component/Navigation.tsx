import {RootState, store} from "../store/models";
import {useSelector} from "react-redux";
import {AppBar, Button, Toolbar, Typography} from "@mui/material";
import React from "react";

export const Navigation = () => {
    const handleClick = () => {
        store.dispatch.auth.logout()
    }
    const authState = useSelector((state: RootState) => state.auth)
    const baseButtonStyle = {
        marginY: '16px',
        marginRight: '32px',
        marginLeft: '16px',
    }

    const buttonStyle = (typeof authState?.token) === 'undefined' ? {
        display: 'none'
    } : {}

    const headerTitleStyle = {
        marginY: '16px',
        marginRight: '16px',
        marginLeft: '32px',
    };
    return (
        <AppBar position="static">
            <Toolbar disableGutters sx={{justifyContent: 'space-between'}}>
                <Typography sx={headerTitleStyle} variant={'h4'}>Monopoly.AI</Typography>
                <Button color="error" variant={'contained'} sx={{...baseButtonStyle, ...buttonStyle}}
                        onClick={handleClick}>Выйти</Button>
            </Toolbar>
        </AppBar>
    )
}
