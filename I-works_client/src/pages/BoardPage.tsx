import BoardSideBar from './boards/BoardSideBar'
import { useEffect } from 'react'
import { getAccessToken } from '../utils/auth'
import { useNavigate } from 'react-router-dom'

function BoardPage() {
  const navigate = useNavigate()
  useEffect(() => {
    const token = getAccessToken()
    if (token == null) {
      navigate('/user/login')
    }
  }, [])

  return (
    <>
      <BoardSideBar />
    </>
  )
}

export default BoardPage
