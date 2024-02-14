import axios from 'axios'
import { ChangeEvent, FormEvent, useEffect, useState } from 'react'
import { useNavigate, useParams, Form } from 'react-router-dom'

import ScheduleModal from '../../components/ScheduleModal'
import ReactQuill from "react-quill"
import "react-quill/dist/quill.snow.css"
import { Button } from 'flowbite-react'

const AdminSchedulesUpdate = () => {
  const { scheduleId = '' } = useParams<{ scheduleId: string }>()

  const navigate = useNavigate()
  const backToIndex = () => {
    navigate('/admin/schedules/')
  }

  const [scheduleTitle, setScheduleTitle] = useState<string>('')
  const [scheduleContent, setScheduleContent] = useState<string>('')
  const [scheduleOwnerId, setScheduleOwnerId] = useState<string>('')
  const [scheduleCategoryCodeId, setCategoryCodeId] = useState<string>('')
  // const [scheduleModifierId, setScheduleModifierId] = useState<number>()

  const onTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setScheduleTitle(event.target.value)
  }

  const onContentChange = (content: string) => {
    setScheduleContent(content)
  }

  useEffect(() => {
    async function getScheduleDetail(scheduleId: string) {
      try {
        const res = await axios.get(`https://suhyeon.site/api/schedule/${scheduleId}`)
        const scheduleDetailData = res.data.data

        setScheduleTitle(scheduleDetailData.scheduleTitle)
        setScheduleContent(scheduleDetailData.scheduleContent)
        setScheduleOwnerId(scheduleDetailData.scheduleOwnerId)
        setCategoryCodeId(scheduleDetailData.scheduleCategoryId)
      }
      catch (err) {
        console.log(err)
      }
    }

    getScheduleDetail(scheduleId)
  }, [scheduleId])

  function handleUpdate(event: FormEvent) {
    event.preventDefault()
    const plainTextContent = (scheduleContent || '').replace(/<[^>]+>/g, '');
    const updateSchedule = {
      "scheduleTitle": scheduleTitle,
      "scheduleContent": plainTextContent,
      "scheduleCreatorId": '1',
      "scheduleIsDeleted": '0',
      "scheduleCategoryCodeId": scheduleCategoryCodeId,
      "scheduleOwnerId": scheduleOwnerId,
    }

    axios
      .put(`https://suhyeon.site/api/schedule/update/${scheduleId}`, updateSchedule)
      .then(() => {
        alert('수정되었습니다.')
        navigate('/admin/schedules/')
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // 모달창 구현
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [selectedSchedule, setselectedSchedule] = useState<string | null>(null);

  const openModal = () => {
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setModalIsOpen(false);
  };

  const handleSelectSchedule = (scheduleName: string, scheduleOwnerId: string, scheduleCategoryCodeId: string) => {
    setselectedSchedule(scheduleName);
    setScheduleOwnerId(scheduleOwnerId)
    setCategoryCodeId(scheduleCategoryCodeId)
  };

  return (
    <div>
      <div className="h-10 mb-2 border-b-2 text-xl">
        <h1>할 일 수정</h1>
      </div>
      <Form className="flex flex-col" onSubmit={handleUpdate}>
        <div className="flex items-center">
          <p className="ml-2">{selectedSchedule}</p>
          <ScheduleModal
            show={modalIsOpen}
            onClose={closeModal}
            onSelect={handleSelectSchedule}
          />
        </div>
        <div className="flex items-center my-2">
          <label className="mr-14" htmlFor="title">제목 : </label>
          <input onChange={onTitleChange} className="h-8 w-3/4" type="text" name="scheduleTitle" value={scheduleTitle} id="title" required />
        </div>

        <div className="flex items-center my-2">
          <label className="mr-14" htmlFor="content">내용 : </label>
          <input onChange={onTitleChange} className="h-8 w-3/4" type="text" name="scheduleContent" value={scheduleTitle} id="content" required />
        </div>
        <br />
        <div className='flex justify-end mt-10 '>
          <Button className='bg-mainGreen mr-4' type='submit'>수정</Button>
          <Button onClick={backToIndex}>취소</Button>
        </div>
      </Form>
    </div>
  )
}

export default AdminSchedulesUpdate