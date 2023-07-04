import {Button, Layout} from 'antd';
import {RootState, store} from "../store/models";
import {useSelector} from "react-redux";

const {Header} = Layout;
export const Navigation = () => {
    const handleClick = () => {
        store.dispatch.auth.logout()
    }
    const authState = useSelector((state: RootState) => state.auth)

    const buttonStyle = (typeof authState?.token) === 'undefined' ? {
        display: 'none'
    } : {}

    return (
        <Header style={{
            alignItems: 'center',
            display: 'flex',
            width: '100vw',
            justifyContent: 'flex-end'
        }}>
            <Button style={buttonStyle} onClick={handleClick}>Выйти</Button>
        </Header>
    )
}
