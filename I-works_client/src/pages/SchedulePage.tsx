import axios from 'axios'
import { Form, useLoaderData, useLocation } from 'react-router-dom'
import ScheduleSideBar from '../components/ScheduleSideBar'
import { API_URL } from '../utils/api'
import { Button, TextInput } from 'flowbite-react'
import { useEffect, useState } from 'react'
import ScheduleList from '../components/ScheduleList'
import { AssigneeType } from '../components/ScheduleCreate'
import { UserDetail } from '../utils/User'

const API_SCH = API_URL + '/schedule-assign/get-by-assignees-and-date'

interface DateConditionType {
  startDate: string
  endDate: string
}

interface ScheduleRequestBody {
  assigneeInfos: AssigneeType[]
  dateCondition: DateConditionType
}

function SchedulePage() {
  const loader = useLoaderData() as UserDetail

  const [startDate, setStartDate] = useState(() => {
    const currentDate = new Date()
    currentDate.setDate(currentDate.getDate() - 30)
    currentDate.setHours(currentDate.getHours() + 9)
    console.log(currentDate.toISOString().slice(0, -8))
    return currentDate.toISOString().slice(0, -8)
  })

  const [endDate, setEndDate] = useState(() => {
    const initialEndDate = new Date()
    initialEndDate.setDate(initialEndDate.getDate() + 30)
    initialEndDate.setHours(initialEndDate.getHours() + 9)
    return initialEndDate.toISOString().slice(0, -8)
  })

  const [userInfo, _setUserInfo] = useState<UserDetail>(loader)

  const param = new URLSearchParams(useLocation().search)
  const mode = param.get('mode')

  function getSchedule() {
    if (startDate > endDate){
      alert("검색 시작 기간이 종료 기간보다 더 뒤에 있습니다.")
      return      
    }
    axios
      .post(API_SCH, scheduleRequestBody! as ScheduleRequestBody)
      .then((response) => {
        setScheduleList(response.data.data)
      })
      .catch((_error) => alert('오류'))
  }

  const [scheduleList, setScheduleList] = useState<[]>([])

  let scheduleRequestBody = {
    assigneeInfos: [] as AssigneeType[],
    dateCondition: {
      startDate,
      endDate,
    },
  }

  switch (mode) {
    case 'all':
      scheduleRequestBody.assigneeInfos = [
        {
          categoryCodeId: 5,
          assigneeId: userInfo?.userId,
        },
        {
          categoryCodeId: 6,
          assigneeId: userInfo?.departmentId,
        },
        {
          categoryCodeId: 7,
          assigneeId: 1,
        },
      ]
      break
    case 'user':
      scheduleRequestBody.assigneeInfos = [
        {
          categoryCodeId: 5,
          assigneeId: userInfo?.userId,
        },
      ]
      break
    case 'department':
      scheduleRequestBody.assigneeInfos = [
        {
          categoryCodeId: 6,
          assigneeId: userInfo?.departmentId,
        },
      ]
      break
    case 'team':
      scheduleRequestBody.assigneeInfos = [
        {
          categoryCodeId: 7,
          assigneeId: 1,
        },
      ]
      break
  }

  useEffect(() => {
    getSchedule()
  }, [mode])

  function handleStartDate(event: any) {
    setStartDate(event.target.value)
  }

  function handleEndDate(event: any) {
    setEndDate(event.target.value)
  }

  return (
    <div className="flex h-full">
      <ScheduleSideBar />
      <div className="m-10">
        <div>
          <Form className="flex gap-3">
            <TextInput
              id="startDate"
              name="startDate"
              type="datetime-local"
              onChange={handleStartDate}
              value={startDate}
            />
            <TextInput
              id="endDate"
              name="endDate"
              type="datetime-local"
              onChange={handleEndDate}
              value={endDate}
            />
            <Button className="bg-mainGreen" onClick={() => getSchedule()}>
              검색
            </Button>
          </Form>
        </div>
        <div className="mt-8">
          <ScheduleList contents={scheduleList} />
        </div>
      </div>
    </div>
  )
}

export default SchedulePage
