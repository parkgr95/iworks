import axios from "axios"
import { useState, useEffect } from "react"
import PostType from "../../interface/BoardType"
import { Link } from "react-router-dom"
import dateUtils from "../../utils/dateUtils"
import { useUser } from "../../utils/userInfo"

function BoardMy() {
  const loginedUser = useUser()

  const [boardList, setBoardList] = useState<PostType[]>([])

  useEffect(() => {
    axios.get(`https://suhyeon.site/api/board/`)
      .then((res) => {
        setBoardList(res.data.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }, [])

  const MyArticles = boardList.filter(article => article.boardCreatorId == loginedUser?.userId);

  return (
    <div className="">
      {MyArticles.map((article) => {
        
        return (
        <div className="border-b-2 pb-2 mb-2" key={article.boardId}>
          <div className="mb-2 text-md font-semibold">
            <Link to={`/board/${article.boardId}`}>{article.boardTitle}</Link>
          </div>
          <div className="text-sm mb-2">
            <p>{article.boardContent}</p>
          </div>
          <div className="flex justify-between text-xs w- ml-1">
            <span>{loginedUser ? loginedUser.userNameLast + loginedUser.userNameFirst : 'unKnown'}</span>
            <span>{dateUtils.formatDate(article.boardCreatedAt)}</span>
          </div>
        </div>
        )})}
    </div>
  )
}

export default BoardMy
