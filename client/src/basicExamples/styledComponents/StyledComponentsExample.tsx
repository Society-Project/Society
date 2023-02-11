"use client";
import styled from "styled-components";

const StyledComponentsExample = () => (
    <Container>
        <Content>Hello World from styled-components!</Content>
    </Container>
);

export default StyledComponentsExample;

const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
`;

const Content = styled.p`
  color: aquamarine;
  font-size: 24px;
`;
