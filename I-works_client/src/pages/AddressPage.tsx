import AddressSideBar from './addresses/AddressSideBar'
import { useEffect } from 'react'
import { getAccessToken } from '../utils/auth'
import { useNavigate } from 'react-router-dom'

function AddressPage() {
  const navigate = useNavigate()
  useEffect(() => {
    const token = getAccessToken()
    if (token == null) {
      navigate('/user/login')
    }
  }, [])
  return (
    <>
      <AddressSideBar />
    </>
  )
}

export default AddressPage
