import { redirect } from 'react-router-dom'
import { jwtDecode } from "jwt-decode";
import axios from "axios";

function getAccessToken () {
  const token = localStorage.getItem('accessToken')
  if (token) {
    return token
  }
  return null
}

function tokenLoader () {
  const token = getAccessToken()

  if (!token && window.location.pathname !== "/user/login") {           
    return redirect('/user/login')
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



function getNewAccessToken() {
  const refresh = localStorage.getItem('refreshToken');
  axios
      .get('https://suhyeon.site/api/token', {
        headers: {
          'Authorization': 'Bearer '+refresh,
        }
      })
      .then((res) => {
        console.log(res.data.accessToken)
        localStorage.setItem('accessToken',res.data.accessToken)
      })
    .catch((err) => {
        alert(err.response.data.message)
        console.log(err)
      })
}

export { getAccessToken, tokenLoader,getDecoded,getNewAccessToken }