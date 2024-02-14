import { ChangeEvent, FormEvent, useState } from "react"
import axios from "axios"
import { useNavigate, Form } from "react-router-dom"
import BoardModal from "../../components/BoardModal"
import { Button, } from "flowbite-react"
import ReactQuill from "react-quill"
import "react-quill/dist/quill.snow.css"
import { getAccessToken } from "../../utils/auth"
import { useUser } from "../../utils/userInfo"

function AdminBoardsCreate() {
  const loginedUser = useUser()
  const navigate = useNavigate()
  console.log(loginedUser)
  const [boardTitle, setBoardTitle] = useState<string>('')
  const [boardContent, setBoardContent] = useState<string>()
  const [boardOwnerId, setBoardOwnerId] = useState<string>('')
  const [boardCategoryCodeId, setCategoryCodeId] = useState<string>('')

  const onTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setBoardTitle(event.target.value)
  }
  const onContentChange = (content: string) => {
    setBoardContent(content)
  }

  function handleCreate(event: FormEvent) {
    event.preventDefault()
    if (!loginedUser) {
      // 유저 정보가 없을 경우에 대한 처리
      return alert("로그인이 필요합니다.");
    }
    
    const plainTextContent = (boardContent || '').replace(/<[^>]+>/g, '');
    axios
      .post("https://suhyeon.site/api/board", {
        "boardTitle": boardTitle,
        "boardContent": plainTextContent,
        "boardCreatorId": loginedUser.userId,
        "boardIsDeleted": '0',
        "boardCategoryCodeId": boardCategoryCodeId,
        "boardOwnerId": boardOwnerId,
      },         {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
        },
      })
      .then((res) => {
        navigate("/admin/boards")
        console.log(res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // 모달창 구현
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [selectedBoard, setselectedBoard] = useState<string | null>(null);

  const openModal = () => {
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setModalIsOpen(false);
  };

  const handleSelectBoard = (boardName: string, boardOwnerId: string, boardCategoryCodeId: string) => {
    setselectedBoard(boardName);
    setBoardOwnerId(boardOwnerId)
    setCategoryCodeId(boardCategoryCodeId)
  };

  return (
    <div>
      <div className="h-10 mb-2 border-b-2 text-xl">
        <h1>글쓰기</h1>
      </div>
      <Form className="flex flex-col" onSubmit={handleCreate}>
        <div className="flex items-center">
          <span>게시판 : </span>
          <Button className="ml-8 h-8 w-auto bg-mainGray text-black" type="button" onClick={openModal}>
            게시판 선택
          </Button>
          <p className="ml-2">{selectedBoard}</p>
          <BoardModal
            show={modalIsOpen}
            onClose={closeModal}
            onSelect={handleSelectBoard}
          />
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

export default AdminBoardsCreate
