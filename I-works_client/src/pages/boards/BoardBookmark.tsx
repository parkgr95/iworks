// import axios from "axios"
// import { useState, useEffect } from "react"
// import PostType from '../../interface/BoardType.ts';
// import { Link, useParams } from "react-router-dom"

// function BoardBookMark() {
//   const { boardCreatorId = '' } = useParams<{boardCreatorId: string}>()
//   const [bookMarkList, setBookMarkList] = useState<PostType[]>([])
  
//   useEffect(() => {
//     async function getBoardList(boardCreatorId: string) {
//       try {
//         const res = await axios.get(`${boardCreatorId}`)
//         const bookMarkListData = res.data
//         setBookMarkList(bookMarkListData)
//       }
//       catch (err) {
//         console.log(err)
//       }
//     }
//     getBoardList(boardCreatorId)
//   }, [boardCreatorId])

//   return (
//     <div className="">
//       {bookMarkList.map((article) => (
//         <div className="border-b-2 pb-2 mb-2" key={article.id}>
//           <div className="mb-2 text-md font-semibold">
//             <Link to={`/board/${article.id}`}>{article.title}</Link>
//           </div>
//           <div className="text-sm mb-2">
//             <p>{article.body}</p>
//           </div>
//           <div className="flex justify-between text-xs w- ml-1">
//             <p>진창현</p>
//             <p>2024-01-13</p>
//           </div>
//         </div>
//           )
//         )
//       }
//     </div>
//   )
// }

// export default BoardBookMark
