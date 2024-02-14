import axios from "axios"
import { FormEvent, useEffect, useState } from "react"
import { useParams, useNavigate } from "react-router-dom"
import dateUtils from "../../utils/dateUtils"
import { Button } from "flowbite-react"
import PostType from "../../interface/BoardType"
import { FaRegStar } from "react-icons/fa";
import { FaStar } from "react-icons/fa";
import BoardComment from "./BoardCommnet"
import { getAccessToken } from "../../utils/auth"
import { useUser } from "../../utils/userInfo"

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
  const loginedUser = useUser()
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
    "1": "공지게시판",
    "2": "자유게시판",
    "3": "부서게시판",
    "4": "그룹게시판",
  };
  const [userName, setUserName] = useState<UserType>()
  const navigate = useNavigate()
  const [isStarred, setIsStarred] = useState(false);

  useEffect(() => {
    // 상세 페이지 정보 받아오기
    async function getBoardDetail(boardId: string) {
      try {
        const res = await axios.get(`https://suhyeon.site/api/board/${boardId}`)
        const boardDetailData = res.data.data

        setBoardDetail(boardDetailData)
        getUsers(boardDetailData)
      } catch (err) {
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

    // 북마크 목록 받아오기
    async function getBookmarks() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/board/byBookmark`, {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          }
        })
        const isStarred = res.data.data.some((post: PostType) => post.boardId == boardId);
        if (isStarred) {
          setIsStarred(true);
        }
      }
      catch (err) {
        console.log(err);
      }
    }

    if (boardId) {
      getBoardDetail(boardId);
      getBookmarks();
    }
  }, [boardId]);

  const moveToUpdate = () => {
    navigate('/board/update/' + boardId)
  }

  function deleteBoard(event: FormEvent) {
    event.preventDefault();
  
    const isConfirmed = window.confirm('정말로 삭제하시겠습니까?');
    if (isConfirmed) {
      axios
        .post(`https://suhyeon.site/api/board/delete/${boardId}`, {
          'boardIsDeleted': '1'
        }, {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          }
        })
        .then(() => {
          alert('삭제되었습니다.');
          navigate('/board/');
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

  // // 북마크 기능
  const toggleStar = () => {
    setIsStarred(prevState => !prevState);
    axios
      .post(`https://suhyeon.site/api/board/bookmark/${boardId}`, null, {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
        }
      })
      .then((res) => {
        console.log(res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  const showEditButtons = loginedUser?.userId === boardDetail.boardCreatorId;

  return (
    <div className="flex flex-col">
      <div className="h-10 mb-4 text-sm text-gray-500 border-b-2">
        <p>{boardCategory[boardDetail.boardCategoryCodeId || ''] || 'Unknown Category'}</p>
      </div>
      <div className="flex justify-between items-center mb-4">
        <p className="text-3xl font-semibold">{boardDetail.boardTitle}</p>
        {isStarred ? <FaStar size={36} onClick={toggleStar} /> : <FaRegStar size={36} onClick={toggleStar} />}
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
        {showEditButtons && (
        <div className="flex justify-end mt-6">
          <Button className="" onClick={moveToUpdate}>수정</Button>
          <Button className="ml-4" onClick={deleteBoard}>삭제</Button>
        </div>
      )}
      <div className="mb-6"></div>
      </div>
      <div>
        <BoardComment boardId={boardId} />
      </div>
      <div>

      </div>

    </div>
  )
}

export default BoardDetail
