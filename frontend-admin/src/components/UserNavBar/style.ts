import styled from 'styled-components';

export const Body = styled.div`
  position: relative;
  height: 64px;
  background: rgba(255, 255, 255, 0.6);
  display:flex;
  justify-content: space-between;
  box-sizing: border-box;
`;

export const Bar = styled.div`
  display: flex;
  align-items: center;
`;

export const Name = styled.p`
  margin-left:8px;
  margin-bottom: 0;
`;

export const AvatarBar = styled.div`
  margin-left:12px;
  padding-left:12px;
  padding-right:12px;
  height: 64px;
  display: flex;
  align-items: center;
  position: relative;
  cursor: pointer;

  :hover {
    background: #D4ECE5;
  }
`;

export const Panel = styled.div`
  width: 120px;
  top: 64px;
  right: 0px;
  background: white;
  position: absolute;
  box-shadow: 0px 9px 28px 8px rgba(0, 0, 0, 0.05), 0px 6px 16px 0px rgba(0, 0, 0, 0.08), 0px 3px 6px -4px rgba(0, 0, 0, 0.12);
  z-index: 999;
`;
