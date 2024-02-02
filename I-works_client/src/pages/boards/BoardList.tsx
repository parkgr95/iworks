import axios from "axios"
import { useState, useEffect } from "react"
import PostType from "./interface/BoardType"
import { Link, useParams } from "react-router-dom"

function BoardList() {
  const { boardCategoryCodeId, boardOwnerId } = useParams()
  const [boardList, setBoardList] = useState<PostType[]>([])
  
  useEffect(() => {
    async function getBoardList(boardCategoryCodeId, boardOwnerId) {
      try {
        const res = await axios.get(`https://suhyeon.site/api/?boardCategoryCodeId=${boardCategoryCodeId}&boardOwnerId=${boardOwnerId}`)
        const boardListData = res.data
        setBoardList(boardListData)
      }
      catch (err) {
        console.log(err)
      }
    }
    getBoardList(boardCategoryCodeId, boardOwnerId)
  }, [boardOwnerId])

  return (
    <div className="">
      {boardList.map((article) => (
        <div className="border-b-2 pb-2 mb-2" key={article.id}>
          <div className="mb-2 text-md font-semibold">
            <Link to={`/board/${article.id}`}>{article.title}</Link>
          </div>
          <div className="text-sm mb-2">
            <p>{article.body}</p>
          </div>
          <div className="flex justify-between text-xs w- ml-1">
            <p>진창현</p>
            <p>2024-01-13</p>
          </div>
        </div>
          )
        )
      }
    </div>
  )
}

export default BoardList
