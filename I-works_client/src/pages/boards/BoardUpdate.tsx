import axios from 'axios'
import { ChangeEvent, FormEvent, useEffect, useState } from 'react'
import { useNavigate, useParams, Form } from 'react-router-dom'

import BoardModal from '../../components/BoardModal'
import ReactQuill from "react-quill"
import "react-quill/dist/quill.snow.css"
import { Button } from 'flowbite-react'

const BoardUpdate = () => {
  const { boardId = '' } = useParams<{ boardId: string }>()

  const navigate = useNavigate()
  const backToDetail = () => {
    navigate('/board/' + boardId)
  }

  const [boardTitle, setBoardTitle] = useState<string>('')
  const [boardContent, setBoardContent] = useState<string>('')
  const [boardOwnerId, setBoardOwnerId] = useState<string>('')
  const [boardCategoryCodeId, setCategoryCodeId] = useState<string>('')
  // const [boardModifierId, setBoardModifierId] = useState<number>()

  const onTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setBoardTitle(event.target.value)
  }

  const onContentChange = (content: string) => {
    setBoardContent(content)
  }

  useEffect(() => {
    async function getBoardDetail(boardId: string) {
      try {
        const res = await axios.get(`https://suhyeon.site/api/board/${boardId}`)
        const boardDetailData = res.data.data

        setBoardTitle(boardDetailData.boardTitle)
        setBoardContent(boardDetailData.boardContent)
        setBoardOwnerId(boardDetailData.boardOwnerId)
        setCategoryCodeId(boardDetailData.boardCategoryId)
      }
      catch (err) {
        console.log(err)
      }
    }

    getBoardDetail(boardId)
  }, [boardId])

  function handleUpdate(event: FormEvent) {
    event.preventDefault()
    const plainTextContent = (boardContent || '').replace(/<[^>]+>/g, '');
    const updateBoard = {
      "boardTitle": boardTitle,
      "boardContent": plainTextContent,
      "boardCreatorId": '1',
      "boardIsDeleted": '0',
      "boardCategoryCodeId": boardCategoryCodeId,
      "boardOwnerId": boardOwnerId,
    }

    axios
      .put(`https://suhyeon.site/api/board/update/${boardId}`, updateBoard)
      .then(() => {
        alert('수정되었습니다.')
        navigate('/board/' + boardId)
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
      <Form className="flex flex-col" onSubmit={handleUpdate}>
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
        <br />
        <div className='flex justify-end mt-10 '>
          <Button className='bg-mainGreen mr-4' type='submit'>수정</Button>
          <Button onClick={backToDetail}>취소</Button>
        </div>
      </Form>
    </div>
  )
}

export default BoardUpdate