import axios from "axios"
import { useState, useEffect } from "react"
import PostType from "./interface/BoardType"
import { Link } from "react-router-dom"

const API_URL = "https://dummyjson.com/posts"

function BoardList() {
  const [apiData, setApiData] = useState<PostType[]>([])

  useEffect(() => {
    axios.get(API_URL)
      .then((res) => {
        setApiData(res.data.posts)
      })
      .catch((err) => {
        console.log(err)
      })
  }, [])

  return (
    <div className="">
      {apiData.map((article) => (
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
