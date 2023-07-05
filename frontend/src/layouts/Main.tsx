import {MainCenterContainer} from "../component/common/MainCenterContainer";
import {BottomNavigation, BottomNavigationAction, Box} from "@mui/material";
import {useEffect, useState} from "react";
import {Room} from "../view/Room";
import {Settings} from "../view/Settings";
import {useSelector} from "react-redux";
import {RootState} from "../store/models";
import {Login} from "./Login";

interface TabPanelProps {
    children?: React.ReactNode;
    index: number;
    value: number;
}

function TabPanel(props: TabPanelProps) {
    const {children, value, index, ...other} = props;

    return (
        <Box
            role="tabpanel"
            hidden={value !== index}
            id={`tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
            sx={{
                flexGrow: '1'
            }}>
            {children}
        </Box>
    );
}

export const Main = () => {

    const authState = useSelector((state: RootState) => state.auth)
    const [current, setCurrent] = useState(0)
    const isLoggedIn = (typeof authState?.token) !== 'undefined'

    useEffect(() => {
        setCurrent(isLoggedIn ? 0 : 2)
    }, [isLoggedIn])

    return (
        <MainCenterContainer>
            <TabPanel value={current} index={0}>
                <Room/>
            </TabPanel>
            <TabPanel value={current} index={1}>
                <Settings/>
            </TabPanel>
            <TabPanel value={current} index={2}>
                <Login onSuccess={() => setCurrent(0)}/>
            </TabPanel>
            <BottomNavigation
                showLabels
                value={current}
                onChange={(event, newValue) => {
                    setCurrent(newValue);
                }}
            >
                <BottomNavigationAction sx={{display: isLoggedIn ? 'block' : 'none'}} label="Комнаты"/>
                <BottomNavigationAction sx={{display: isLoggedIn ? 'block' : 'none'}} label="Настройки"/>
                <BottomNavigationAction sx={{display: isLoggedIn ? 'none' : 'block'}} label="Вход"/>
            </BottomNavigation>
        </MainCenterContainer>
    )
}
