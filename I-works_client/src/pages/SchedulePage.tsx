
import { Form, useLocation } from 'react-router-dom'
import ScheduleSideBar from '../components/ScheduleSideBar'

import { Button, TextInput } from 'flowbite-react'
import { useEffect, useState } from 'react'
import ScheduleList from '../components/ScheduleList'

import { ScheduleData, getMyScheduleList } from '../utils/Schedule'





function SchedulePage() {
  // 초기 기간 설정
  const [startDate, setStartDate] = useState(() => {
    const currentDate = new Date()
    currentDate.setDate(currentDate.getDate())
    currentDate.setHours(currentDate.getHours() + 9)
    return currentDate.toISOString().slice(0, -8)
  })

  const [endDate, setEndDate] = useState(() => {
    const initialEndDate = new Date()
    initialEndDate.setDate(initialEndDate.getDate() + 30)
    initialEndDate.setHours(initialEndDate.getHours() + 9)
    return initialEndDate.toISOString().slice(0, -8)
  })

  // 파라미터 가져오기
  const param = new URLSearchParams(useLocation().search)
  const [mode, _setMode] = useState<string>(param.get('mode') as string)

  // 기간별 할일 담당자 불러오기 함수

  // 전체 일정 불러오기
  async function getSchedule(startDate: string, endDate: string) {
    try {
      const response = (await getMyScheduleList(
        startDate,
        endDate,
      )) as ScheduleData[]

      switch (mode) {
        case 'all':
          setScheduleList(response)
          break
        case 'user':
          const userSch = response.filter(
            (sch) => sch.scheduleAssigneeCategoryId === 5,
          )
          setScheduleList(userSch)
          break
        case 'department':
          const departmentSch = response.filter(
            (sch) => sch.scheduleAssigneeCategoryId == 6,
          )
          setScheduleList(departmentSch)
          break
        case 'team':
          const teamSch = response.filter(
            (sch) => sch.scheduleAssigneeCategoryId == 7,
          )
          setScheduleList(teamSch)
          break
      }
    } catch {
      alert('스케줄 불러오기 실패')
    }
  }

  useEffect(() => {
    getSchedule(startDate, endDate)

    // switch (mode) {
    //   case 'all':
    //     break
    //   case 'user':
    //     console.log(mode)
    //     const userSch = scheduleList.filter(
    //       (sch) => sch.scheduleAssigneeCategoryId === 5,
    //     )

    //     setScheduleList(userSch)
    //     break
    //   case 'department':
    //     const departmentSch = scheduleList.filter(
    //       (sch) => sch.scheduleAssigneeCategoryId == 6,
    //     )
    //     setScheduleList(departmentSch)
    //     break
    //   case 'team':
    //     const teamSch = scheduleList.filter(
    //       (sch) => sch.scheduleAssigneeCategoryId == 7,
    //     )
    //     setScheduleList(teamSch)
    //     break
    // }
  }, [])

  // 스케줄 리스트 컴포넌트에 prop 할 데이터

  const [scheduleList, setScheduleList] = useState<ScheduleData[]>([])

  // 임시 변수
  // const [allScheduleList, setAllScheduleList] = useState<ScheduleData[]>([])
  // const [userScheduleList, setUserScheduleList] = useState<ScheduleData[]>([])
  // const [deprtmentScheduleList, setDepartmentScheduleList] = useState<
  //   ScheduleData[]
  // >([])
  // const [teamScheduleList, setTeamScheduleList] = useState<ScheduleData[]>([])

  // 모드가 바뀔 때마다 새로 요청
  // useEffect(() => {
  //   switch (mode) {
  //     case 'all':
  //       setScheduleList((_prev) => [...allScheduleList])
  //       break
  //     case 'user':
  //       setScheduleList((_prev) => [...userScheduleList])
  //       break
  //     case 'department':
  //       setScheduleList((_prev) => [...deprtmentScheduleList])
  //       break
  //     case 'team':
  //       setScheduleList((_prev) => [...teamScheduleList])
  //       break
  //   }
  // }, [mode])

  // 검색용 시작 시간 끝나는 시간 설정
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
          <Button
            className="bg-mainGreen"
            onClick={() => getSchedule(startDate, endDate)}
          >
            검색
          </Button>
        </Form>

        <div className="mt-8">
          <ScheduleList contents={scheduleList} />
        </div>
      </div>
    </div>
  )
}

export default SchedulePage
