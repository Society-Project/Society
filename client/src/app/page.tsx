"use client";
import styled from "styled-components";
import { NavigationBar } from "@/components/NavigationBar/Navigation";
import { MainPageBox } from "@/components/page-box/PageBox";
import useWindowScreenSize from "@/useWindowScreenSize";
import ProtectedRoutes from "@/ProtectedRoutes/ProtectedRoutes";
import { useRouter } from "next/navigation";

import "../components/Styles/MainPage.scss";

const Home = () => {
  const [width, height] = useWindowScreenSize();
  const router = useRouter();

  const teamPageOnClick = () => {
    router.push('/team');
  }

  return <Main className={width > 1024 ? "main-page" : "main-mobile-view"}>
    <ProtectedRoutes>
      <NavigationBar />
    </ProtectedRoutes>
    <ProtectedRoutes>
      <MainPageBox />
    </ProtectedRoutes>
    <ProtectedRoutes>
      {
        width > 1024 ? <div style={{ 
          display: 'flex',
          justifyContent: 'end',
          cursor: 'pointer',
          height: '100vh',
          width: '10%',
          position: 'absolute',
          right: 0,
         }}>

        <svg style={{ 
            marginTop: 'auto',
            marginBottom: '2.5rem',
            marginLeft: 'auto',
            marginRight: 'auto',
         }} onClick={teamPageOnClick} width="55" height="55" viewBox="0 0 75 75" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M53.125 62.5C53.125 57.3223 46.1295 53.125 37.5 53.125C28.8705 53.125 21.875 57.3223 21.875 62.5M65.625 53.1262C65.625 49.2818 61.7685 45.9779 56.25 44.5312M9.375 53.1262C9.375 49.2818 13.2315 45.9779 18.75 44.5312M56.25 31.9878C58.168 30.2712 59.375 27.7766 59.375 25C59.375 19.8223 55.1777 15.625 50 15.625C47.5989 15.625 45.4086 16.5277 43.75 18.0122M18.75 31.9878C16.832 30.2712 15.625 27.7766 15.625 25C15.625 19.8223 19.8223 15.625 25 15.625C27.4011 15.625 29.5914 16.5277 31.25 18.0122M37.5 43.75C32.3223 43.75 28.125 39.5527 28.125 34.375C28.125 29.1973 32.3223 25 37.5 25C42.6777 25 46.875 29.1973 46.875 34.375C46.875 39.5527 42.6777 43.75 37.5 43.75Z" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
        </svg>

      </div> : null
      }
    </ProtectedRoutes>
  </Main>
};

export default Home;

const Main = styled.div`
  position: relative;
`;
