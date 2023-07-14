import React from "react";
import styled from "styled-components";
import Header from "../components/Header";
import { NavigationBar } from "@/components/NavigationBar/Navigation";

import { Box } from "@mui/material";
import "../../src/components/Styles/FriendsStyles.scss";
import { FriendsList } from "@/components/friends/FriendsList";
import ProtectedRoutes from "@/ProtectedRoutes/ProtectedRoutes";

const friends = () => {
  return <ProtectedRoutes>
    <Main className="main-page">
      <NavigationBar />
      <FriendsList />
    </Main>
  </ProtectedRoutes>
};

export default friends;

const Wrapper = styled.div`
  position: relative;
`;
const Main = styled.div`
  position: relative;
`;
