import React, { FC, ReactNode }                        from 'react';
import { Link }                                        from 'react-router-dom';
import { Layout, Menu }                                from 'antd';
import { DesktopOutlined, FormOutlined, UserOutlined } from '@ant-design/icons';
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
    link: '/user',
  },
  {
    key: '1',
    title: '一级目录',
    icon: <FormOutlined />,
    children: [
      {
        key: '1.1',
        title: '二级目录',
        link: '/user',
      },
    ],
  },
  {
    key: '2',
    title: '一级目录',
    icon: <DesktopOutlined />,
    children: [
      {
        key: '2.1',
        title: '二级目录',
        link: '/user',
      },
    ],
  },
];

const NavBar: FC = () => (
  <div>
    <Link to="/home">
      <div className="nav-bar-title">
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
