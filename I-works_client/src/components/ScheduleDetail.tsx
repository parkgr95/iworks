import axios from 'axios'
import { Button } from 'flowbite-react'
import { RiEdit2Line } from 'react-icons/ri'
import { API_URL } from '../utils/api'
import { useState } from 'react'

function ScheduleDetail(props: any) {
  const scheduleDetailInfo = props.scheduleDetailInfo
  const [isFinished, setIsFinished] = useState<boolean>(
    scheduleDetailInfo.scheduleIsFinish,
  )

  function handleFinish(id:number) {
    axios
    .post(API_URL +'/schedule'+`/${id}`+'/isFinish', !isFinished)
    .then((response) => {
      alert(response.data.data)
      setIsFinished(prev => !prev)
      })
      .catch(_error => alert('완료 요청 실패'))
  }

  return (
    <div
      className="flex flex-col gap-8 border-2 border-mainGreen rounded-xl p-16"
      style={{ width:'35vw', height: '75vh', overflow: 'scroll' }}
    >
      <div className="flex text-3xl font-bold pb-2 border-b-2 border-b-mainGreen justify-between">
        {scheduleDetailInfo.scheduleTitle}
        <button className="self-end bg-transparent text-black">
          <RiEdit2Line />
        </button>
      </div>
      <div className="grid grid-cols-2 gap-12">
        <div>
          <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
            시작 일시
          </p>
          {scheduleDetailInfo.scheduleStartDate.slice(0, 4)}년{' '}
          {scheduleDetailInfo.scheduleStartDate.slice(5, 7)}월{' '}
          {scheduleDetailInfo.scheduleStartDate.slice(8, 10)}일{' '}
          {scheduleDetailInfo.scheduleStartDate.slice(11, 13)}시{' '}
          {scheduleDetailInfo.scheduleStartDate.slice(14, 16)}분
        </div>
        <div>
          <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
            종료 일시
          </p>
          {scheduleDetailInfo.scheduleEndDate.slice(0, 4)}년{' '}
          {scheduleDetailInfo.scheduleEndDate.slice(5, 7)}월{' '}
          {scheduleDetailInfo.scheduleEndDate.slice(8, 10)}일{' '}
          {scheduleDetailInfo.scheduleEndDate.slice(11, 13)}시{' '}
          {scheduleDetailInfo.scheduleEndDate.slice(14, 16)}분
        </div>
      </div>
      <div className="">
        <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
          내용
        </p>
        {scheduleDetailInfo.scheduleContent}
      </div>
      <div className="">
        <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
          생성자
        </p>
        {scheduleDetailInfo.scheduleCreatorName}
      </div>
      {isFinished ? <Button onClick={() => handleFinish(scheduleDetailInfo.scheduleId)} className="bg-mainGreen">미완료로 변경</Button> :
      <Button onClick={() => handleFinish(scheduleDetailInfo.scheduleId)} className="bg-mainGreen">완료하기</Button>
       }
    </div>
  )
}

export default ScheduleDetail
