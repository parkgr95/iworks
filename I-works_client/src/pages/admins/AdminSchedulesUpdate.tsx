import axios from 'axios'
import { ChangeEvent, FormEvent, useEffect, useState } from 'react'
import { useNavigate, useParams, Form } from 'react-router-dom'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'

import 'react-quill/dist/quill.snow.css'
import { Button } from 'flowbite-react'

interface ScheduleEditForm {
  scheduleDivisionCodeId: number
  scheduleTitle: string
  schedulePriority: string
  scheduleContent: string
  scheduleStartDate: Date
  scheduleEndDate: Date
  meetingDate: Date
  schedulePlace: string
  meetingCode: string
}

const AdminSchedulesUpdate: React.FC = () => {
  const { scheduleId = '' } = useParams<{ scheduleId: string }>()
  const [formState, setFormState] = useState<ScheduleEditForm>()
  const navigate = useNavigate()
  const backToIndex = () => {
    navigate('/admin/schedules/')
  }

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target
    console.log(name)
    setFormState({
      ...formState,
      [name]: value,
    })
  }

  const handleDateChange = (date: Date, name: string) => {
    setFormState({
      ...formState,
      [name]: date,
    })
  }

  useEffect(() => {
    async function getScheduleDetail(scheduleId: string) {
      try {
        const res = await axios.get(
          `https://suhyeon.site/api/schedule/${scheduleId}`,
        )
        const scheduleDetailData = res.data.data
        const initData: ScheduleEditForm = {
          //scheduleDivisionCodeId: scheduleDetailData.scheduleDivisionCodeId,
          scheduleDivisionCodeId: 1,
          scheduleTitle: scheduleDetailData.scheduleTitle,
          schedulePriority: scheduleDetailData.schedulePriority,
          scheduleContent: scheduleDetailData.scheduleContent,
          scheduleStartDate: scheduleDetailData.scheduleStartDate,
          scheduleEndDate: scheduleDetailData.scheduleEndDate,
          schedulePlace: scheduleDetailData.schedulePlace,
          //meetingCode: scheduleDetailData.meetingCode,
          meetingCode: 'test',
          meetingDate: scheduleDetailData.meetingDate,
        }
        setFormState(initData)
      } catch (err) {
        console.log(err)
      }
    }

    getScheduleDetail(scheduleId)
  }, [scheduleId])

  function handleUpdate(event: FormEvent) {
    event.preventDefault()
    console.log(formState)
    axios
      .post(`https://suhyeon.site/api/schedule/${scheduleId}/update`, formState)
      .then(() => {
        alert('수정되었습니다.')
        navigate('/admin/schedules/')
      })
      .catch((err) => {
        console.log(err)
      })
  }

  return (
    <div>
      <div className="h-10 mb-2 border-b-2 text-xl">
        <h1>할 일 수정</h1>
      </div>
      <Form className="flex flex-col" onSubmit={handleUpdate}>
        <div className="flex items-center my-2">
          <label className="mr-14" htmlFor="title">
            제목 :{' '}
          </label>
          <input
            onChange={handleInputChange}
            className="h-8 w-3/4"
            type="text"
            name="scheduleTitle"
            value={formState?.scheduleTitle}
            id="title"
            required
          />
        </div>

        <div className="flex items-center my-2">
          <label className="mr-14" htmlFor="content">
            내용 :{' '}
          </label>
          <input
            onChange={handleInputChange}
            className="h-8 w-3/4"
            type="text"
            name="scheduleContent"
            value={formState?.scheduleContent}
            id="content"
            required
          />
        </div>

        <div className="flex items-center my-2">
          <label className="mr-14" htmlFor="content">
            장소 :{' '}
          </label>
          <input
            onChange={handleInputChange}
            className="h-8 w-3/4"
            type="text"
            name="schedulePlace"
            value={formState?.schedulePlace}
            id="content"
            required
          />
        </div>

        <div className="flex items-center my-2">
          <label className="mr-14" htmlFor="content">
            시작 시간 :{' '}
          </label>
          <DatePicker
            showIcon
            dateFormat="yyyy.MM.dd" // 날짜 형태
            shouldCloseOnSelect // 날짜를 선택하면 datepicker가 자동으로 닫힘
            selected={formState?.scheduleStartDate}
            onChange={(date) => handleDateChange(date!, 'scheduleStartDate')}
          />
        </div>
        <div className="flex items-center my-2">
          <label className="mr-14" htmlFor="content">
            마감 시간 :{' '}
          </label>
          <DatePicker
            showIcon
            dateFormat="yyyy.MM.dd" // 날짜 형태
            shouldCloseOnSelect // 날짜를 선택하면 datepicker가 자동으로 닫힘
            selected={formState?.scheduleEndDate}
            onChange={(date) => handleDateChange(date!, 'scheduleEndDate')}
          />
        </div>
        <br />
        <div className="flex justify-end mt-10 ">
          <Button className="bg-mainGreen mr-4" type="submit">
            수정
          </Button>
          <Button onClick={backToIndex}>취소</Button>
        </div>
      </Form>
    </div>
  )
}
export default AdminSchedulesUpdate
