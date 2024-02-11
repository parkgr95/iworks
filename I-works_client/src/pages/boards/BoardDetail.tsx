import axios from "axios"
import { FormEvent, useEffect, useState } from "react"
import { useParams, useNavigate } from "react-router-dom"
// import CommentCreate from "./BoardCommnetCreate"
import dateUtils from "../../utils/dateUtils"
import { Button } from "flowbite-react"
import PostType from "../../interface/BoardType"
import { FaRegStar } from "react-icons/fa";
import { FaStar } from "react-icons/fa";

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
  const { boardId = '' } = useParams<{ boardId: string }>()
  const [boardDetail, setBoardDetail] = useState<PostType>({
    boardId: '',
    boardTitle: '',
    boardCreatorId: '',
    boardModifierId: '',
    boardContent: '',
    boardCreatedAt: '',
    boardUpdatedAt: '',
    boardCategoryCodeId: ''
  })
  const boardCategory: { [key: string]: string } = {
    "1" : "공지게시판",
    "2" : "자유게시판",
    "3" : "부서게시판",
    "4" : "그룹게시판",
  };
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
        const filteredUser = users.find((user: UserType) => user.userId == boardDetail.boardCreatorId)

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
      .put(`https://suhyeon.site/api/board/delete/${boardId}`, {
        'boardIsDeleted': '1'
      })
      .then(() => {
        alert('삭제되었습니다.')
        navigate('/board/')
      })
      .catch((err) => {
        console.log(err)
      })
  }


  // // 북마크 기능
  // function toggleStar() {
  //   // 별표 상태 업데이트
  //   setIsStarred(prevState => !prevState);

  //   // 여기서 API 요청을 보내고 상태를 업데이트할 수 있음
  //   // axios.put(...) 또는 필요한 API 요청을 보내고 상태를 업데이트하는 코드 작성
  // }

  return (
    <div className="flex flex-col">
      <div className="h-10 mb-4 text-sm text-gray-500 border-b-2">
        <p>{boardCategory[boardDetail.boardCategoryCodeId || ''] || 'Unknown Category'}</p>
      </div>
      <div className="flex justify-between items-center mb-4">
        <p className="text-3xl font-semibold">{boardDetail.boardTitle}</p>
        <FaRegStar size={36}/>
        <FaStar size={36} />
      </div>
      <div className="mb-4">
        <div className="flex items-center">
          <div className="flex flex-col">
            <span>이름: {userName?.userNameLast}{userName?.userNameFirst}</span>
            <span>작성일: {dateUtils.formatDateTime(boardDetail.boardCreatedAt)}</span>
          </div>
        </div>
        <p>최근 수정: {dateUtils.formatDateTime(boardDetail.boardUpdatedAt)}</p>
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
