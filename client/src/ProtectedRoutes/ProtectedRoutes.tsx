const ProtectedRoutes = ({ children }: any) => {
  const doesUserHaveCookie: string | null = localStorage.getItem('userCookie');
  
  if(doesUserHaveCookie === null){
    return window.location.href = '/login'
  }

  return children
}

export default ProtectedRoutes