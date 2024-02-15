import { redirect } from 'react-router-dom'

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


export { getAccessToken, tokenLoader }