import styled from 'styled-components';

export const Block = styled.div`
  background: white;
  padding-left: 32px;
  padding-top: 12px;
  box-sizing: border-box; 
  overflow: hidden;
  position: relative;
  z-index:98;
`;

export const Title = styled.h1`
  margin: 18px 0;
  height: 28px;
  font-size: 20px;
  font-weight: 500;
  color: rgba(0,0,0,0.85);
  line-height: 28px;
`;

export const Description = styled.p`
  font-size:14px;
  margin-bottom:15px;
`;

export const Tab = styled.div`
  height:46px;
`;
