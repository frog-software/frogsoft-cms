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

import React, { FC, useEffect, useState }                                                         from 'react';
import Block
  from 'components/Block';
import {
  Button, Col, Descriptions, Form, Image, Input, Layout, notification, Row, Space, Tabs,
} from 'antd';
import {
  AndroidOutlined,
  AppleOutlined,
  DesktopOutlined,
  GithubOutlined,
  MailOutlined,
  SkinOutlined,
  WindowsOutlined,
}                                                                                                 from '@ant-design/icons';
import http                                                                                       from 'utils/http';
import { ConfigurationType }                                                                      from 'types/configuration';
import { useForm }                                                                                from 'antd/es/form/Form';

const { TabPane } = Tabs;

const Configuration: FC = () => {
  const [configuration, setConfiguration] = useState<ConfigurationType>();
  const [editable, setEditable]           = useState<boolean>(false);
  const [form]                            = useForm();
  const [render, setRender]               = useState<boolean>(false);
  const [loading, setLoading]             = useState<boolean>(false);
  const { TextArea }                        = Input;
  const {
    Header, Footer, Sider, Content,
  }                                 = Layout;

  useEffect(() => {
    (async () => {
      const data = await http.get<ConfigurationType>('http://127.0.0.1:4523/mock/419258/v1/global/config');

      setConfiguration(data);
    })();
  }, [render]);

  const testImg: string = 'http://pic.soutu123.cn/element_origin_min_pic/16/08/31/1457c67986055d6.jpg';

  // 输入内容规范
  const validateMessages = {
    // eslint-disable-next-line no-template-curly-in-string
    required: '${label}不能为空',
  };

  // 编辑个性配置
  const handleWebEdit = (data) => {
    setLoading(true);

    const tempData = {
      favicon: data?.favicon || configuration?.favicon,
      title: data?.title || configuration?.title,
      logo: data?.logo || configuration?.logo,
      email: {
        password: data?.password || configuration?.email?.password,
        account: data?.account || configuration?.email?.account,
        body: data?.body || configuration?.email?.body,
        title: data?.emailTitle || configuration?.email?.title,
        host: data?.host || configuration?.email?.host,
        port: data?.port || configuration?.email?.port,
      },
      header: {
        logo: data?.headerLogo || configuration?.header?.logo,
      },
      footer: {
        logo: data?.footerLogo || configuration?.footer?.logo,
      },
    };

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
  };

  return (
    <>
      <Block
        title="个性配置"
        bodyStyle={{ minHeight: '830px' }}
        loading={loading}
        description={(
          <Space>
            <Button onClick={editable ? () => form.submit() : () => setEditable(true)}>
              {editable ? '保存' : '编辑'}
            </Button>
            {editable ? (
              <Button onClick={() => setEditable(false)}>取消</Button>
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
                        {configuration?.title}
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
                              src={testImg}
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
                              src={testImg}
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
                              src={testImg}
                            />
                            <p>32*32</p>
                          </Space>
                        </Row>
                      </Descriptions.Item>
                    </Descriptions>
                    <Descriptions style={{ marginBottom: '20px' }}>
                      <Descriptions.Item label="站点图标">
                        {configuration?.favicon}
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
                              src={testImg}
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
                              src={testImg}
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
                              src={testImg}
                            />
                            <p>32*32</p>
                          </Space>
                        </Row>
                      </Descriptions.Item>
                    </Descriptions>
                    <Descriptions>
                      <Descriptions.Item label="LOGO图标">
                        {configuration?.logo}
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
                      initialValue={configuration?.title}
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
                      initialValue={configuration?.favicon}
                      style={{ marginTop: '20px' }}
                      rules={[{ required: true }]}
                    >
                      <Input />
                    </Form.Item>
                    <Row align="bottom">
                      <Space style={{ marginRight: '40px' }} direction="vertical" align="center">
                        <Image height={184} width={184} src={configuration?.logo} />
                        <p>184*184</p>
                      </Space>
                      <Space style={{ marginRight: '40px' }} direction="vertical" align="center">
                        <Image height={64} width={64} preview={false} src={configuration?.logo} />
                        <p>64*64</p>
                      </Space>
                      <Space direction="vertical" align="center">
                        <Image height={32} width={32} preview={false} src={configuration?.logo} />
                        <p>32*32</p>
                      </Space>
                    </Row>
                    <Form.Item
                      name="logo"
                      label="LOGO图标"
                      initialValue={configuration?.logo}
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
                  <Descriptions bordered column={1} title="验证邮箱设置">
                    <Descriptions.Item label="邮箱账号">{configuration?.email?.account}</Descriptions.Item>
                    <Descriptions.Item label="邮箱密码">{configuration?.email?.password}</Descriptions.Item>
                    <Descriptions.Item label="邮件服务器主机">{configuration?.email?.host}</Descriptions.Item>
                    <Descriptions.Item label="邮件服务器端口">{configuration?.email?.port}</Descriptions.Item>
                    <Descriptions.Item label="邮件标题">{configuration?.email?.title}</Descriptions.Item>
                    <Descriptions.Item label="邮件内容">{configuration?.email?.body}</Descriptions.Item>
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
                        initialValue={configuration?.email?.account}
                        rules={[{ required: true }]}
                      >
                        <Input />
                      </Form.Item>
                      <Form.Item
                        name="password"
                        label="邮箱密码"
                        initialValue={configuration?.email?.password}
                        rules={[{ required: true }]}
                      >
                        <Input />
                      </Form.Item>
                      <Form.Item
                        name="host"
                        label="邮件服务器主机"
                        initialValue={configuration?.email?.host}
                        rules={[{ required: true }]}
                      >
                        <Input />
                      </Form.Item>
                      <Form.Item
                        name="port"
                        label="邮件服务器端口"
                        initialValue={configuration?.email?.port}
                        rules={[{ required: true }]}
                      >
                        <Input />
                      </Form.Item>
                      <Form.Item
                        name="emailTitle"
                        label="邮件标题"
                        initialValue={configuration?.email?.title}
                        rules={[{ required: true }]}
                      >
                        <Input />
                      </Form.Item>
                    </Col>
                    <Col span={20}>
                      <Form.Item
                        name="body"
                        label="邮件内容"
                        initialValue={configuration?.email?.body}
                        rules={[{ required: true }]}
                      >
                        <TextArea autoSize={{ minRows: 3 }} />
                      </Form.Item>
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
                          {configuration?.header?.logo}
                        </Descriptions.Item>
                        <Descriptions.Item label="页脚LOGO">
                          {configuration?.footer?.logo}
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
                        initialValue={configuration?.header?.logo}
                        rules={[{ required: true }]}
                      >
                        <Input />
                      </Form.Item>

                      <Form.Item
                        name="footerLogo"
                        label="页脚LOGO"
                        initialValue={configuration?.footer?.logo}
                        rules={[{ required: true }]}
                      >
                        <Input />
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
                          src={configuration?.header?.logo}
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
                          marginTop: '22px',
                        }}
                      >
                        {configuration?.email.account}
                      </Col>
                    </Row>
                  </Header>
                  <Content style={{ backgroundColor: '#f0f0f0', height: '280px' }}>
                    <Col span={23}>
                      <Content
                        style={{
                          backgroundColor: '#fafafa',
                          height: '240px',
                          marginLeft: '30px',
                          marginTop: '18px',
                          fontStyle: 'oblique',
                          fontWeight: 'bolder',
                          fontSize: 'xx-large',
                        }}
                      >
                        <Descriptions title={configuration?.email?.body} />
                      </Content>
                    </Col>
                  </Content>
                  <Footer style={{ backgroundColor: '#262626', height: '140px' }}>
                    <Row>
                      <Col offset={6} span={5}>
                        <Image
                          height={50}
                          width={100}
                          src={configuration?.footer?.logo}
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
