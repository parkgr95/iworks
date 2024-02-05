import axios from 'axios'
import { useEffect, useState } from 'react'

interface UserData {
  userEid: string,
  userNameFirst: string,
  userNameLast: string,
  departmentName: string,
  departmentId: string,
  positionCodeName: null,
  positionCodeId: null,
  userTel: string,
  userEmail: string
}

function AddressSelect() {
  const [users, setUsers] = useState<UserData[]>([])
  const [selectedUsers, setSelectedUsers] = useState<UserData[]>([])
  
  // 유저 목록 받아오기
  useEffect(() => {
    async function getUsers() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/user/all`)
        console.log(res.data.data)
        setUsers(res.data.data)
        
      } catch (err) {
        console.log(err)
      }
    }

    getUsers()
  }, [])


  const handleUserToggle = (user: UserData) => {
    // 사용자 선택/해제 로직
    const isSelected = selectedUsers.some((selectedUser) => selectedUser.userEid === user.userEid)

    if (isSelected) {
      setSelectedUsers(selectedUsers.filter((selectedUser) => selectedUser.userEid !== user.userEid))
    } else {
      setSelectedUsers([...selectedUsers, user])
    }
  }

  // 선택된 사용자 정보를 부모 창으로 전송
  const handleSendData = () => {
    window.opener.postMessage({ type: 'SEND_DATA', payload: { selectedUsers }}, window.location.href);
    // 보내기 전에 창을 닫으면 동작을 안하는 듯
    setTimeout(() => {
      window.close();
    }, 100);
  }

  return (
    <div className='overflow-auto h-full'>
      <h2>유저 선택</h2>
      {users.map((user) => (
        <div key={user.userEid}>
          <label>
            <input
              className='m-2'
              type="checkbox"
              checked={selectedUsers.some((selectedUser) => selectedUser.userEid === user.userEid)}
              onChange={() => handleUserToggle(user)}
            />
            {user.userNameLast}/{user.userNameFirst}
          </label>
        </div>
      ))}
      <button onClick={handleSendData}>보내기</button>
    </div>
  )
}


export default AddressSelect