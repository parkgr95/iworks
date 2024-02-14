import { useEffect, useRef, useState } from 'react'
import { API_URL } from '../utils/api'
import ScheduleDetail from './ScheduleDetail'
import axios from 'axios'
import { Checkbox, Dropdown, Label, ToggleSwitch } from 'flowbite-react'
import { ScheduleData } from '../utils/Schedule'

export interface ScheduleDetailType {
  scheduleId: number
  scheduleDivisionName: string
  scheduleTitle: string
  schedulePriority: string
  scheduleContent: string
  scheduleStartDate: string
  scheduleEndDate: string
  scheduleIsFinish: boolean
  scheduleFinishedAt: string
  schedulePlace: string
  meetingDate: string
  meetingSessionId: string
  scheduleCreatorId: number
  scheduleCreatorName: string
  scheduleCreatedAt: string
  scheduleModifierId: number
  scheduleModifierName: string
  scheduleModifiedAt: string
}

const SCH_DEATIL_URL = API_URL + '/schedule'

interface ScheduleListProp {
  startDate: string
  endDate: string
  contents: ScheduleData[]
  getSchedule: (startDate: string, endDate: string) => void
  mode: string
}

function ScheduleList({
  startDate,
  endDate,
  contents,
  getSchedule,
  mode,
}: ScheduleListProp) {
  const [scheduleDetailInfo, setScheduleDetailInfo] =
    useState<ScheduleDetailType>()

  // const [listSort, setListSort] = useState<string>('등록 순')
  const [finishFilter, setFinishFilter] = useState<boolean>(false)

  const [selectedScheduleId, setSelectedScheduleId] = useState<number>()
  
  const sortedList = useRef<ScheduleData[]>()

  // 완료 업무 스타일 지정
  const [finishedStyle, _setFinishedStyle] = useState<string>(
    'text-stone-400 line-through',
    )
    
  // 중복 제거
  const filteredList = contents.filter(
    (obj, index, self) =>
    index === self.findIndex((t) => t.scheduleId === obj.scheduleId),
    )
    
  // 아이디 순 정렬
  sortedList.current = filteredList.slice().sort((a, b) => a.scheduleId - b.scheduleId)



  // 스케줄 상세 내려보내기
  function getScheduleDetailInfo(id: number) {
    axios
      .get(SCH_DEATIL_URL + `/${id}`)
      .then((response) => {
        setScheduleDetailInfo(response.data.data)
        setSelectedScheduleId(id)
      })
      .catch((_error) => {
        alert('스케줄 상세 불러오기 실패')
      })
  }

  function handleFinish(id: number, isFinished: boolean) {
    axios
      .post(API_URL + '/schedule' + `/${id}` + '/isFinish', !isFinished, {
        headers: { 'content-type': 'application/json' },
      })
      .then((_response) => {
        getSchedule(startDate, endDate)
        getScheduleDetailInfo(id)
      })
      .catch((_error) => alert('완료 요청 실패'))
  }



  useEffect(() => {
    setScheduleDetailInfo(undefined)
    setSelectedScheduleId(undefined)
  }, [mode])

  // useEffect(() => {
  //   switch(listSort){
  //     case '등록 순':

  //       sortedList.current = filteredList
  //         .slice()
  //         .sort((a, b) => a.scheduleId - b.scheduleId) 
  //       break
  //     case '기한 순':      
  //       sortedList.current = filteredList
  //         .slice()
  //         .sort((a, b) => - a.scheduleId + b.scheduleId)
  //       break
  //   }
  // },[listSort])



  return (
    <div className="flex gap-4 items-stretch">
      <div className="flex flex-col gap-2" style={{ height: '70vh' }}>
        <div className="flex justify-between px-4">
          {/* <Dropdown label={listSort} inline>
            <Dropdown.Item onClick={()=> setListSort('등록 순')}>등록 순</Dropdown.Item>
            <Dropdown.Item onClick={()=> setListSort('기한 순')} >기한 순</Dropdown.Item>
          </Dropdown> */}
          <div>
            <Checkbox
              id="toggleFinish"
              checked={finishFilter}
              onChange={() => setFinishFilter((prev) => !prev)}
            />
            <Label className="ml-2" htmlFor="toggleFinish">
              완료된 할 일 보기
            </Label>
          </div>
        </div>
        <div
          className="flex flex-col gap-2 h-full"
          style={{ width: '40vw', overflow: 'scroll' }}
        >
          {sortedList.current.length == 0 && (
            <div className="text-center text-xl font-bold border-2 border-mainGreen rounded-xl text-nowrap h-full p-12">
              스케줄이 없습니다.
            </div>
          )}
          {sortedList.current.map((schedule) => {
            return (
              <div
                className={
                  'border-2 p-6 gap-4 border-mainBlue rounded-xl w-full h-fit' +
                  ' ' +
                  (schedule.scheduleIsFinish ? finishedStyle : null) +
                  ' ' +
                  (selectedScheduleId == schedule.scheduleId
                    ? 'bg-mainGray'
                    : null)
                }
                key={schedule.scheduleId}
                onClick={() => getScheduleDetailInfo(schedule.scheduleId)}
                hidden={!finishFilter && schedule.scheduleIsFinish}
              >
                <div className="flex justify-between text-lg font-bold mb-2 truncate border-b-2 border-b-mainBlue ">
                  <span className="self-center">{schedule.scheduleTitle}</span>
                  <ToggleSwitch
                    className="mr-4 my-2 self-center"
                    checked={schedule.scheduleIsFinish}
                    onChange={() =>
                      handleFinish(
                        schedule.scheduleId,
                        schedule.scheduleIsFinish,
                      )
                    }
                  />
                </div>
                <div className="truncate">
                  시작 일시 : {schedule.scheduleStartDate.slice(0, 4)}년{' '}
                  {schedule.scheduleStartDate.slice(5, 7)}월{' '}
                  {schedule.scheduleStartDate.slice(8, 10)}일{' '}
                  {schedule.scheduleStartDate.slice(11, 13)}시{' '}
                  {schedule.scheduleStartDate.slice(14, 16)}분
                </div>
                <div className="truncate">
                  종료 일시 : {schedule.scheduleEndDate.slice(0, 4)}년{' '}
                  {schedule.scheduleEndDate.slice(5, 7)}월{' '}
                  {schedule.scheduleEndDate.slice(8, 10)}일{' '}
                  {schedule.scheduleEndDate.slice(11, 13)}시{' '}
                  {schedule.scheduleEndDate.slice(14, 16)}분
                </div>
              </div>
            )
          })}
        </div>
      </div>
      <div>
        {scheduleDetailInfo && sortedList.current.length ? (
          <ScheduleDetail
            startDate={startDate}
            endDate={endDate}
            getSchedule={getSchedule}
            scheduleDetailInfo={scheduleDetailInfo}
            handleFinish={handleFinish}
            getScheduleDetailInfo={getScheduleDetailInfo}
          />
        ) : (
          <div className="text-center text-xl font-bold border-2 border-mainGreen rounded-xl text-nowrap h-full p-12">
            스케줄을 선택해주세요
          </div>
        )}
      </div>
    </div>
  )
}

export default ScheduleList
