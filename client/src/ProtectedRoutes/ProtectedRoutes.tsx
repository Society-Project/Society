import Cookie from 'universal-cookie';

const ProtectedRoutes = ({ children }: any) => {
  const cookies: Cookie = new Cookie();
  const userCookie: string | null = cookies.get('accessToken');

  if(userCookie === undefined){
    return window.location.href = '/login';
  }

  return children;
}

export default ProtectedRoutes