import axios from "axios"
import { useState, useEffect } from "react"
import PostType from "../../interface/BoardType"
import { Link, useParams } from "react-router-dom"

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

function BoardList() {
  const { boardCategoryCodeId='', boardOwnerId='' } = useParams<{boardCategoryCodeId: string, boardOwnerId: string}>()
  const [boardList, setBoardList] = useState<PostType[]>([])
  const [users, setUsers] = useState<UserType[]>([])
  
  useEffect(() => {
    async function getBoardList(boardCategoryCodeId: string, boardOwnerId: string) {
      try {
        const res = await axios.get(`https://suhyeon.site/api/board/byCategory?boardCategoryCodeId=${boardCategoryCodeId}&boardOwnerId=${boardOwnerId}`)
        const boardListData = res.data.data
        setBoardList(boardListData)
      }
      catch (err) {
        console.log(err)
      }
    }

    async function getUsers() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/user/all`);
        setUsers(res.data.data)
      } catch (err) {
        console.log(err);
      }
    }
    getBoardList(boardCategoryCodeId, boardOwnerId)
    getUsers()

  }, [boardCategoryCodeId, boardOwnerId])

  return (
    <div className="">
      {boardList.map((article) => {
        const user: UserType | undefined = users.find((user) => user.userEid == article.boardCreatorId)
        
        return (
        <div className="border-b-2 pb-2 mb-2" key={article.boardId}>
          <div className="mb-2 text-md font-semibold">
            <Link to={`/board/${article.boardId}`}>{article.boardTitle}</Link>
          </div>
          <div className="text-sm mb-2">
            <p>{article.boardContent}</p>
          </div>
          <div className="flex justify-between text-xs w- ml-1">
            <span>{user ? user.userNameLast + user.userNameFirst : 'unKnown'}</span>
            <span>{article.boardCreatedAt}</span>
          </div>
        </div>
        )})}
    </div>
  )
}


export default BoardList
