import axios from "axios"
import { useState, useEffect } from "react"
import PostType from "../../interface/BoardType"
import { Link } from "react-router-dom"
import dateUtils from "../../utils/dateUtils"

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

function BoardNew() {
  const [boardList, setBoardList] = useState<PostType[]>([])
  const [users, setUsers] = useState<UserType[]>([])

  useEffect(() => {
    axios.get(`https://suhyeon.site/api/board`)
      .then((res) => {
        const allPosts: PostType[] = res.data.data;
        const filteredPosts = allPosts.filter(post => {
          // 게시물의 생성일
          const postDate = post.boardCreatedAt ? new Date(post.boardCreatedAt) : null;
          // 현재 날짜
          const currentDate = new Date();
          // 일주일 이전의 날짜 계산
          const oneWeekAgo = new Date(currentDate.getTime() - 7 * 24 * 60 * 60 * 1000);
          // 게시물의 생성일이 일주일 이내인지 확인
          return postDate && postDate >= oneWeekAgo;
        });
        setBoardList(filteredPosts);
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

  return (
    <div className="">
      {boardList.map((article) => {
        const user: UserType | undefined = users.find((user) => user.userId == article.boardCreatorId)
        
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

export default BoardNew