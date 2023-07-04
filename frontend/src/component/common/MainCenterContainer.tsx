import React, {ReactNode} from "react";
import {Navigation} from "../Navigation";
import {Box} from "@mui/material";

type Props = {
    children: ReactNode
}
const contentStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'column',
    margin: '8px'
};
export const MainCenterContainer: React.FC<Props> = (props) => (
    <Box style={{
        display: 'flex',
        flexDirection: 'column',
        width: '100vw',
        height: '100vh'
    }}>
        <Navigation/>
        <Box style={contentStyle}>{props.children}</Box>
    </Box>
)
