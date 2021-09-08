import React, { FC, ReactNode }                                       from 'react';
import { Link }                                                       from 'react-router-dom';
import { Avatar, Menu, Row }                                         from 'antd';
import {
  BarsOutlined, SettingOutlined, TeamOutlined, UserOutlined,
} from '@ant-design/icons';
import './index.css';

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
    title: '个性配置',
    icon: <SettingOutlined />,
    link: '/configuration',
  },
  {
    key: '3',
    title: '关于我们',
    icon: <TeamOutlined />,
    link: '/about',
  },
];

const NavBar: FC = () => (
  <div>
    <Link to="/home">
      <div className="nav-bar-title" style={{ marginTop: '20px' }}>
        <Row justify="center">
          <Avatar src="/logo.svg" shape="square" size={120} />
          Frogsoft CMS
        </Row>
      </div>
    </Link>
    <Menu
      mode="inline"
      style={{
        zIndex: 99,
        width: '100%',
        fontSize: '90%',
        alignItems: 'center',
        marginTop: '20px',
        backgroundColor: '#FFFFFF00',
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
