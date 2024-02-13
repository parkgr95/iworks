import axios from "axios"
import { useState, useEffect } from "react"
import { Button } from "flowbite-react"
import { useNavigate } from "react-router-dom"

interface DepartmentType {
  departmentCreatedAt: string
  departmentDesc: string
  departmentId: number
  departmentIsUsed: boolean
  departmentLeaderId: number
  departmentName: string
  departmentTelFirst: string
  departmentTelLast: string
  departmentTelMiddle: string
  departmentUpdatedAt: string
}

function AdminDepartments() {
  const [departmentList, setDepartmentList] = useState<DepartmentType[]>([])

  useEffect(() => {
    axios.get(`https://suhyeon.site/api/admin/department/`)
      .then((res) => {
        console.log(res.data.data)
        setDepartmentList(res.data.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }, [])

  const navigate = useNavigate()
  const moveToCreate = () => {
    navigate(`/admin/boards/create`)
  }
  // const moveToUpdate = (boardId: string) => {
  //   navigate(`/admin/boards/update/${boardId}`)
  // }

  // function deleteBoard(boardId: string, event: FormEvent) {
  //   event.preventDefault()
  //   const isConfirmed = window.confirm('게시물을 삭제하시겠습니까?');
  //   if (!isConfirmed) {
  //     return; // 사용자가 취소한 경우 함수를 종료합니다.
  //   }
  //   axios
  //     .put(`https://suhyeon.site/api/board/delete/${boardId}`, {
  //       'boardIsDeleted': '1'
  //     })
  //     .then(() => {
  //       alert('삭제되었습니다.')
  //       window.location.reload()
  //     })
  //     .catch((err) => {
  //       console.log(err)
  //     })
  // }

  return (
    <>
      <div className="flex justify-between items-center text-2xl font-bold  mt-10">
        <h1 className="text-2xl font-bold">게시판 관리</h1>
        <Button className="bg-mainGreen" onClick={moveToCreate}>부서 생성</Button>
      </div>
      <div className="flex justify-between border-2 w- h-[32rem] mt-5">
        <div className="relative overflow-auto w-full">
          <table className="w-full text-sm text-center rtl:text-right text-gray-500">
            <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
              <tr>
                <th scope="col" className="px-6 py-3">
                  부서 이름
                </th>
                <th scope="col" className="px-6 py-3">
                  부서 설명
                </th>
                <th scope="col" className="px-6 py-3">
                  부서장
                </th>
                <th scope="col" className="px-6 py-3">
                  부서 전화번호
                </th>
                <th scope="col" className="px-6 py-3">
                </th>
              </tr>
            </thead>
            <tbody>
              {departmentList.map((department) => {

                return (
                  <tr key={department.departmentId} className="cursor-pointer bg-white border-b hover:bg-gray-100">
                    <th scope="row" className="px-6 py-4 text-gray-900 whitespace-nowrap ">
                      <div className="">
                        {department.departmentName}
                      </div>
                    </th>
                    <td className="px-6 py-4">
                      {department.departmentDesc}
                    </td>
                    <td className="px-6 py-4">
                      {department.departmentLeaderId}
                    </td>
                    <td className="px-6 py-4">
                      {department.departmentTelFirst + "-" + department.departmentTelMiddle + "-" + department.departmentTelLast}
                    </td>
                    <td className="px-6 py-4">
                      {/* <div className="flex">
                    <Button onClick={() => moveToUpdate(department.departmentLeaderId)} className="bg-mainBlue">수정</Button>
                    <Button onClick={(event) => deleteBoard(department.departmentLeaderId, event)} className="bg-rose-700 ml-2">삭제</Button>
                  </div> */}
                    </td>
                  </tr>
                )
              })}
            </tbody>
          </table>
        </div>
      </div>
    </>
  )
}



export default AdminDepartments;
