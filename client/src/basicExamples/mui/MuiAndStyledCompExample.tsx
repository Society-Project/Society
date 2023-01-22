"use client";
import Button from "@mui/material-next/Button";
import styled from "styled-components";

const MuiAndStyledCompExample = () => (
  <StyledButton variant="text">I am Mui + styled comps</StyledButton>
);

export default MuiAndStyledCompExample;

const StyledButton = styled(Button)`
  background-color: mediumaquamarine;
  color: white;
  font-size: 1rem;
`;
