import axios from 'axios'
import { Button, Select, TextInput, Textarea } from 'flowbite-react'
import { RiEdit2Line } from 'react-icons/ri'
import { API_URL, formDataToRequestData } from '../utils/api'
import { FormEvent, useEffect, useState } from 'react'
import { Form } from 'react-router-dom'
import { RiDeleteBin2Line } from 'react-icons/ri'
import { ScheduleDetailType } from './ScheduleList'

const MEETING_SESSSION_URL = 'https://openvidu.iworks.live/#'

function ScheduleDetail({
  scheduleDetailInfo,
  startDate,
  endDate,
  getSchedule,
  getScheduleDetailInfo,
  setScheduleDetailInfo,
}: any) {
  const [isFinished, setIsFinished] = useState<boolean>()
  const [isEdit, setIsEdit] = useState<boolean>(false)

  useEffect(() => {
    setIsFinished(scheduleDetailInfo.scheduleIsFinish)
  }, [scheduleDetailInfo])

  function handleFinish(id: number) {
    axios
      .post(API_URL + '/schedule' + `/${id}` + '/isFinish', !isFinished, {
        headers: { 'content-type': 'application/json' },
      })
      .then((_response) => {
        setIsFinished((prev) => !prev)
        getSchedule(startDate, endDate)
      })
      .catch((_error) => alert('완료 요청 실패'))
  }

  function toggleEdit() {
    if (isEdit) {
      setFormInfo(scheduleDetailInfo)
    }
    setIsEdit((prev) => !prev)
  }

  const [formInfo, setFormInfo] =
    useState<ScheduleDetailType>(scheduleDetailInfo)

  useEffect(() => {
    setFormInfo(scheduleDetailInfo)
  }, [scheduleDetailInfo])

  function handleInfoChange(key: string, value: string) {
    setFormInfo((prevInfo: any) => ({
      ...prevInfo,
      [key]: value,
    }))
  }

  function handleEditSchedule(event: FormEvent, id: number) {
    event.preventDefault()

    const scheduleEditFormData = new FormData(event.target as HTMLFormElement)
    const scheduleEditRequestData = formDataToRequestData(scheduleEditFormData)
    console.log(id, scheduleEditRequestData)
    axios
      .post(
        API_URL + '/schedule' + `/${id}` + '/update',
        scheduleEditRequestData,
      )
      .then((_response) => {      
        getSchedule(startDate, endDate)
        getScheduleDetailInfo(id)
        setIsEdit((prev) => !prev)
        alert('스케줄 수정 완료')
      })
      .catch((_error) => alert('스케줄 수정 실패'))
  }

  function handleDeleteSchedule(id: number) {
    if (!window.confirm('정말로 삭제하시겠습니까?')) {
      return
    }
    axios
      .post(API_URL + '/schedule' + `/${id}` + '/delete')
      .then((response) => {
        getSchedule(startDate, endDate)
        alert(response.data.data)
        setScheduleDetailInfo([])
      })
      .catch((err) => alert(err.response.data.data))
  }

  function openMeeting(url:string){
    window.open(url)
  }

  return (
    <>
      {!isEdit ? (
        <div
          className="flex flex-col gap-8 border-2 border-mainGreen rounded-xl p-16"
          style={{ height: '70vh', overflow: 'scroll' }}
        >
          <div className="flex text-2xl font-bold pb-2 border-b-2 border-b-mainGreen justify-between">
            {formInfo.scheduleTitle}
            <div className="flex gap-4 self-center">
              <div
                className="text-sm self-center text-nowrap"
                onClick={() => handleFinish(formInfo.scheduleId)}
              >
                {isFinished ? (
                  <span className="text-white bg-green-500 p-2 rounded">
                    완료됨
                  </span>
                ) : (
                  <span className="text-white bg-red-500 p-2 rounded">
                    미완료
                  </span>
                )}
              </div>
              <button
                type="button"
                onClick={() => toggleEdit()}
                className="self-end bg-transparent text-black self-center"
              >
                <RiEdit2Line />
              </button>
              <button
                className="self-center"
                type="button"
                onClick={() => handleDeleteSchedule(formInfo.scheduleId)}
              >
                <RiDeleteBin2Line />
              </button>
            </div>
          </div>
          <div className="grid grid-cols-2 gap-12">
            <div>
              <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
                시작 일시
              </p>
              {formInfo.scheduleStartDate.slice(0, 4)}년{' '}
              {formInfo.scheduleStartDate.slice(5, 7)}월{' '}
              {formInfo.scheduleStartDate.slice(8, 10)}일{' '}
              {formInfo.scheduleStartDate.slice(11, 13)}시{' '}
              {formInfo.scheduleStartDate.slice(14, 16)}분
            </div>
            <div>
              <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
                종료 일시
              </p>
              {formInfo.scheduleEndDate.slice(0, 4)}년{' '}
              {formInfo.scheduleEndDate.slice(5, 7)}월{' '}
              {formInfo.scheduleEndDate.slice(8, 10)}일{' '}
              {formInfo.scheduleEndDate.slice(11, 13)}시{' '}
              {formInfo.scheduleEndDate.slice(14, 16)}분
            </div>
          </div>
          {formInfo.meetingSessionId && (
            <div className="">
              <div className="flex text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen gap-4">
                화상 회의
              <div className="text-xs text-white px-4 py-1 self-center bg-mainGreen rounded" onClick={() => openMeeting(MEETING_SESSSION_URL + formInfo.meetingSessionId)}>         
                    입장
                </div>
              </div>
              <div className="">
                {formInfo.meetingDate.slice(0, 4)}년{' '}
                {formInfo.meetingDate.slice(5, 7)}월{' '}
                {formInfo.meetingDate.slice(8, 10)}일{' '}
                {formInfo.meetingDate.slice(11, 13)}시{' '}
                {formInfo.meetingDate.slice(14, 16)}분
                
              </div>
            </div>
          )}

          <div className="">
            <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
              우선순위
            </p>
            {formInfo.schedulePriority == 'H' && '높음'}
            {formInfo.schedulePriority == 'M' && '중간'}
            {formInfo.schedulePriority == 'L' && '낮음'}
          </div>
          <div className="">
            <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
              내용
            </p>
            {formInfo.scheduleContent}
          </div>
          <div className="">
            <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
              장소
            </p>
            {formInfo.schedulePlace}
          </div>
          <div className="">
            <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
              생성자
            </p>
            {formInfo.scheduleCreatorName}
          </div>
          <Button
            onClick={() => handleFinish(formInfo.scheduleId)}
            className="bg-mainGreen"
          >
            {isFinished ? '미완료로 변경' : '완료하기'}
          </Button>
        </div>
      ) : (
        <Form
          className="flex flex-col gap-8 border-2 border-mainGreen rounded-xl p-16"
          style={{ height: '70vh', overflow: 'scroll' }}
          onSubmit={(event) => handleEditSchedule(event, formInfo.scheduleId)}
        >
          <div className="flex text-3xl font-bold pb-2 border-b-2 border-b-mainGreen justify-between">
            <TextInput
              id="scheduleTitle"
              name="scheduleTitle"
              onChange={(e) =>
                handleInfoChange('scheduleTitle', e.target.value)
              }
              type="text"
              value={formInfo.scheduleTitle}
            />
            <button
              type="button"
              onClick={() => toggleEdit()}
              className="self-end bg-transparent text-black"
            >
              <RiEdit2Line />
            </button>
          </div>
          <div className="grid grid-cols-2 gap-12">
            <div>
              <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
                시작 일시
              </p>
              <TextInput
                id="scheduleStartDate"
                name="scheduleStartDate"
                onChange={(e) =>
                  handleInfoChange('scheduleStartDate', e.target.value)
                }
                type="datetime-local"
                value={formInfo.scheduleStartDate}
              />
            </div>
            <div>
              <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
                종료 일시
              </p>
              <TextInput
                id="scheduleEndDate"
                name="scheduleEndDate"
                onChange={(e) =>
                  handleInfoChange('scheduleEndDate', e.target.value)
                }
                type="datetime-local"
                value={formInfo.scheduleEndDate}
              />
            </div>
          </div>
          {formInfo.meetingDate && <div>
              <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
                회의 일시
              </p>
              <TextInput
                id="meetingDate"
                name="meetingDate"
                onChange={(e) =>
                  handleInfoChange('meetingDate', e.target.value)
                }
                type="datetime-local"
                value={formInfo.meetingDate}
              />
            </div>}
          <div className="">
            <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
              우선순위
            </p>
            <Select id="schedulePriority" name="schedulePriority" required>
              <option value={'H'}>높음</option>
              <option value={'M'}>중간</option>
              <option value={'L'}>낮음</option>
            </Select>
          </div>
          <TextInput
            type="hidden"
            id="scheduleDivisionCodeId"
            name="scheduleDivisionCodeId"
            value={16}
          />
          <div className="">
            <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
              내용
            </p>
            <Textarea
              id="scheduleContent"
              name="scheduleContent"
              onChange={(e) =>
                handleInfoChange('scheduleContent', e.target.value)
              }
              value={formInfo.scheduleContent}
            />
          </div>
          <div className="">
            <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
              장소
            </p>
            <Textarea
              id="schedulePlace"
              name="schedulePlace"
              onChange={(e) =>
                handleInfoChange('schedulePlace', e.target.value)
              }
              value={formInfo.schedulePlace}
            />
          </div>
          <div className="">
            <p className="text-xl font-bold border-b-2 pb-1 mb-1 border-b-mainGreen">
              생성자
            </p>
            {formInfo.scheduleCreatorName}
          </div>
          <Button type="submit" className="bg-mainGreen">
            수정
          </Button>
        </Form>
      )}
    </>
  )
}

export default ScheduleDetail
