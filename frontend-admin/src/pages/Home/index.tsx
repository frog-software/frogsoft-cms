import React, { FC } from 'react';
import Block         from 'components/Block';

const Home: FC = () => {
  const getTraefikUrl = () => {
    const { origin } = window.location;
    const replacedUrl = origin.replace('admin', 'traefik-dashboard');

    return replacedUrl === origin ? '' : replacedUrl;
  };

  return (
    <>
      <Block
        title="Traefik 控制面板"
      >
        <iframe
          width="100%"
          height="800px"
          src={getTraefikUrl()}
          title="Traefik Dashboard"
        />
      </Block>
    </>
  );
};

export default Home;
