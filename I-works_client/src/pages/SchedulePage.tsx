import axios from 'axios'
import { Form, useLocation } from 'react-router-dom'
import ScheduleSideBar from '../components/ScheduleSideBar'
import { API_URL } from '../utils/api'
import { Button, TextInput } from 'flowbite-react'
import { useEffect, useState } from 'react'
import ScheduleList from '../components/ScheduleList'
import { AssigneeType } from '../components/ScheduleCreate'

const API_SCH = API_URL + '/schedule-assign/get-by-assignees-and-date'

interface DateConditionType {
  startDate: string
  endDate: string
}

interface ScheduleRequestBody {
  assigneeBelongs: AssigneeType[]
  dateCondition: DateConditionType
}

function SchedulePage() {


  const [startDate, setStartDate] = useState(() => {
    const currentDate = new Date()
    currentDate.setHours(currentDate.getHours() + 9) // 아홉 시간 추가
    return currentDate.toISOString().slice(0, -8) // ISO 문자열로 변환 후 마지막 8자리 잘라내기
  })

  const [endDate, setEndDate] = useState(() => {
    const initialEndDate = new Date()
    initialEndDate.setDate(initialEndDate.getDate() + 7) // 아홉 시간 추가
    initialEndDate.setHours(initialEndDate.getHours() + 9) // 아홉 시간 추가
    return initialEndDate.toISOString().slice(0, -8) // ISO 문자열로 변환 후 마지막 8자리 잘라내기
  })

  const [scheduleList, setScheduleList] = useState<[]>([])

  const param = new URLSearchParams(useLocation().search)
  const mode = param.get('mode')

  let scheduleRequestBody

  switch (mode) {
    case 'all' || null:
      scheduleRequestBody = {
        assigneeBelongs: [
          {
            categoryCodeId: 5,
            assigneeId: 104,
          },
          {
            categoryCodeId: 6,
            assigneeId: 1,
          },
        ],
        dateCondition: {
          startDate,
          endDate,
        },
      }
      break
    case 'user':
      scheduleRequestBody = {
        assigneeBelongs: [
          {
            categoryCodeId: 5,
            assigneeId: 104,
          },
        ],
        dateCondition: {
          startDate,
          endDate,
        },
      }
      break
    case 'department':
      scheduleRequestBody = {
        assigneeBelongs: [
          {
            categoryCodeId: 6,
            assigneeId: 1,
          },
        ],
        dateCondition: {
          startDate,
          endDate,
        },
      }
      break
    case 'team':
      scheduleRequestBody = {
        assigneeBelongs: [
          {
            categoryCodeId: 7,
            assigneeId: 1,
          },
        ],
        dateCondition: {
          startDate,
          endDate,
        },
      }
      break
  }

  function getSchedule() {
    axios
      .post(API_SCH, scheduleRequestBody! as ScheduleRequestBody)
      .then((response) => {
        setScheduleList(response.data.data)
      })
      .catch((_error) => alert('오류'))
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
              type="datetime-local"
              onChange={handleStartDate}
              value={startDate}
            />
            <TextInput
              type="datetime-local"
              onChange={handleEndDate}
              value={endDate}
            />
            <Button className="bg-mainGreen" onClick={() => getSchedule()}>
              검색
            </Button>
          </Form>
        </div>
        <div className='my-10'>
          <ScheduleList contents={scheduleList} />
        </div>
      </div>
    </div>
  )
}

export default SchedulePage

// async function scheduleLoader() {
//   const data1 = {
//     startDate: '2024-01-03T10:00',
//     endDate: '2024-08-01T18:00',
//   }
//   try {
//     const response = await axios.get(API_SCH, {
//       params: { body: data1 },
//     })

//     return response.data
//   } catch (_error) {
//     alert('에러!')
//     return redirect('/')
//   }
// }

// export { scheduleLoader }
