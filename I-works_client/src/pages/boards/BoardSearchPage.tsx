import { useEffect, useState } from "react"
import { Link, useParams } from "react-router-dom"
import axios from "axios"
import PostType from "../../interface/BoardType"
import dateUtils from "../../utils/dateUtils"
import { getAccessToken } from "../../utils/auth"

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

function BoardSearch() {
  const { searchKeyword = '' } = useParams<{ searchKeyword: string }>()
  const [searchResult, setSearchResult] = useState<PostType[]>([])
  const [users, setUsers] = useState<UserType[]>([])

  useEffect(() => {
    console.log(searchKeyword)
    async function getSearchData() {
      try {
        const userRes = await axios.get(`https://suhyeon.site/api/address/user/all`, {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          }
        });
        const searchRes = await axios.get(`https://suhyeon.site/api/board/total-search?keywords=${searchKeyword}`, {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          }
        })
        console.log(searchRes)
        setUsers(userRes.data.data)
        setSearchResult(searchRes.data.data)
      }
      catch (err) {
        console.log(err)
      }
    }

    getSearchData()
  }, [searchKeyword])

  return (
    <div>
      <div className="h-10 mb-2 border-b-2 text-xl">
        <h1>검색결과</h1>
      </div>
      {searchResult.map((article) => {
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
            <span>{dateUtils.formatDate(article.boardCreatedAt)}</span>
          </div>
        </div>
        )})}
    </div>
  )
}

export default BoardSearch
