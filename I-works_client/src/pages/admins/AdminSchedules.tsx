import axios from "axios"
import { useState, useEffect, FormEvent, useRef } from "react"

import { Button } from "flowbite-react"
import { useNavigate } from "react-router-dom"
import AdminScheduleCreate, { ScheduleCreateRef } from "./AdminScheduleCreate"
import { getAccessToken } from "../../utils/auth"

interface ScheduleType {
  scheduleId: number
  scheduleDivisionName: string
  scheduleTitle: string
  schedulePriority: string
  scheduleContent: string
  scheduleStartDate: string
  scheduleEndDat: string
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
  assigneeList: string[]

}

function AdminSchedules() {
  const dialog = useRef<ScheduleCreateRef>(null)

  function handleModal() {
    dialog.current?.open()
  }

  const [scheduleList, setScheduleList] = useState<ScheduleType[]>([])

  useEffect(() => {
    axios.get(`https://suhyeon.site/api/admin/schedule/`, {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
      .then((res) => {
        setScheduleList(res.data.data)
        console.log(res.data.data)
      })
      .catch((err) => {
        console.log(err)
      })

  }, [])

  const navigate = useNavigate()

  const moveToUpdate = (scheduleId: number) => {
    navigate(`/admin/schedules/update/${scheduleId}`)
  }

  function deleteSchedule(shceduleId: number, event: FormEvent) {
    event.preventDefault()
    const isConfirmed = window.confirm('해당 할 일을 삭제하시겠습니까?');
    if (!isConfirmed) {
      return; // 사용자가 취소한 경우 함수를 종료합니다.
    }
    axios
      .post(`https://suhyeon.site/api/admin/schedule/${shceduleId}/delete`, {}, {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
        }
      })
      .then(() => {
        alert('삭제되었습니다.')
        window.location.reload()
      })
      .catch((err) => {
        console.log(err)
      })
  }

  return (
    <>
      <div className="flex justify-between items-center text-2xl font-bold  mt-10">
        <h1 className="text-2xl font-bold">할 일 관리</h1>
        <AdminScheduleCreate ref={dialog} />
        <Button className="bg-mainGreen" onClick={handleModal}>할 일 생성</Button>
      </div>
      <div className="flex justify-between border-2 w- h-[32rem] mt-5">
        <div className="relative overflow-auto w-full">
          <table className="w-full text-sm text-center rtl:text-right text-gray-500">
            <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
              <tr>
                <th scope="col" className="px-6 py-3">
                  이름
                </th>
                <th scope="col" className="px-6 py-3">
                  내용
                </th>
                <th scope="col" className="px-6 py-3">
                  생성자
                </th>
                <th scope="col" className="px-6 py-3">
                  담당
                </th>
                <th scope="col" className="px-6 py-3">
                </th>
              </tr>
            </thead>
            <tbody>
              {scheduleList.map((schedule) => {

                return (
                  <tr key={schedule.scheduleId} className=" bg-white border-b hover:bg-gray-100">
                    <th scope="row" className="px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white">
                      <div className="">
                        {schedule.scheduleTitle}
                      </div>
                    </th>
                    <td className="px-6 py-4">
                      {schedule.scheduleContent}
                    </td>
                    <td className="px-6 py-4">
                      {schedule.scheduleCreatorName}
                    </td>
                    <td className="flex px-6 py-4 truncate... justify-center items-center">
                    {schedule?.assigneeList.map((assign, index) => (
                      <span key={index} className="pr-1">
                        {assign}
                      </span>
                    ))}
                    </td>
                    <td className="px-6 py-4 w-48">
                      <div className="flex">
                        <Button onClick={() => moveToUpdate(schedule.scheduleId)} className="bg-mainBlue">수정</Button>
                        <Button onClick={(event) => deleteSchedule(schedule.scheduleId, event)} className="bg-rose-700 ml-2">삭제</Button>
                      </div>
                    </td>
                  </tr>
                )
              })}
            </tbody>
          </table>
        </div>
      </div>
    </>
  )
}



export default AdminSchedules;
