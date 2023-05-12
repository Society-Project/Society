import Cookie from 'universal-cookie';
import { ValidateUser } from '../pages/settings';

const ProtectedRoutes = ({ children }: any) => {
  const cookies: Cookie = new Cookie();
  const userCookie: string | null = cookies.get('accessToken')

  if(userCookie === undefined){
    return ValidateUser();
  }

  return children;
}

export default ProtectedRoutes