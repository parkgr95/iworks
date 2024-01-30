import { redirect } from 'react-router-dom'

function getAccessToken () {
  const token = localStorage.getItem('authToken')
  // const token = localStorage.getItem('accessToken')
  if (token) {
    return token
  }
  return redirect('/')
}

function tokenLoader () {
  const token = getAccessToken()
  if (!token) {
    return redirect('/login')
  }
  return null
}


export { getAccessToken, tokenLoader }