//+-------------------------------------------------------------------------
//
//  登录
//
//  File:       Login.tsx
//
//  Directory:  src/pages/Auth/Login
//
//  History:    Sep-08-2021   Charlie Chiang  Created
//
//--------------------------------------------------------------------------

import React, { FC, useEffect, useState } from 'react';
import {
  Button, Col, Form, Input, message, Row, Typography,
}                                         from 'antd';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import http                           from 'utils/http';
import Typical                        from 'react-typical';
import './index.less';

const Login: FC = () => {
  const [isBackgroundOff, setBackgroundOff] = useState<boolean>();
  const [isLoading, setLoading] = useState<boolean>(false);

  useEffect(() => {
    setBackgroundOff(!!localStorage.getItem('isBackgroundDisabled'));
  }, []);

  const handleLogin = (formData) => {
    console.log(formData);
    if (!formData.username || !formData.password) {
      message.error('请填写用户名与密码');
      return;
    }

    setLoading(true);
    http.post<{ username: string, token: string }>('/v1/auth/login', {
      username: formData.username,
      password: formData.password,
    })
      .then((res) => {
        localStorage.setItem('token', res.token);
        window.location.href = '/home';
      })
      .catch((err) => {
        message.error(err);
        setTimeout(() => {
          setLoading(false);
        }, 1000);
      });
  };

  return (
    <div className={`login-root ${!isBackgroundOff ? 'gradient-background' : 'login-background'}`}>
      <Row
        className="login-modal"
        align="middle"
        justify="center"
        gutter={[0, 12]}
      >
        <Col style={{ textAlign: 'center', marginTop: 32 }}>
          <img
            width={120}
            height={120}
            src="/logo.svg"
            alt="logo"
            onClick={() => {
              localStorage.setItem('isBackgroundDisabled', !isBackgroundOff ? 'true' : '');
              setBackgroundOff(!isBackgroundOff);
            }}
          />
          <Typography.Text
            style={{
              fontSize: 18, color: 'rgba(0,0,0,0.36)', display: 'block', marginTop: 16,
            }}
          >
            <Typical
              loop={Infinity}
              steps={[
                'Frogsoft CMS',
                2000,
                '蛙软内容管理系统',
                2000,
              ]}
            />
          </Typography.Text>
          <Typography.Title
            style={{ fontSize: 24, color: 'rgba(0,0,0,0.75)', marginBottom: 24 }}
          >
            管理员后台管理
          </Typography.Title>
        </Col>
        <Col style={{ width: 380 }}>
          <Form
            onFinish={handleLogin}
          >
            <Form.Item
              name="username"
              // 不使用 rules 是因为校验不通过时它会影响透明背景
            >
              <Input
                className="login-input"
                placeholder="用户名"
                size="large"
                prefix={<UserOutlined />}
              />
            </Form.Item>
            <Form.Item
              name="password"
            >
              <Input.Password
                className="login-input"
                placeholder="密码"
                size="large"
                prefix={<LockOutlined />}
                maxLength={18}
              />
            </Form.Item>
            <Form.Item wrapperCol={{ offset: 8, span: 8 }}>
              <Button
                className="login-button"
                type="primary"
                htmlType="submit"
                style={{ width: '100%', marginTop: 16 }}
                loading={isLoading}
              >
                登录
              </Button>
            </Form.Item>
            <Form.Item wrapperCol={{ span: 24 }}>
              <Typography.Text
                style={{
                  fontSize: 12, color: 'rgba(0,0,0,0.36)', display: 'block', textAlign: 'center',
                }}
              >
                {`Frogsoft ${(new Date()).getFullYear()}. All Rights Reserved.`}
              </Typography.Text>
            </Form.Item>
          </Form>
        </Col>
      </Row>
    </div>

  );
};

export default Login;
