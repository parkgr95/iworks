import axios from 'axios'
import { ChangeEvent, FormEvent, useEffect, useState } from 'react'
import { useNavigate, useParams, Form } from 'react-router-dom'

// import ScheduleModal from '../../components/ScheduleModal'
import { Button, Label, Select, TextInput } from 'flowbite-react'

interface ScheduleType {
  scheduleId: number
  scheduleDivisionName: string
  scheduleTitle: string
  schedulePriority: string
  scheduleContent: string
  scheduleStartDate: string
  scheduleEndDate: string
  scheduleIsFinish: boolean
  scheduleFinishedAt: string | null
  schedulePlace: string | null
  meetingDate: string | null
  meetingSessionId: string | null
  scheduleCreatorId: number
  scheduleCreatorName: string
  scheduleCreatedAt: string
  scheduleModifierId: number | null,
  scheduleModifierName: string | null
  scheduleModifiedAt: string | null
}

const AdminSchedulesUpdate = () => {
  const { scheduleId = '' } = useParams<{ scheduleId: string }>()
  // const [formState, setFormState] = useState<ScheduleEditForm>()
  const navigate = useNavigate()
  const backToIndex = () => {
    navigate('/admin/schedules')
  }
  // const [scheduleDivisionList, setScheduleDivisionList] = useState<CodeData[]>(
  //   []
  // )
  const [scheduleDetail, setScheduleDetail] = useState<ScheduleType>()
  const [formData, setFormData] = useState({
    scheduleDivisionCodeId: 0,
    scheduleTitle: "",
    schedulePriority: "",
    scheduleContent: "",
    scheduleStartDate: "",
    scheduleEndDate: "",
    schedulePlace: "",
  })

  const { scheduleDivisionCodeId, scheduleTitle, schedulePriority, scheduleContent, scheduleStartDate, scheduleEndDate, schedulePlace } = formData;
  const onInputChange = (event: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = event.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }))
  }

  useEffect(() => {
    async function getScheduleDetail(scheduleId: string) {
      try {
        const res = await axios.get(`https://suhyeon.site/api/admin/schedule/${scheduleId}`)
        setScheduleDetail(res.data.data)
        setFormData({
          scheduleDivisionCodeId: res.data.data.scheduleDivisionCodeId,
          scheduleTitle: res.data.data.scheduleTitle,
          schedulePriority: res.data.data.schedulePriority,
          scheduleContent: res.data.data.scheduleContent,
          scheduleStartDate: res.data.data.scheduleStartDate,
          scheduleEndDate: res.data.data.scheduleEndDate,
          schedulePlace: res.data.data.schedulePlace,
        })
      }
      catch (err) {
        console.log(err)
      }
    }

    getScheduleDetail(scheduleId)
  }, [scheduleId])

  function handleUpdate(event: FormEvent) {
    event.preventDefault()

    const updateSchedule = {
      "scheduleDivisionCodeId": scheduleDivisionCodeId,
      "scheduleTitle": scheduleTitle,
      "schedulePriority": schedulePriority,
      "scheduleContent": scheduleContent,
      "scheduleStartDate": scheduleStartDate,
      "scheduleEndDate": scheduleEndDate,
      "schedulePlace": schedulePlace,
      "meetingCode" : ""
    }

    axios
      .post(`https://suhyeon.site/api/admin/schedule/${scheduleId}/update`, updateSchedule)
      .then(() => {
        alert('수정되었습니다.')
        navigate('/admin/schedules/')
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // // 모달창 구현
  // const [modalIsOpen, setModalIsOpen] = useState(false);
  // const [selectedSchedule, setselectedSchedule] = useState<string | null>(null);

  // const openModal = () => {
  //   setModalIsOpen(true);
  // };

  // const closeModal = () => {
  //   setModalIsOpen(false);
  // };

  // const handleSelectSchedule = (scheduleName: string, scheduleOwnerId: string, scheduleCategoryCodeId: string) => {
  //   setselectedSchedule(scheduleName);
  //   setScheduleOwnerId(scheduleOwnerId)
  //   setCategoryCodeId(scheduleCategoryCodeId)
  // };

  return (
    <div className="p-16">
      <h1 className="text-2xl">할 일 상세 페이지</h1>
      <Form className="grid justify-stretch">
        <div className="grid md:grid-cols-3 my-12 gap-8">
          <div>
            <div className="">완료 여부 : {scheduleDetail?.scheduleIsFinish ? "완료" : "진행중"}</div>
          </div>
          <div>
            <div className="">할 일 생성자 : {scheduleDetail?.scheduleCreatorName}</div>
          </div>
          <div>
            <div className="">할 일 분류 : {scheduleDetail?.scheduleDivisionName}</div>
          </div>
          <div>
            <Label htmlFor="scheduleTitle">제목</Label>
            <TextInput
              id="scheduleTitle"
              type="text"
              name="scheduleTitle"
              onChange={onInputChange}
              value={scheduleTitle}
            />
          </div>
          <div>
            <Label htmlFor="scheduleContent">내용</Label>
            <TextInput
              id="scheduleContent"
              type="text"
              name="scheduleContent"
              onChange={onInputChange}
              value={scheduleContent}
            />
          </div>
          <div>
            <Label htmlFor="schedulePlace">장소</Label>
            <TextInput
              id="schedulePlace"
              type="text"
              name="schedulePlace"
              onChange={onInputChange}
              value={schedulePlace}
            />
          </div>
          <div>
            <Label htmlFor="schedulePriority">
              우선순위
            </Label>
            <Select
              id="schedulePriority"
              name="schedulePriority"
              value={schedulePriority}
              onChange={onInputChange}
            >
              <option value={'H'}>높음</option>
              <option value={'M'}>중간</option>
              <option value={'L'}>낮음</option>
            </Select>
          </div>
          <div>
            <Label htmlFor="scheduleStartDate">
              시작 일시
            </Label>
            <TextInput
              type="datetime-local"
              name="scheduleStartDate"
              required
              id="scheduleStartDate"
              value={scheduleStartDate}
              onChange={onInputChange}
            />
          </div>
          <div>
            <Label htmlFor="scheduleEndDate">
              종료 일시
            </Label>
            <TextInput
              type="datetime-local"
              name="scheduleEndDate"
              required
              id="scheduleEndDate"
              value={scheduleEndDate}
              onChange={onInputChange}
            />
          </div>
          {/* <div>
            <p className="ml-2">{selectedSchedule}</p>
            <ScheduleModal
              show={modalIsOpen}
              onClose={closeModal}
              onSelect={handleSelectSchedule}
            />
          </div> */}
        </div>
        <div className='flex justify-end'>
          {/* <div className="mb-2 block">
            <Label htmlFor="scheduleDivisionCodeId">
              구분
            </Label>
            <Select
              id="scheduleDivisionCodeId"
              name="scheduleDivisionCodeId"
              required
            >
              {scheduleDivisionList.map((scheduleDivisionData) => {
                return (
                  <option
                    key={scheduleDivisionData.codeId}
                    value={scheduleDivisionData.codeId}
                  >
                    {scheduleDivisionData.codeName}
                  </option>
                )
              })}
            </Select>
          </div> */}
          <Button onClick={handleUpdate} className='bg-mainGreen mr-4' type='submit'>수정</Button>
          <Button onClick={backToIndex}>취소</Button>
        </div>
      </Form >
    </div >
  )
}
export default AdminSchedulesUpdate
