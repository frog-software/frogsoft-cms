import styled from 'styled-components';

export const Body = styled.div`
  margin: 24px;
  background: white;
  position: relative;
  z-index: 10;
  box-shadow: 0 0 5px 0px #eee;
`;/*  */

export const Header = styled.div`
  height: 56px;
  line-height:56px;
  display:flex;
  padding-left:30px;
  padding-right:20px;
  justify-content: space-between;
  border-bottom: 1px solid #e8e8e8;
`;

export const Title = styled.h2`
  font-size: 16px;
  font-weight:500;
`;

export const Main = styled.div`
  width: 100%;
  padding: 16px 32px;
  box-sizing: border-box;
  height: calc(100% - 56px);
  overflow: scroll;
`;

export const Loading = styled.div`
  position: absolute;
  z-index: 99;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  background: rgba(0,0,0,0.1);
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const Bottom = styled.div`
  width: 100%;
  height: 64px;
  background-color: #FFFFFF;
  box-shadow: 0px -1px 4px 0px rgba(0, 21, 41, 0.12);
  text-align: center;
  line-height: 64px;
  left: 0px;
  bottom: 0px;
`;
