import React, {ReactNode} from "react";
import {Navigation} from "../Navigation";
import {Box} from "@mui/material";

type Props = {
    children: ReactNode
}
const contentStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'column',
    margin: '32px',
    flexGrow: '1'
};
export const MainCenterContainer: React.FC<Props> = (props) => (
    <Box style={{
        display: 'flex',
        flexDirection: 'column',
        minWidth: '100vw',
        minHeight: '100vh'
    }}>
        <Navigation/>
        <Box sx={contentStyle}>{props.children}</Box>
    </Box>
)
