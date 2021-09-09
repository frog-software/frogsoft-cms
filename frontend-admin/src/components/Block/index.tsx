import React, { CSSProperties, FC, ReactNode }         from 'react';
import { Spin }                                        from 'antd';
import { ArrowLeftOutlined }                           from '@ant-design/icons';
import {
  Body, Bottom, Header, Loading, Main, Title,
} from './style';

interface BlockProps {
  title?: ReactNode,
  description?: ReactNode,
  children?: ReactNode,
  loading?: boolean,
  showBack?: boolean,
  style?: CSSProperties,
  bodyStyle?: CSSProperties;
  stretch?: number;
  bottom?: ReactNode;
}

const Block: FC<BlockProps> = (props) => {
  const {
    title, description, children, loading, showBack, style, bodyStyle, stretch, bottom,
  } = props;

  return (
    <Body
      style={{
        ...bodyStyle,
        backgroundColor: '#FFFFFFA0',
        height: stretch ? `calc(100vh - ${stretch}px)` : 'auto',
        paddingBottom: stretch ? 64 : 0,
      }}
    >
      {
        title || description
          ? (
            <Header>
              <Title>
                {
                  showBack
                  && (
                    <ArrowLeftOutlined
                      style={{ marginRight: 10 }}
                      onClick={() => window.history.back()}
                    />
                  )
                }
                {title || <div />}
              </Title>
              <div>
                {description || <div />}
              </div>
            </Header>
          )
          : null
      }
      <Main style={style}>{children}</Main>
      {loading ? <Loading><Spin size="large" /></Loading> : null}
      {bottom ? <Bottom style={{ position: stretch ? 'absolute' : 'static' }}>{bottom}</Bottom> : null}
    </Body>
  );
};
export default Block;
