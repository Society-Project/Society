import { useEffect } from 'react';
import SettingsPage from "@/components/Settings/SettingsPage";
import Cookie from 'universal-cookie';

export const ValidateUser = () => {
  const cookies: Cookie = new Cookie();
  const getUserCookie: string | null = cookies.get('accessToken');

  if(getUserCookie === undefined) {
    return window.location.href = '/login';
  }
}

const settings = () => {
  useEffect(() => {
    ValidateUser();
  }, []);

  return <SettingsPage />
}

export default settings