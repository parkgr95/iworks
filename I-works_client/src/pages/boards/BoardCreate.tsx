import { ChangeEvent, FormEvent, useState } from "react"
import axios from "axios"
import { useNavigate, Form } from "react-router-dom"
// import BoardModal from "../../components/BoardModal"

import { Button } from "flowbite-react"
import ReactQuill from "react-quill"
import "react-quill/dist/quill.snow.css"

function BoardCreate() {
  const navigate = useNavigate()

  const [boardTitle, setBoardTitle] = useState<string>('')
  const [boardContent, setBoardContent] = useState<string>()
  // const [boardCategoryCodeId, setCategoryCodeId] = useState<number>(1)
  // const [boardOwnerId, setBoardOwnerId] = useState<number>(1)
  
  const onTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setBoardTitle(event.target.value)
  }
  const onContentChange = (content: string) => {
    setBoardContent(content)
  }

  function handleCreate(event: FormEvent) {
    event.preventDefault()
    // api 추가하기
    axios
      .post("https://suhyeon.site/api/board", {
        "boardTitle": boardTitle,
        "boardContent": boardContent,
        "boardCreatorId": '1234',
        "boardIsDeleted": '0',
        // "boardCategoryCodeId": boardCategoryCodeId,
        // "boardOwnerId": boardOwnerId,
      })
      .then((res) => {
        navigate("../")
        console.log(res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  return (
    <div>
      <div className="h-10 mb-2 border-b-2 text-xl">
        <h1>글쓰기</h1>
      </div>
      <Form className="flex flex-col" onSubmit={handleCreate}>
        <div className="flex items-center">
          <label htmlFor="boards" className="block mb-2 text-sm font-medium text-gray-900">게시판 :</label>
          <select id="boards" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-auto p-2.5">
            <option selected>게시판 선택</option>
            <option value="1">고지게시판</option>
            <option value="2">자유게시판</option>
            <option value="3">부서게시판</option>
            <option value="4">그룹게시판</option>
          </select>
        </div>
        <div className="flex items-center my-2">
          <label className="mr-14" htmlFor="title">제목 : </label>
          <input onChange={onTitleChange} className="h-8 w-3/4" type="text" name="boardTitle" value={boardTitle} id="title" required />
        </div>
        <div className="">
          <label className="mr-10" htmlFor="file">첨부파일 : </label>
          <input className="h" type="file" name="" id="file" />
        </div>
        <div className="mt-5">
          <ReactQuill
            style={{ height: "300px" }}
            theme="snow"
            // modules={modules}
            // formats={formats}
            value={boardContent || ''}
            placeholder="내용을 작성해주세요..."
            onChange={onContentChange}
          />
        </div>
        <div className="flex justify-end mt-16">
          <Button className="bg-mainGreen" type="submit">게시글 등록</Button>
        </div>
      </Form>
    </div>
  )
}

export default BoardCreate
