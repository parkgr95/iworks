import { redirect } from 'react-router-dom';

function logoutAction() {
  localStorage.removeItem('accessToken');
  localStorage.removeItem('refreshToken');
  return redirect('/user/login');
}

export { logoutAction }