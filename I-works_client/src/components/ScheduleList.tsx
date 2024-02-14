import { useState } from 'react'
import { API_URL } from '../utils/api'
import ScheduleDetail from './ScheduleDetail'
import axios from 'axios'
import { Checkbox, Dropdown, Label } from 'flowbite-react'
import { ScheduleData } from '../utils/Schedule'

interface ScheduleListProp {
  contents: ScheduleData[]
}

export interface ScheduleDetailType {
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
  meetingCode: string
  scheduleCreatorId: number
  scheduleCreatorName: string
  scheduleCreatedAt: string
  scheduleModifierId: number
  scheduleModifierName: string
  scheduleModifiedAt: string
}

const SCH_DEATIL_URL = API_URL + '/schedule'

function ScheduleList(props: ScheduleListProp) {
  const contents = props.contents
  const [scheduleDetailInfo, setScheduleDetailInfo] =
    useState<ScheduleDetailType>()

  const [listSort, _setListSort] = useState<string>('등록순')
  const [finishFilter, setFinishFilter] = useState<boolean>(false)


  const [ finishedStyle, _setFinishedStyle ] = useState<string>(' ')


  const filteredList = contents.filter(
    (obj, index, self) =>
      index === self.findIndex((t) => t.scheduleId === obj.scheduleId),
  )

  const sortedList = filteredList
    .slice()
    .sort((a, b) => a.scheduleId - b.scheduleId)

  function getScheduleDetailInfo(id: number) {
    axios
      .get(SCH_DEATIL_URL + `/${id}`)
      .then((response) => {
        const detailResponse = response.data.data
        setScheduleDetailInfo({ ...detailResponse, scheduleId: id })
      })
      .catch((_error) => {
        alert('스케줄 상세 불러오기 실패')
      })
  }

  // function handleToggleFinished() {

  // }

  return (
    <div className="flex gap-4 items-stretch">
      <div className="flex flex-col gap-2" style={{ height: '70vh' }}>
        <div className="flex justify-between px-4">
          <Dropdown label={listSort} inline>
            <Dropdown.Item>등록 순</Dropdown.Item>
            <Dropdown.Item>기한 순</Dropdown.Item>
            <Dropdown.Item>중요 순</Dropdown.Item>
          </Dropdown>
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
        <div className="flex flex-col gap-2 h-full" style={{ overflow:'scroll'}}>
          {sortedList.length == 0 && <div className="text-center text-xl font-bold border-2 border-mainGreen rounded-xl text-nowrap h-full p-12">
            스케줄이 없습니다.
          </div>}
          {sortedList.map((schedule) => {
            return (
              <div
                className={"border-2 p-6 gap-4 border-mainBlue rounded-xl w-full h-fit" + ' ' + finishedStyle}
                key={schedule.scheduleId}
                onClick={() => getScheduleDetailInfo(schedule.scheduleId)}
                hidden={!finishFilter && schedule.scheduleIsFinish}
              >
                <div className="text-lg font-bold mb-2 truncate border-b-2 border-b-mainBlue ">
                  {schedule.scheduleTitle}
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
        {scheduleDetailInfo && sortedList.length ? (
          <ScheduleDetail scheduleDetailInfo={scheduleDetailInfo} />
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
