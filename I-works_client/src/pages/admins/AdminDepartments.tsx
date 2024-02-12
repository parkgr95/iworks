import axios from "axios"
import { useState, useEffect, FormEvent } from "react"
import PostType from "../../interface/BoardType"
import { Button } from "flowbite-react"
import { useNavigate } from "react-router-dom"

interface UserType {
  userId: string
  userEid: string
  userNameFirst: string
  userNameLast: string
  departmentName: string
  departmentId: string
  positionCodeName: null
  positionCodeId: null
  userTel: string
  userEmail: string
}

function AdminDepartments() {
  const [boardList, setBoardList] = useState<PostType[]>([])
  const [users, setUsers] = useState<UserType[]>([])

  useEffect(() => {
    axios.get(`https://suhyeon.site/api/board/`)
      .then((res) => {
        setBoardList(res.data.data)
      })
      .catch((err) => {
        console.log(err)
      })

    async function getUsers() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/user/all`);
        setUsers(res.data.data)
      } catch (err) {
        console.log(err);
      }
    }

    getUsers()
  }, [])

  const navigate = useNavigate()
  const moveToCreate = () => {
    navigate(`/admin/boards/create`)
  }
  const moveToUpdate = (boardId: string) => {
    navigate(`/admin/boards/update/${boardId}`)
  }

  function deleteBoard(boardId:string, event: FormEvent) {
    event.preventDefault()
    const isConfirmed = window.confirm('게시물을 삭제하시겠습니까?');
    if (!isConfirmed) {
      return; // 사용자가 취소한 경우 함수를 종료합니다.
    }
    axios
      .put(`https://suhyeon.site/api/board/delete/${boardId}`, {
        'boardIsDeleted': '1'
      })
      .then(() => {
        alert('삭제되었습니다.')
        window.location.reload()
      })
      .catch((err) => {
        console.log(err)
      })
  }

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

            </th>
          </tr>
        </thead>
        <tbody>
          {boardList.map((article) => {
            const user: UserType | undefined = users.find((user) => user.userId == article.boardCreatorId)

            return (
              <tr key={article.boardId} className="cursor-pointer bg-white border-b hover:bg-gray-100">
                <th scope="row" className="flex items-center px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white">
                  <div className="ps-3">
                  {article.boardTitle}
                  </div>
                </th>
                <td className="px-6 py-4">
                {article.boardContent}
                </td>
                <td className="px-6 py-4">
                {user ? user.userNameLast + user.userNameFirst : 'unKnown'}
                </td>
                <td className="px-6 py-4">
                  <div className="flex">
                    <Button onClick={() => moveToUpdate(article.boardId)} className="bg-mainBlue">수정</Button>
                    <Button onClick={(event) => deleteBoard(article.boardId, event)} className="bg-rose-700 ml-2">삭제</Button>
                  </div>
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
