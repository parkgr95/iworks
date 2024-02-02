import axios from "axios"
import { FormEvent, useEffect, useState } from "react"
import { useParams, useNavigate } from "react-router-dom"

import { Button } from "flowbite-react"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faStar } from "@fortawesome/free-regular-svg-icons"

function BoardDetail() {
  const { boardId } = useParams()
  const [boardDetail, setBoardDetail] = useState<{ title: string, body: string }>({
    title: '',
    body: ''
  })
  const navigate = useNavigate()

  useEffect(() => {
    async function getBoardDetail(boardId) {
      try {
        const res = await axios.get(`https://dummyjson.com/posts/${boardId}`)
        const boardDetailData = res.data

        setBoardDetail(boardDetailData)
      }
      catch (err) {
        console.log(err)
      }
    }
    getBoardDetail(boardId)
  }, [boardId])

  const moveToUpdate = () => {
    navigate('/board/update/' + boardId)
  }

  function deleteBoard(event: FormEvent) {
    event.preventDefault()

    axios
      .patch(`https://dummyjson.com/posts/${boardId}`, {
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
        <p className="text-3xl font-semibold">{boardDetail.title}</p>
        {/* 로직 추가필요 */}
        <FontAwesomeIcon icon={faStar} size="xl" style={{color: "#1F4068",}} />
      </div>
      <div className="mb-4">
        <div className="flex items-center">
          <div className="h-10 w-10 pt-2">
            <span>사진</span>
          </div>
          <div className="flex flex-col">
            <span>이름: </span>
            <span>작성일: </span>
          </div>
        </div>
        <p>수정일자: </p>
      </div>
      <div>
        <p>{boardDetail.body}</p>
        <div className="flex justify-end my-6">
          <Button className="" onClick={moveToUpdate}>수정</Button>
          <Button className="ml-4" onClick={deleteBoard}>삭제</Button>
        </div>
      </div>
      <div>
        <form>
          <div className="w-full mb-4 border border-gray-200 rounded-sm bg-gray-50">
            <div className="px-4 py-2 bg-white rounded-t-sm">
              <textarea id="comment" rows="4" className="w-full px-0 text-sm text-gray-900 bg-white border-0" placeholder="댓글을 입력하세요" required></textarea>
            </div>
            <div className="flex items-center justify-end px-3 py-2 border-t">
              <button type="submit" className="inline-flex items-center py-2.5 px-4 text-xs font-medium text-center text-white bg-mainGreen rounded-lg hover:bg-blue-800">
                Post comment
              </button>
            </div>
          </div>
        </form>
      </div>

    </div>
  )
}

export default BoardDetail
