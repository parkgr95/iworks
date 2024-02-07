import axios from 'axios'
import { Button } from 'flowbite-react'
import { useEffect, useState } from 'react'

interface orginizationType {
  departmentName?: string,
  departmentId?: string,
  teamName?: string,
  teamId?: string,
}

interface UserData {
  userId: string,
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
  const [departmentList, setDepartmentList] = useState<orginizationType[]>([])
  const [selectedDepartment, setSelectedDepartment] = useState<string | null>(null);
  const [filteredUsers, setFilteredUsers] = useState<UserData[]>([])

  useEffect(() => {
    // 부서 목록 받아오기
    async function getDepartments() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/department/all`)
        setDepartmentList(res.data.data)

      } catch (err) {
        console.log(err)
      }
    }
    // 유저 목록 받아오기
    async function getUsers() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/user/all`)       
        setUsers(res.data.data)
      } catch (err) {
        console.log(err)
      }
    }
    getDepartments()
    getUsers()
  }, [])

  useEffect(() => {
      const filteredUsers = selectedDepartment ? users.filter((user) => user.departmentId === selectedDepartment) : users;
      setFilteredUsers(filteredUsers);
  }, [selectedDepartment, users]);

  const handleUserToggle = (user: UserData) => {
    // 사용자 선택/해제 로직
    const isSelected = selectedUsers.some((selectedUser) => selectedUser.userEid === user.userEid)

    if (isSelected) {
      setSelectedUsers(selectedUsers.filter((selectedUser) => selectedUser.userEid !== user.userEid))
    } else {
      setSelectedUsers([...selectedUsers, user])
    }
  }

  const handleDepartmentSelect = (departmentId: string) => {
    setSelectedDepartment(departmentId);
  };

  // 선택된 사용자 정보를 부모 창으로 전송
  const handleSendData = () => {
    window.opener.postMessage({ type: 'SEND_DATA', payload: { selectedUsers } }, window.location.href);
    // 보내기 전에 창을 닫으면 동작을 안하는 듯
    setTimeout(() => {
      window.close();
    }, 100);
  }

  return (
    <div className='h-full pb-10'>
      <h2 className='font-bold text-xl border-b-2 p-5'>주소록</h2>
      <div className='flex h-3/4 border-b-2'>
        <div className='w-60 border-r-2 p-2'>
          <span className="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap font-semibold">조직도</span>
          <ul>
            <li>
              <ul>
                {departmentList.map((dept) => (
                  <li key={dept.departmentId}>
                    <span 
                      className={`flex items-center w-full text-mainBlack pl-16 pt-2 text-sm ${
                        selectedDepartment === dept.departmentId ? 'text-mainBlue' : ''
                      }`}
                      onClick={() => handleDepartmentSelect(dept.departmentId || '')}
                      >
                      {dept.departmentName}
                    </span>
                  </li>
                ))}
              </ul>
            </li>
          </ul>
        </div>
        <div className='overflow-auto h-full w-full p-2'>
          {filteredUsers.map((user) => (
            <div key={user.userEid}>
              <label className='flex items-center mb-2 border-b-2'>
                <input
                  className='mr-4'
                  type="checkbox"
                  checked={selectedUsers.some((selectedUser) => selectedUser.userEid === user.userEid)}
                  onChange={() => handleUserToggle(user)}
                />
                <div>
                  <div className='font-semibold'>{user.userNameLast}{user.userNameFirst}</div>
                  <div className='font-thin text-sm'>{user.departmentName}</div>
                </div>
              </label>
            </div>
          ))}
        </div>
      </div>
      <div className='flex flex-wrap'>
        {selectedUsers.map((user) => (
          <span 
            className='px-2 m-2 bg-mainGray' 
            key={user.userEid}>
            {user.userNameLast}{user.userNameFirst}
          </span>
        ))}
      </div>
      <div className='flex justify-end m-2'>
        <Button className='bg-mainBlue' onClick={handleSendData}>선택</Button>
      </div>
    </div>
  )
}


export default AddressSelect