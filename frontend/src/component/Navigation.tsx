import {RootState, store} from "../store/models";
import {useSelector} from "react-redux";
import {Box, Button, Container, Typography} from "@mui/material";

export const Navigation = () => {
    const handleClick = () => {
        store.dispatch.auth.logout()
    }
    const authState = useSelector((state: RootState) => state.auth)
    const baseButtonStyle = {
        margin: '8px',
    }

    const buttonStyle = (typeof authState?.token) === 'undefined' ? {
        display: 'none'
    } : {}

    return (
        <Box style={{
            alignItems: 'center',
            display: 'flex',
            width: '100vw',
            justifyContent: 'space-between',
            textAlign: 'center'
        }}>
            <Typography style={{margin: '8px'}} variant={'h4'}>Monopoly.AI</Typography>
            <Button variant={'contained'} style={{...baseButtonStyle, ...buttonStyle}} onClick={handleClick}>Выйти</Button>
        </Box>
    )
}
