import axios from "axios"
import { useEffect, useState, useRef } from "react"
import SignupPage, { SignupRef } from '../SignupPage'
import { Button } from "flowbite-react"
import { useNavigate } from "react-router-dom"
import { getAccessToken } from "../../utils/auth"

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

interface orginizationType {
  departmentName: string,
  departmentId: string,

}

function AdminUsers() {
  const navigate = useNavigate()
  const [userAll, setUserAll] = useState<UserData[]>([])
  const [departmentList, setDepartmentList] = useState<orginizationType[]>([])
  const [selectedDepartment, setSelectedDepartment] = useState<string>("")

  useEffect(() => {
    axios.get(`https://suhyeon.site/api/admin/user/`, {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
      .then((res) => {
        setUserAll(res.data.data)
      })
      .catch((err) => {
        console.log(err)
      })

    axios.get('https://suhyeon.site/api/address/department/all', {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
      .then((res) => {
        setDepartmentList(res.data.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }, [])

  const dialog = useRef<SignupRef>(null)

  function handleModal() {
    dialog.current?.open()
  }

  function handleDepartmentClick(departmentName: string) {
    setSelectedDepartment(departmentName === "A208" ? "" : departmentName) // A208인 경우 빈 문자열로 설정하여 전체 사용자를 보여줌
  }

  function handleTableRowClick(userId: string) {
    // 사용자의 상세 정보 페이지로 이동
    navigate(`/admin/users/${userId}`);
  }

  return (
    <>
      <div className="flex justify-between items-center text-2xl font-bold  mt-10">
        <h1 className="text-2xl font-bold">구성원 관리</h1>
        <SignupPage ref={dialog} />
        <Button className="bg-mainGreen" onClick={handleModal}>구성원 추가</Button>
      </div>
      <div className="flex justify-between border-2 w- h-[32rem] mt-5">
        <div className="w-56 p-5 border-r-2">
          <h1 onClick={() => handleDepartmentClick("A208")} className="cursor-pointer font-semibold pl-10">A208</h1>
          <ul className="space-y-2">
            {departmentList.map((dept) => (
              <li className="cursor-pointer" key={dept.departmentId}>
                <h2 onClick={() => handleDepartmentClick(dept.departmentName)} className="flex items-center text-mainBlack pl-14 pt-2 text-sm ">{dept.departmentName}</h2>
              </li>
            ))}
          </ul>
        </div>
        <div className="relative overflow-auto w-full">
          <div>

          </div>
          <table className="w-full text-sm text-center rtl:text-right text-gray-500">
            <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
              <tr>
                <th scope="col" className="px-6 py-3">
                  이름
                </th>
                <th scope="col" className="px-6 py-3">
                  직책/직급
                </th>
                <th scope="col" className="px-6 py-3">
                  부서
                </th>
                <th scope="col" className="px-6 py-3">
                  이메일
                </th>
                <th scope="col" className="px-6 py-3">
                  전화번호
                </th>
              </tr>
            </thead>
            <tbody>
              {userAll.filter(user => selectedDepartment === "" || user.departmentName === selectedDepartment).map((user) => (
                  <tr key={user.userId} onClick={() => handleTableRowClick(user.userId)} className="cursor-pointer bg-white border-b hover:bg-gray-100">
                    <th scope="row" className="flex items-center px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white">
                      <div className="ps-3">
                        {user.userNameLast}{user.userNameFirst}
                      </div>
                    </th>
                    <td className="px-6 py-4">
                      {user.positionCodeName}
                    </td>
                    <td className="px-6 py-4">
                      {user.departmentName}
                    </td>
                    <td className="px-6 py-4">
                      {user.userEmail}
                    </td>
                    <td className="px-6 py-4">
                      {user.userTel}
                    </td>
                  </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </>
  )
}

export default AdminUsers;
