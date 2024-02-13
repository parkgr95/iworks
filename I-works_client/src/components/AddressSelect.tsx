import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button } from 'flowbite-react';

interface OrganizationType {
  departmentName?: string;
  departmentId?: string;
  teamName?: string;
  teamId?: string;
}

interface UserData {
  userId: string;
  userEid: string;
  userNameFirst: string;
  userNameLast: string;
  departmentName: string;
  departmentId: string;
  positionCodeName: null;
  positionCodeId: null;
  userTel: string;
  userEmail: string;
}

function AddressSelect() {
  const [users, setUsers] = useState<UserData[]>([]);
  const [selectedUsers, setSelectedUsers] = useState<UserData[]>([]);
  const [departmentList, setDepartmentList] = useState<OrganizationType[]>([]);
  const [selectedDepartment, setSelectedDepartment] = useState<string | null>(null);
  const [filteredUsers, setFilteredUsers] = useState<UserData[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>('');

  useEffect(() => {
    async function getDepartments() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/department/all`);
        setDepartmentList(res.data.data);
      } catch (err) {
        console.log(err);
      }
    }

    async function getUsers() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/user/all`);
        setUsers(res.data.data);
      } catch (err) {
        console.log(err);
      }
    }

    getDepartments();
    getUsers();
  }, []);

  useEffect(() => {
    const filteredUsers = selectedDepartment
      ? users.filter((user) => user.departmentId === selectedDepartment)
      : users;
    setFilteredUsers(filteredUsers);
  }, [selectedDepartment, users]);

  const handleUserToggle = (user: UserData) => {
    const isSelected = selectedUsers.some((selectedUser) => selectedUser.userEid === user.userEid);

    if (isSelected) {
      setSelectedUsers(selectedUsers.filter((selectedUser) => selectedUser.userEid !== user.userEid));
    } else {
      setSelectedUsers([...selectedUsers, user]);
    }
  };

  const handleDepartmentSelect = (departmentId: string) => {
    setSelectedDepartment(departmentId);
  };

  const handleSendData = () => {
    window.opener.postMessage({ type: 'SEND_DATA', payload: { selectedUsers } }, window.location.href);
    setTimeout(() => {
      window.close();
    }, 100);
  };

  const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  const filteredUsersByName = filteredUsers.filter((user) =>
    `${user.userNameLast}${user.userNameFirst}`.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="h-full pb-10">
      <div className='flex justify-between border-b-2 p-5 items-center'>
      <h2 className="font-bold text-xl">주소록</h2>
      <input
        type="text"
        value={searchTerm}
        onChange={handleSearch}
        placeholder="이름으로 검색"
        className="mb-2 w-1/2 p-2 border border-gray-300 rounded-md"
      />
      </div>
      <div className="flex h-3/4 border-b-2">
        <div className="w-60 border-r-2 p-2">
          <span className="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap font-semibold">조직도</span>
          <ul>
            <li>
              <ul>
                {departmentList.map((dept) => (
                  <li key={dept.departmentId}>
                    <span
                      className={`flex items-center w-full text-mainBlack pl-16 pt-2 text-sm cursor-pointer ${selectedDepartment === dept.departmentId ? 'text-mainBlue' : ''
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
        <div className="overflow-auto h-full w-full p-2">
          {filteredUsersByName.map((user) => (
            <div key={user.userEid}>
              <label className="flex items-center mb-2 border-b-2">
                <input
                  className="mr-4"
                  type="checkbox"
                  checked={selectedUsers.some((selectedUser) => selectedUser.userEid === user.userEid)}
                  onChange={() => handleUserToggle(user)}
                />
                <div>
                  <div className="font-semibold">
                    {user.userNameLast}
                    {user.userNameFirst}
                  </div>
                  <div className="font-thin text-sm">{user.departmentName}</div>
                </div>
              </label>
            </div>
          ))}
        </div>
      </div>
      <div className="flex flex-wrap">
        {selectedUsers.map((user) => (
          <span className="px-2 m-2 bg-mainGray" key={user.userEid}>
            {user.userNameLast}
            {user.userNameFirst}
          </span>
        ))}
      </div>
      <div className="flex justify-end m-2">
        <Button className="bg-mainBlue" onClick={handleSendData}>
          선택
        </Button>
      </div>
    </div>
  );
}

export default AddressSelect;