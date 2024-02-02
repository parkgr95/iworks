// import { useEffect, useState } from "react"
// import { useParams } from "react-router-dom"
// import axios from "axios"
// import PostType from "../../interface/BoardType"

function BoardSearch() {
  // const { searchKeyword='' } = useParams<{searchKeyword: string}>()
  // const [searchResult, setSearchResult] = useState<PostType[]>([])
  
  // useEffect(() => {
  //   async function getSearchData(searchKeyword: string) {
  //     try {
  //       const res = await axios.get(`https://suhyeon.site/api/search?requestBoard=${searchKeyword}`)
  //       const boardSearchData = res.data

  //       setSearchResult(boardSearchData)
  //     }
  //     catch (err) {
  //       console.log(err)
  //     }
  //   }
  //   getSearchData(searchKeyword)
  // }, [searchKeyword])

  return (
    <div>
      <div className="h-10 mb-2 border-b-2 text-xl">
        <h1>검색결과</h1>
      </div>
      {/* {searchResult.map((article) => (
        <div className="border-2" key={article.id}>
          <div className="mb-2">
            <Link to={`/board/${article.id}`}>제목 : {article.title}</Link>
          </div>
          <p>내용 : {article.body}</p>
          <p>작성자 : {article.userId}</p>
          <br /> */}
    </div>
  )
}

export default BoardSearch
