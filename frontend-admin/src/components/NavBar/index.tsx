import React, { FC, ReactNode, useState } from 'react';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { Menu, Spin, Tag } from 'antd';
import { CrownOutlined, FormOutlined, DesktopOutlined } from '@ant-design/icons';

interface EntryTree {
  key: string,
  title: string,
  icon?: ReactNode,
  link?: string,
  children?: EntryTree[],
  disabled?: boolean
}

const entryTree: EntryTree[] = [
  {
    key: '0',
    title: '一级目录',
    icon: <CrownOutlined />,
    children: [
      {
        key: '1.1',
        title: '二级目录',
        // link: '/reserve/resource'
      },
    ],
  },
  {
    key: '1',
    title: '一级目录',
    icon: <FormOutlined />,
    children: [
      {
        key: '1.1',
        title: '二级目录',
        // link: '/reserve/resource'
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
      },
    ],
  },
];

const NavBar: FC = () => {
  const { configure: config } = useSelector((state: RootState) => state.app);

  const [loading, setLoading] = useState<boolean>(false);

  return (
    <div>
      <Link to="/home">
        {/* <img */}
        {/*  src={`${ossURL}/img/${isMini ? 'mini_' : ''}logo.svg`} */}
        {/*  style={isMini ? */}
        {/*    { width: '36px', margin: '19px 22px 44px' } : */}
        {/*    { width: '188px', margin: '19px 34px 44px' } */}
        {/*  } */}
        {/* /> */}
      </Link>
      <Spin spinning={loading}>
        <Menu
          mode="inline"
          style={{
            borderRight: 'none', zIndex: 99, width: 188, marginTop: 16,
          }}
        >
          {entryTree.map((entry) => (
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
                    {
                      subEntry.title
                    }
                  </Menu.Item>
                ))
}
            </Menu.SubMenu>
          ))}
        </Menu>
      </Spin>
    </div>
  );
};

export default NavBar;
export { entryTree };
