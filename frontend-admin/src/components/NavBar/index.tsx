import React, { FC, ReactNode }                      from 'react';
import { Link }                                      from 'react-router-dom';
import { Avatar, Layout, Menu } from 'antd';
import {
  TeamOutlined,
  BarsOutlined,
  UserOutlined,
  FacebookOutlined,
  TrademarkOutlined,
  ManOutlined, GoogleOutlined,
} from '@ant-design/icons';
import './index.css';

const {
  Header, Content, Footer, Sider,
} = Layout;

interface EntryTree {
  key: string,
  title: string,
  icon?: ReactNode,
  link?: string,
  children?: EntryTree[]
}

const entryTree: EntryTree[] = [
  {
    key: '0',
    title: '用户管理',
    icon: <UserOutlined />,
    link: '/users',
  },
  {
    key: '1',
    title: '文章管理',
    icon: <BarsOutlined />,
    link: '/articles',
  },
  {
    key: '2',
    title: '关于我们',
    icon: <TeamOutlined />,
    link: '/about',
  },
];

const NavBar: FC = () => (
  <div>
    <Link to="/home">
      <div className="nav-bar-title">
        {/*<FacebookOutlined />*/}
        {/*<TrademarkOutlined />*/}
        {/*<ManOutlined />*/}
        {/*<GoogleOutlined />*/}
        {/*<Avatar src="public/logo.png" shape="square" />*/}
        Frogsoft CMS
      </div>
    </Link>
    <Menu
      mode="inline"
      style={{
        zIndex: 99, width: '100%',
      }}
    >
      {entryTree.map((entry) => (
        entry.children ? (
          <Menu.SubMenu
            key={entry.key}
            icon={entry.icon}
            title={entry.title}
          >
            {
              entry?.children?.map((subEntry) => (
                <Menu.Item
                  key={subEntry.key}
                  title={subEntry.title}
                >
                  <Link to={subEntry.link}>
                    {subEntry.title}
                  </Link>
                </Menu.Item>
              ))
            }
          </Menu.SubMenu>
        ) : (
          <Menu.Item
            key={entry.key}
            icon={entry.icon}

          >
            <Link to={entry.link}>
              {entry.title}
            </Link>
          </Menu.Item>
        )
      ))}
    </Menu>
  </div>
);

export default NavBar;
export { entryTree };
