import styled from "styled-components";

export const StyledHorizonTable = styled.div`
  overflow-x: scroll;
  overflow-y: hidden;
  white-space: nowrap;
  padding-left: 20px;
  padding-right: 50px;
  padding-top: 15px;
  &::-webkit-scrollbar {
    display: none;
    width: 0 !important;
  }

  .card {
    display: inline-block;
  }
`;
