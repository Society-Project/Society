import Cookie from 'universal-cookie';
import { useEffect } from 'react';

const ValidateUser = () => {
  const cookies: Cookie = new Cookie();
  const getUserCookie: string | null = cookies.get('accessToken');

  if(getUserCookie === undefined) {
    return window.location.href = '/login';
  }
}
const ProtectedRoutes = ({ children }: any) => {
  const cookies: Cookie = new Cookie();
  const userCookie: string | null = cookies.get('accessToken')

  useEffect((): any => {
    if(userCookie === undefined){
      return ValidateUser();
    }
  
  }, [])
  return children;
}

export default ProtectedRoutes