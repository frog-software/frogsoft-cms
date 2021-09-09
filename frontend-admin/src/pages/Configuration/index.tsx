//+-------------------------------------------------------------------------
//
//  个性配置
//
//  File:       index.tsx
//
//  Directory:  src/pages/Configuration
//
//  History:    9月-06-2021   QQK  Created
//
//--------------------------------------------------------------------------

import React, { FC, useEffect, useState } from 'react';
import Block
  from 'components/Block';
import {
  Button, Col, Descriptions, Form, Image, Input, Layout, notification, Row, Space, Tabs, Tooltip,
}                                         from 'antd';
import {
  AndroidOutlined,
  AppleOutlined,
  DesktopOutlined,
  GithubOutlined,
  MailOutlined,
  SkinOutlined,
  WindowsOutlined,
}                                         from '@ant-design/icons';
import http                               from 'utils/http';
import { ConfigurationType }              from 'types/configuration';
import { useForm }                        from 'antd/es/form/Form';

const { TabPane } = Tabs;

const Configuration: FC = () => {
  const [configuration, setConfiguration] = useState<ConfigurationType>();
  const [editable, setEditable]           = useState<boolean>(false);
  const [form]                            = useForm();
  const [render, setRender]               = useState<boolean>(false);
  const [loading, setLoading]             = useState<boolean>(false);
  const { TextArea }                        = Input;
  const { Header, Footer, Content }         = Layout;

  useEffect(() => {
    setLoading(true);

    http.get<ConfigurationType>('v1/global/config')
      .then((data) => {
        setConfiguration(data);
      })
      .catch((error) => {
        notification['error']({ message: '获取个性配置失败', description: String(error) });
      })
      .finally(() => {
        setLoading(false);
      });
  }, [render]);

  // 输入内容规范
  const validateMessages = {
    // eslint-disable-next-line no-template-curly-in-string
    required: '${label}不能为空',
  };

  // 编辑个性配置
  const handleWebEdit = (data) => {
    setLoading(true);

    const tempData = {
      favicon: data?.favicon,
      title: data?.title,
      logo: data?.logo,
      emailDto: {
        password: data?.password,
        account: data?.account,
        body: data?.body,
        title: data?.emailTitle,
        host: data?.host,
        port: data?.port,
      },
      headerDto: {
        logo: data?.headerLogo,
      },
      footerDto: {
        logo: data?.footerLogo,
      },
    };

    // eslint-disable-next-line no-template-curly-in-string
    if (tempData.emailDto.body.includes('[[${verifyCode}]]')) {
      http.put('/v1/global/config', tempData)
        .then(() => {
          notification['success']({ message: '站点属性编辑成功' });
          setRender(!render);
          setEditable(false);
        })
        .catch(() => {
          notification['error']({ message: '站点属性编辑失败' });
        })
        .finally(() => {
          setLoading(false);
        });
    } else {
      notification['error']({
        message: '邮件内容编辑失败',
        // eslint-disable-next-line no-template-curly-in-string
        description: '请检查邮箱内容中是否正确包括了字符串[[${verifyCode}]]',
      });
      setLoading(false);
    }
  };

  return (
    <>
      <Block
        title="个性配置"
        bodyStyle={{ minHeight: '830px' }}
        loading={loading}
        description={(
          <Space>
            <Button
              type={editable ? 'primary' : 'ghost'}
              onClick={editable ? () => form.submit() : () => setEditable(true)}
            >
              {editable ? '保存' : '编辑'}
            </Button>
            {editable ? (
              <Button type="ghost" onClick={() => setEditable(false)}>取消</Button>
            ) : ''}
          </Space>
        )}
      >
        <Tabs
          tabPosition="left"
          style={{ marginTop: '20px' }}
        >
          <TabPane
            tab={(
              <span>
                <DesktopOutlined />
                站点属性设置
              </span>
            )}
            key="1"
          >
            <Descriptions title="站点属性设置" />
            <Row style={{ marginBottom: '40px' }} align="middle">
              {
                !editable ? (
                  <>
                    <Descriptions style={{ marginBottom: '20px' }}>
                      <Descriptions.Item label="站点名称">
                        {configuration?.title || '暂未设置'}
                      </Descriptions.Item>
                    </Descriptions>
                    <Descriptions>
                      <Descriptions.Item>
                        <Row align="bottom">
                          <Space
                            style={{ marginRight: '40px' }}
                            direction="vertical"
                            align="center"
                          >
                            <Image
                              height={184}
                              width={184}
                              src={configuration?.favicon}
                            />
                            <p>184*184</p>
                          </Space>
                          <Space
                            style={{ marginRight: '40px' }}
                            direction="vertical"
                            align="center"
                          >
                            <Image
                              height={64}
                              width={64}
                              preview={false}
                              src={configuration?.favicon}
                            />
                            <p>64*64</p>
                          </Space>
                          <Space
                            direction="vertical"
                            align="center"
                          >
                            <Image
                              height={32}
                              width={32}
                              preview={false}
                              src={configuration?.favicon}
                            />
                            <p>32*32</p>
                          </Space>
                        </Row>
                      </Descriptions.Item>
                    </Descriptions>
                    <Descriptions style={{ marginBottom: '20px' }}>
                      <Descriptions.Item label="站点图标">
                        {configuration?.favicon || '默认图标'}
                      </Descriptions.Item>
                    </Descriptions>
                    <Descriptions>
                      <Descriptions.Item>
                        <Row align="bottom">
                          <Space
                            style={{ marginRight: '40px' }}
                            direction="vertical"
                            align="center"
                          >
                            <Image
                              height={184}
                              width={184}
                              src={configuration?.favicon || '/logo.svg'}
                            />
                            <p>184*184</p>
                          </Space>
                          <Space
                            style={{ marginRight: '40px' }}
                            direction="vertical"
                            align="center"
                          >
                            <Image
                              height={64}
                              width={64}
                              preview={false}
                              src={configuration?.favicon || '/logo.svg'}
                            />
                            <p>64*64</p>
                          </Space>
                          <Space
                            direction="vertical"
                            align="center"
                          >
                            <Image
                              height={32}
                              width={32}
                              preview={false}
                              src={configuration?.favicon || '/logo.svg'}
                            />
                            <p>32*32</p>
                          </Space>
                        </Row>
                      </Descriptions.Item>
                    </Descriptions>
                    <Descriptions>
                      <Descriptions.Item label="LOGO图标">
                        {configuration?.logo || '默认图标'}
                      </Descriptions.Item>
                    </Descriptions>
                  </>
                ) : (
                  <Form
                    form={form}
                    name="newWebInfo"
                    onFinish={handleWebEdit}
                    onFinishFailed={() => notification['error']({ message: '站点属性编辑失败' })}
                    validateMessages={validateMessages}
                  >
                    <Form.Item
                      name="title"
                      label="站点名称"
                      initialValue={configuration?.title || '暂未设置'}
                      rules={[{ required: true }]}
                    >
                      <Input />
                    </Form.Item>
                    <Row align="bottom">
                      <Space
                        style={{ marginRight: '40px' }}
                        direction="vertical"
                        align="center"
                      >
                        <Image
                          height={184}
                          width={184}
                          src={configuration?.favicon}
                        />
                        <p>184*184</p>
                      </Space>
                      <Space
                        style={{ marginRight: '40px' }}
                        direction="vertical"
                        align="center"
                      >
                        <Image
                          height={64}
                          width={64}
                          preview={false}
                          src={configuration?.favicon}
                        />
                        <p>64*64</p>
                      </Space>
                      <Space
                        direction="vertical"
                        align="center"
                      >
                        <Image
                          height={32}
                          width={32}
                          preview={false}
                          src={configuration?.favicon}
                        />
                        <p>32*32</p>
                      </Space>
                    </Row>
                    <Form.Item
                      name="favicon"
                      label="站点图标"
                      initialValue={configuration?.favicon || '默认图标'}
                      style={{ marginTop: '20px' }}
                      rules={[{ required: true }]}
                    >
                      <Input />
                    </Form.Item>
                    <Row align="bottom">
                      <Space style={{ marginRight: '40px' }} direction="vertical" align="center">
                        <Image height={184} width={184} src={configuration?.logo || '/logo.svg'} />
                        <p>184*184</p>
                      </Space>
                      <Space style={{ marginRight: '40px' }} direction="vertical" align="center">
                        <Image height={64} width={64} preview={false} src={configuration?.logo || '/logo.svg'} />
                        <p>64*64</p>
                      </Space>
                      <Space direction="vertical" align="center">
                        <Image height={32} width={32} preview={false} src={configuration?.logo || '/logo.svg'} />
                        <p>32*32</p>
                      </Space>
                    </Row>
                    <Form.Item
                      name="logo"
                      label="LOGO图标"
                      initialValue={configuration?.logo || '默认图标'}
                      style={{ marginTop: '20px' }}
                      rules={[{ required: true }]}
                    >
                      <Input />
                    </Form.Item>
                  </Form>
                )
              }
            </Row>
          </TabPane>
          <TabPane
            tab={(
              <span>
                <MailOutlined />
                验证邮箱设置
              </span>
            )}
            key="2"
          >
            {
              !editable ? (
                <Col span={12}>
                  <Descriptions column={1} title="验证邮箱设置">
                    <Descriptions.Item label="邮箱账号">{configuration?.emailDto?.account || '暂未设置'}</Descriptions.Item>
                    <Descriptions.Item label="邮箱密码">{configuration?.emailDto?.password || '暂未设置'}</Descriptions.Item>
                    <Descriptions.Item label="邮件服务器主机">{configuration?.emailDto?.host || '暂未设置'}</Descriptions.Item>
                    <Descriptions.Item label="邮件服务器端口">{configuration?.emailDto?.port || '暂未设置'}</Descriptions.Item>
                    <Descriptions.Item label="邮件标题">{configuration?.emailDto?.title || '暂未设置'}</Descriptions.Item>
                    <Descriptions.Item label="邮件内容">{configuration?.emailDto?.body || '暂未设置'}</Descriptions.Item>
                  </Descriptions>
                </Col>
              ) : (
                <>
                  <Descriptions title="验证邮箱设置" />
                  <Form
                    form={form}
                    name="email"
                    onFinish={handleWebEdit}
                    onFinishFailed={() => notification['error']({ message: '站点属性编辑失败' })}
                    validateMessages={validateMessages}
                  >
                    <Col span={9}>
                      <Form.Item
                        name="account"
                        label="邮箱账号"
                        initialValue={configuration?.emailDto?.account}
                        rules={[{ required: true }]}
                      >
                        <Input placeholder="请输入邮箱账号" />
                      </Form.Item>
                      <Form.Item
                        name="password"
                        label="邮箱密码"
                        initialValue={configuration?.emailDto?.password}
                        rules={[{ required: true }]}
                      >
                        <Input placeholder="请输入邮箱密码" />
                      </Form.Item>
                      <Form.Item
                        name="host"
                        label="邮件服务器主机"
                        initialValue={configuration?.emailDto?.host}
                        rules={[{ required: true }]}
                      >
                        <Input placeholder="请输入邮件服务器主机" />
                      </Form.Item>
                      <Form.Item
                        name="port"
                        label="邮件服务器端口"
                        initialValue={configuration?.emailDto?.port}
                        rules={[{ required: true }]}
                      >
                        <Input placeholder="请输入邮件服务器端口" />
                      </Form.Item>
                      <Form.Item
                        name="emailTitle"
                        label="邮件标题"
                        initialValue={configuration?.emailDto?.title}
                        rules={[{ required: true }]}
                      >
                        <Input placeholder="请输入邮件标题" />
                      </Form.Item>
                    </Col>
                    <Col span={20}>
                      <Tooltip
                        placement="bottom"
                        title="邮件内容中字符串[[${verifyCode}]]表示验证码出现的位置，请务必在合适的位置填写"
                      >
                        <Form.Item
                          name="body"
                          label="邮件内容"
                          initialValue={configuration?.emailDto?.body}
                          rules={[{ required: true }]}
                        >
                          <TextArea autoSize={{ minRows: 3 }} placeholder="请输入邮件内容" />
                        </Form.Item>
                      </Tooltip>
                    </Col>
                  </Form>
                </>
              )
            }

          </TabPane>
          <TabPane
            tab={(
              <span>
                <SkinOutlined />
                页面属性设置
              </span>
            )}
            key="3"
          >
            <Descriptions title="页面属性设置" />
            <Row>
              <Col span={10}>
                {
                  !editable ? (
                    <>
                      <Descriptions style={{ marginTop: '20px', marginBottom: '20px' }} column={1}>
                        <Descriptions.Item label="页头LOGO">
                          {configuration?.headerDto?.logo || '默认图标'}
                        </Descriptions.Item>
                        <Descriptions.Item label="页脚LOGO">
                          {configuration?.footerDto?.logo || '默认图标'}
                        </Descriptions.Item>
                      </Descriptions>
                    </>
                  ) : (
                    <Form
                      form={form}
                      name="pageSet"
                      onFinish={handleWebEdit}
                      onFinishFailed={() => notification['error']({ message: '站点属性编辑失败' })}
                      validateMessages={validateMessages}
                      style={{ marginTop: '4px', marginBottom: '4px' }}
                    >
                      <Form.Item
                        name="headerLogo"
                        label="页头LOGO"
                        initialValue={configuration?.headerDto?.logo}
                        rules={[{ required: true }]}
                      >
                        <Input placeholder="请输入页头LOGO" />
                      </Form.Item>

                      <Form.Item
                        name="footerLogo"
                        label="页脚LOGO"
                        initialValue={configuration?.footerDto?.logo}
                        rules={[{ required: true }]}
                      >
                        <Input placeholder="请输入页脚LOGO" />
                      </Form.Item>
                    </Form>
                  )
                }
              </Col>
            </Row>
            <Row>
              <Descriptions title="页面预览" />
              <Col span={18}>
                <Layout>
                  <Header style={{ backgroundColor: '#bfbfbf', height: '120px' }}>
                    <Row justify="center">
                      <Col offset={2}>
                        <Image
                          height={80}
                          width={100}
                          src={configuration?.headerDto?.logo || 'https://dummyimage.com/100x80'}
                          style={{ marginTop: '20px' }}
                          preview={false}
                        />
                      </Col>
                      <Col
                        span={13}
                        offset={2}
                        style={{
                          color: '#434343',
                          fontStyle: 'oblique',
                          fontWeight: 'bolder',
                          fontSize: 'xx-large',
                          marginTop: '24px',
                        }}
                      >
                        {configuration?.emailDto?.account || 'Test Page Title'}
                      </Col>
                    </Row>
                  </Header>
                  <Content style={{ backgroundColor: '#f0f0f0', height: '280px' }}>
                    <Col span={23}>
                      <Content
                        style={{
                          backgroundColor: '#fafafa',
                          height: '240px',
                          marginLeft: '36px',
                          marginTop: '18px',
                          fontStyle: 'oblique',
                          fontWeight: 'bolder',
                          fontSize: 'xx-large',
                        }}
                      >
                        <Descriptions title={configuration?.emailDto?.body || 'Test Page Content'} />
                      </Content>
                    </Col>
                  </Content>
                  <Footer style={{ backgroundColor: '#262626', height: '140px' }}>
                    <Row>
                      <Col offset={6} span={5}>
                        <Image
                          height={50}
                          width={100}
                          src={configuration?.footerDto?.logo || 'https://dummyimage.com/100x50'}
                          style={{ marginTop: '20px' }}
                          preview={false}
                        />
                      </Col>
                      <Col style={{ color: '#f5f5f5', marginTop: '24px' }}>
                        <Row justify="center">
                          <Col span={5}>
                            <GithubOutlined />
                          </Col>
                          <Col span={5}>
                            <AndroidOutlined />
                          </Col>
                          <Col span={5}>
                            <AppleOutlined />
                          </Col>
                          <Col span={5}>
                            <WindowsOutlined />
                          </Col>
                        </Row>
                        <Row>© 2021 Hello Frogsoft!</Row>
                      </Col>
                    </Row>
                  </Footer>
                </Layout>
              </Col>
            </Row>
          </TabPane>
        </Tabs>
      </Block>
    </>
  );
};

export default Configuration;
