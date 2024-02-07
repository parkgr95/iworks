import axios from "axios"
import { FormEvent, useEffect, useState } from "react"
import { useParams, useNavigate } from "react-router-dom"
// import CommentCreate from "./BoardCommnetCreate"

import { Button } from "flowbite-react"
import PostType from "../../interface/BoardType"

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

function BoardDetail() {
  const { boardId = '' } = useParams<{boardId: string}>()
  const [boardDetail, setBoardDetail] = useState<PostType>({
    boardId: '',
    boardTitle: '',
    boardCreatorId: '',
    boardModifierId: '',
    boardContent: '',
    boardCreatedAt: '',
    boardUpdatedAt: ''
  })
  const [userName, setUserName] = useState<UserType>()
  const navigate = useNavigate()

  useEffect(() => {
    // 상세 페이지 정보 받아오기
    async function getBoardDetail(boardId: string) {
      try {
        const res = await axios.get(`https://suhyeon.site/api/board/${boardId}`)
        const boardDetailData = res.data.data

        setBoardDetail(boardDetailData)
        getUsers(boardDetail)
      }
      catch (err) {
        console.log(err)
      }
    }

    // 유저 목록 받아오기
    async function getUsers(boardDetail: PostType) {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/user/all`);
        const users = res.data.data
        const filteredUser = users.find((user: UserType) => user.userEid == boardDetail.boardCreatorId)

        setUserName(filteredUser)
      } catch (err) {
        console.log(err);
      }
    }
    
    getBoardDetail(boardId)
  }, [boardId, boardDetail])

  const moveToUpdate = () => {
    navigate('/board/update/' + boardId)
  }

  function deleteBoard(event: FormEvent) {
    event.preventDefault()

    axios
      .patch(`https://suhyeon.site/api/board/${boardId}/`, {
        boardIsDeleted: 1
      })
      .then(() => {
        alert('삭제되었습니다.')
        navigate('/board/')
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // 북마크 기능
  

  return (
    <div className="flex flex-col">
      <div className="h-10 mb-4 text-sm text-gray-500 border-b-2">
        <p>게시판 이름</p>
      </div>
      <div className="flex justify-between items-center mb-4">
        <p className="text-3xl font-semibold">{boardDetail.boardTitle}</p>
        {/* 로직 추가필요 */}
      </div>
      <div className="mb-4">
        <div className="flex items-center">
          <div className="h-10 w-10 pt-2">
            <span>사진</span>
          </div>
          <div className="flex flex-col">
            <span>이름: {userName?.userNameFirst}</span>
            <span>작성일: {boardDetail.boardCreatedAt}</span>
          </div>
        </div>
        <p>최근 수정: {boardDetail.boardCreatedAt}</p>
      </div>
      <div>
        <p>{boardDetail.boardContent}</p>
        <div className="flex justify-end my-6">
          <Button className="" onClick={moveToUpdate}>수정</Button>
          <Button className="ml-4" onClick={deleteBoard}>삭제</Button>
        </div>
      </div>
      <div>
        {/* <CommentCreate /> */}
      </div>
      <div>
        
      </div>

    </div>
  )
}

export default BoardDetail
