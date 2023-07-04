import React, {ReactNode} from "react";
import {Layout} from "antd";
import {Navigation} from "../Navigation";
import {Content} from "antd/es/layout/layout";

type Props = {
    children: ReactNode
}
const contentStyle: React.CSSProperties = {
    justifyContent: 'center',
    alignItems: 'center',
    display: 'flex',
    flexDirection: 'column',
};
export const MainCenterContainer: React.FC<Props> = (props) => (
    <Layout style={{
        width: '100vw',
        height: '100vh'
    }}>
        <Navigation/>
        <Content style={contentStyle}>{props.children}</Content>
    </Layout>
)
