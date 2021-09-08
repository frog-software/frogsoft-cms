import React, { FC, ReactNode, useCallback } from 'react';
import { Breadcrumb, Tabs }                  from 'antd';
import { BreadcrumbOption, TabOption }       from 'types/antd';
import { useHistory }                        from 'react-router';
import {
  Block, Description, Tab, Title,
}   from './style';

interface HeaderProps {
  title: ReactNode,
  description?: ReactNode,
  breadcrumbs?: BreadcrumbOption[],
  tabs?: TabOption[],
  selectTabKey?: string,
  onTabClick?: (key: string) => void
}

const Header: FC<HeaderProps> = (props) => {
  const {
    title, description, breadcrumbs, tabs, selectTabKey, onTabClick,
  }              = props;
  const history        = useHistory();
  const { TabPane }      = Tabs;
  const handleClickTab = useCallback((key) => {
    if (!tabs || !tabs.length) return;
    history.push(tabs.filter((item) => item.key === key)[0].link);
    onTabClick?.(key);
  }, [history, onTabClick, tabs]);
  return (
    <Block>
      {breadcrumbs && breadcrumbs.length
        ? (
          <Breadcrumb>
            {breadcrumbs.map((item) => (
              <Breadcrumb.Item key={item.name} href={item.link}>{item.name}</Breadcrumb.Item>
            ))}
          </Breadcrumb>
        )
        : null}
      <Title>{title}</Title>
      <Description>{description}</Description>
      {tabs && tabs.length
        ? (
          <Tab>
            <Tabs defaultActiveKey={selectTabKey} activeKey={selectTabKey} onChange={handleClickTab}>
              {tabs.map((item) => <TabPane key={item.key} tab={item.name} />)}
            </Tabs>
          </Tab>
        )
        : null}
    </Block>
  );
};

export default Header;
