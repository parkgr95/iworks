import { useEffect, useState } from 'react'
import {
  EventApi,
  // DateSelectArg,
  EventClickArg,
  EventContentArg,
} from '@fullcalendar/core'
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import axios from 'axios'
import { getAccessToken } from '../../utils/auth'

interface CalendarState {
  weekendsVisible: boolean
  currentEvents: EventApi[]
}

interface ScheduleType {
  scheduleId: string
  scheduleAssigneeId: number
  scheduleAssigneeCategoryId: number
  scheduleAssigneeCategoryName: string
  scheduleDivisionName: string
  scheduleTitle: string
  scheduleStartDate: string
  scheduleEndDate: string
  schedulePriority: string
  scheduleIsFinish: boolean
}

function CalendarIndex() {
  const [scheduleData, setScheduleData] = useState<ScheduleType[]>([])
  const [selectedEvent, setSelectedEvent] = useState<ScheduleType | null>(null)

  useEffect(() => {
    async function getScheduleList() {
      try {
        const res = await axios.post(
          `https://suhyeon.site/api/calender/date`,
          {
            startDate: '2024-02-13T10:00:00',
            endDate: '2024-05-01T18:00:00',
          },
          {
            headers: {
              Authorization: 'Bearer ' + getAccessToken(),
            },
          },
        )
        setScheduleData(res.data.data)
        console.log(res.data.data)
      } catch (err) {
        console.log(err)
      }
    }
    getScheduleList()
    console.log(scheduleData)
  }, [])

  const [state, setState] = useState<CalendarState>({
    weekendsVisible: true,
    currentEvents: [],
  })

  // 삭제 매커니즘 -- 모달창으로 변경 필요
  const handleEventClick = (clickInfo: EventClickArg) => {
    const clickedEvent = scheduleData.find(
      (schedule) => schedule.scheduleId == clickInfo.event.id,
    )
    if (clickedEvent) {
      setSelectedEvent(clickedEvent)
    }
  }

  const handleCloseModal = () => {
    setSelectedEvent(null)
  }

  // 변경이 있을 때, 현재 이벤트 목록을 업데이트해준다
  const handleEvents = (events: EventApi[]) => {
    // 현재 이벤트 상태와 새로운 이벤트 배열이 동일한지 확인
    if (
      !events.every(
        (event, index) => state.currentEvents[index]?.id === event.id,
      )
    ) {
      setState((prevState) => ({
        ...prevState,
        currentEvents: events,
      }))
    }
  }

  // 캘린더에 나타낼 문장들
  const renderEventContent = (eventContent: EventContentArg) => {
    return (
      <div>
        <i className="bg-maingray">{eventContent.event.title}</i>
        {/* <i>{startDate}</i> */}
      </div>
    )
  }

  return (
    <div className="">
      <FullCalendar
        plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
        headerToolbar={{
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay',
        }}
        initialView="dayGridMonth"
        editable={true}
        selectable={true}
        selectMirror={true}
        dayMaxEvents={true}
        weekends={state.weekendsVisible}
        eventClick={handleEventClick}
        eventContent={renderEventContent}
        events={scheduleData.map((schedule: ScheduleType) => ({
          id: schedule.scheduleId,
          title: schedule.scheduleTitle,
          start: schedule.scheduleStartDate,
          end: schedule.scheduleEndDate,
        }))}
        eventsSet={handleEvents}
      />
      <div className="modal-wrapper">
        {selectedEvent && (
          <div className="modal border-2 fixed top-40 left-6 p-4 w-60 text-start">
            <div className="modal-content text-lg">
              <span
                className="close flex justify-between font-semibold items-center cursor-pointer"
                onClick={handleCloseModal}
              >
                <p>할 일 제목</p>&times;
              </span>
              <h2 className="border-b-2 mb-2 pb-2">
                {selectedEvent.scheduleTitle}
              </h2>
              <h2 className="font-semibold mt-2">기간</h2>
              <p className="border-b-2 mb-2 pb-2">
                {selectedEvent.scheduleStartDate.replace('T', ' / ')} ~{' '}
                {selectedEvent.scheduleEndDate.replace('T', ' / ')}
              </p>
              <h2 className="font-semibold mt-2">분류</h2>
              <p className="border-b-2 mb-2 pb-2">
                {selectedEvent.scheduleAssigneeCategoryName}{' '}
                {selectedEvent.scheduleDivisionName}
              </p>
              <h2 className="font-semibold mt-2">중요도</h2>
              <p className="border-b-2 mb-2 pb-2">
                {selectedEvent.schedulePriority}
              </p>
              <h2 className="font-semibold mt-2">상태</h2>
              <p className="">
                {selectedEvent.scheduleIsFinish ? '완료' : '진행중'}
              </p>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}

export default CalendarIndex
