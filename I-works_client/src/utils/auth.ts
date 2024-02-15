import { redirect } from 'react-router-dom'
import { jwtDecode } from "jwt-decode";

function getAccessToken () {
  const token = localStorage.getItem('accessToken')
  if (token) {
    return token
  }
  return null
}

function tokenLoader () {
  const token = getAccessToken()
  if (!token) {
    return redirect('/login')
  }
  return null
}

function getDecoded(): { role: string[] | null } | null {
  const token = getAccessToken();
  if (token != null) {
    const decoded = jwtDecode(token) as { role: string[] | null };
    return decoded;
  }
  return null;
}

export { getAccessToken, tokenLoader,getDecoded }