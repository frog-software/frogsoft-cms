import React, { Suspense, useEffect } from 'react';
import { Route, useHistory } from 'react-router-dom';
import { QueryClientProvider } from 'react-query';
import { useDispatch, useSelector }         from 'react-redux';
import {
  Col, ConfigProvider, Layout, Row,
} from 'antd';
import zhCN                                 from 'antd/lib/locale/zh_CN';
import 'moment/dist/locale/zh-cn';
import NavBar from 'components/NavBar';
import Preview from 'components/Preview';
import UserNavBar from 'components/UserNavBar';
import { queryClient } from './store';
import './customAntd.less';

const Home = React.lazy(() => import('./pages/Home'));
const UserManagement = React.lazy(() => import('./pages/User/UserManagement'));

function App() {
  const history = useHistory();
  const { app: { configure } } = useSelector((state: RootState) => state);
  const dispatch = useDispatch<Dispatch>().app;

  useEffect(() => {
    dispatch.setConfigure({});
  }, []);

  // 特殊路径跳转
  if (window.location.pathname === '/') {
    history.replace('/home');
  }

  return (
    <QueryClientProvider client={queryClient}>
      <ConfigProvider locale={zhCN}>
        <Row className="App">
          <Col className="g-nav">
            <NavBar />
          </Col>
          <Col className="g-main">
            <UserNavBar />
            {
              configure ? (
                <Suspense fallback={<Preview />}>
                  <Route path="/home" component={Home} />
                  <Route path="/user" component={UserManagement} />
                </Suspense>
              ) : (
                <Preview />
              )
            }
          </Col>
        </Row>
      </ConfigProvider>
    </QueryClientProvider>
  );
}

export default App;
