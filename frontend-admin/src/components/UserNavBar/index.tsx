import React                        from 'react';
import {
  Avatar, Col, Menu, Row,
}  from 'antd';
import { useDispatch, useSelector } from 'react-redux';

function UserNavBar() {
  const {
    configuration: config,
    userInfo,
  } = useSelector((state: RootState) => state.app);
  const dispatch                = useDispatch<Dispatch>().app;

  return (
    <Row
      justify="end"
      style={{
        backgroundColor: '#FFFFFFA0',
        boxShadow: '0px 1px 4px 0px rgba(0, 21, 41, 0.12)',
        marginBottom: 2,
        padding: '10px 12px',
        position: 'relative',
        zIndex: 98,
      }}
    >
      <Col>
        <Menu mode="horizontal" style={{ border: 'none', backgroundColor: '#FFFFFF00' }}>
          <Menu.SubMenu
            key="SubMenu"
            title={(
              <Row align="middle">
                <Col>
                  <Avatar
                    src={userInfo.avatar || '/logo.svg'}
                    style={{ marginRight: 8 }}
                  />
                </Col>
                <Col>
                  <div>
                    {userInfo.username}
                  </div>
                </Col>
              </Row>
            )}
            style={{ float: 'right', fontSize: 16 }}
          >
            <Menu.Item
              style={{ textAlign: 'center' }}
              onClick={() => {
                window.localStorage.removeItem('token');
                window.location.href = '/auth/login';
              }}
            >
              退出登录
            </Menu.Item>
            <Menu.Item
              style={{ textAlign: 'center' }}
              onClick={() => {
                localStorage.setItem('isBackgroundDisabled', !config.isBackgroundDisabled ? 'true' : '');
                dispatch.setConfiguration({
                  isBackgroundDisabled: !config.isBackgroundDisabled,
                });
              }}
            >
              {`${config.isBackgroundDisabled ? '打开动画' : '关闭动画'}`}
            </Menu.Item>
          </Menu.SubMenu>
        </Menu>
      </Col>
    </Row>
  );
}

export default UserNavBar;
