import React, { Suspense, useEffect } from 'react';
import { Route, useHistory }          from 'react-router-dom';
import { QueryClientProvider }        from 'react-query';
import { useDispatch, useSelector }   from 'react-redux';
import { Col, ConfigProvider, Row }   from 'antd';
import zhCN                           from 'antd/lib/locale/zh_CN';
import 'moment/dist/locale/zh-cn';
import NavBar                         from 'components/NavBar';
import Preview                        from 'components/Preview';
import UserNavBar                     from 'components/UserNavBar';
import { queryClient }                from './store';
import './customAntd.less';

const Home             = React.lazy(() => import('./pages/Home'));
const UserPage         = React.lazy(() => import('./pages/User'));
const ArticlePage      = React.lazy(() => import('./pages/Article'));
const ConfiguationPage = React.lazy(() => import('./pages/Configuration'));
const About            = React.lazy(() => import('./pages/About'));
const Auth             = React.lazy(() => import('./pages/Auth'));

function App() {
  const history                    = useHistory();
  const { app: { configuration } } = useSelector((state: RootState) => state);
  const dispatch                   = useDispatch<Dispatch>().app;

  useEffect(() => {
    dispatch.setConfiguration({
      isBackgroundEnabled: !!localStorage.getItem('isBackgroundEnabled'),
    });

    if (!localStorage.getItem('token')) {
      history.replace('/auth/login');
    }
  }, []);

  // 特殊路径跳转
  if (window.location.pathname === '/') {
    history.replace('/home');
  }

  if (window.location.pathname.startsWith('/auth')) {
    return (
      <Suspense fallback={<Preview />}>
        <Auth />
      </Suspense>
    );
  }

  return (
    <QueryClientProvider client={queryClient}>
      <ConfigProvider locale={zhCN}>
        <div
          className={configuration.isBackgroundEnabled ? 'gradient-background' : undefined}
          style={{
            width: '100vw',
            height: '100vh',
          }}
        >
          <Row className="App">
            <Col className="g-nav">
              <NavBar />
            </Col>
            <Col className="g-main">
              <UserNavBar />
              {
                configuration ? (
                  <Suspense fallback={<Preview />}>
                    <Route path="/home" component={Home} />
                    <Route path="/users" component={UserPage} />
                    <Route path="/configuration" component={ConfiguationPage} />
                    <Route path="/articles" component={ArticlePage} />
                    <Route path="/about" component={About} />
                    <Route path="/auth" component={Auth} />
                  </Suspense>
                ) : (
                  <Preview />
                )
              }
            </Col>
          </Row>
        </div>
      </ConfigProvider>
    </QueryClientProvider>
  );
}

export default App;
