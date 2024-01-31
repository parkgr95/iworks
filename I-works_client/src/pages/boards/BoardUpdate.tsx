import React, { ChangeEvent, FormEvent, useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import axios from 'axios'

import ReactQuill from "react-quill"
import "react-quill/dist/quill.snow.css"
import { Button } from 'flowbite-react'

const BoardUpdate = () => {
  const { boardId } = useParams<{boardId: string}>()

  const navigate = useNavigate()
  const backToDetail = () => {
    navigate('/board/' + boardId)
  }

  const [boardTitle, setBoardTitle] = useState<string>('')
  const [boardContent, setBoardContent] = useState<string>('')
  // const [boardModifierId, setBoardModifierId] = useState<number>()

  const onTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setBoardTitle(event.target.value)
  }

  const onContentChange = (content: string) => {
    setBoardContent(content)
  }

  useEffect(() => {
    async function getBoardDetail(boardId) {
      try {
        const res = await axios.get(`https://dummyjson.com/posts/${boardId}`)
        const boardDetailData = res.data

        setBoardTitle(boardDetailData.title)
        setBoardContent(boardDetailData.body)
      }
      catch (err) {
        console.log(err)
      }
    }

    getBoardDetail(boardId)
  }, [boardId])

  function handleUpdate(event: FormEvent) {
    event.preventDefault()

    const updateBoard = {
      title: boardTitle,
      body: boardContent
    }

    axios
      .put(`https://dummyjson.com/posts/${boardId}`, updateBoard)
      .then(() => {
        alert('수정되었습니다.')
        navigate('/board/' + boardId)
      })
      .catch((err) => {
        console.log(err)
      })
  }


  return (

    <div>
      <div className="flex items-center">
        <label className="mr-10" htmlFor="category">게시판 : </label>
        <select name="" id="category">
          <li>1</li>
          <li>2</li>
          <li>3</li>
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
      <br />
      <div className='flex justify-end mt-10 '>
        <Button className='bg-mainGreen mr-4' onClick={handleUpdate}>수정</Button>
        <Button onClick={backToDetail}>취소</Button>
      </div>
    </div>
  )
}

export default BoardUpdate