import { useState } from 'react'
import { API_URL } from '../utils/api'
import ScheduleDetail from './ScheduleDetail'
import axios from 'axios'

interface prop {
  contents: any[]
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

function ScheduleList(props: prop) {
  const contents = props.contents
  const [scheduleDetailInfo, setScheduleDetailInfo] =
    useState<ScheduleDetailType>()

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
        setScheduleDetailInfo({ ...detailResponse, scheduleId: id });
      })
      .catch((_error) => {
        alert('스케줄 상세 불러오기 실패')
      })
  }

  return (
    <div className="flex gap-4 items-stretch">
      <div className="flex flex-col gap-2" style={{height:'70vh', overflow: 'scroll' }}>
        {sortedList.map((schedule) => {
          return (
            <div
              className="border-2 p-6 gap-4 border-mainBlue rounded-xl w-full h-fit"
              key={schedule.scheduleId}
              onClick={() => getScheduleDetailInfo(schedule.scheduleId)}
            >
              <div className="text-lg font-bold mb-2 truncate border-b-2 border-b-mainBlue ">
                {schedule.scheduleTitle}
              </div>
              <div className='truncate'>
                시작 일시 : {schedule.scheduleStartDate.slice(0, 4)}년{' '}
                {schedule.scheduleStartDate.slice(5, 7)}월{' '}
                {schedule.scheduleStartDate.slice(8, 10)}일{' '}
                {schedule.scheduleStartDate.slice(11, 13)}시{' '}
                {schedule.scheduleStartDate.slice(14, 16)}분
              </div>
              <div className='truncate'>
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
      <div>
        {scheduleDetailInfo ? (
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
