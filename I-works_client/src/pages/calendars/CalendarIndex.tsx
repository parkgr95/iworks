import { useState } from 'react';
import {
	EventApi,
	DateSelectArg,
	EventClickArg,
	EventContentArg,
} from '@fullcalendar/core';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { INITIAL_EVENTS, createEventId } from './event-utils';

interface CalendarState {
	weekendsVisible: boolean;
	currentEvents: EventApi[];
}

function CalendarIndex() {

	const [state, setState] = useState<CalendarState>({
		weekendsVisible: true,
		currentEvents: [],
	});

	// 날짜 선택시 발생하는 함수 ==> 할 일 작성 링크로 변경
	const handleDateSelect = (selectInfo: DateSelectArg) => {
		const title = prompt('Please enter a new title for your event');
		const calendarApi = selectInfo.view.calendar;

		calendarApi.unselect(); // clear date selection

		if (title) {
			calendarApi.addEvent({
				id: createEventId(),
				title,
				start: selectInfo.startStr,
				end: selectInfo.endStr,
				allDay: selectInfo.allDay,
			});
		}
	};

	// 삭제 매커니즘 -- 모달창으로 변경 필요
	const handleEventClick = (clickInfo: EventClickArg) => {
		if (confirm(`Are you sure you want to delete the event '${clickInfo.event.title}'`)) {
			clickInfo.event.remove();
		}
	};

	// 변경이 있을 때, 현재 이벤트 목록을 업데이트해준다
	const handleEvents = (events: EventApi[]) => {
		setState((prevState) => ({
			...prevState,
			currentEvents: events,
		}));
	};

	// 캘린더에 나타낼 문장들
	const renderEventContent = (eventContent: EventContentArg) => (
		<>
			<i>{eventContent.timeText}</i>
			<i>{eventContent.event.title}</i>
		</>
	);

	// 사이드 바 로직
	// const renderSidebarEvent = (event: EventApi) => (
	// 	<li key={event.id}>
	// 		<b>{formatDate(event.start!, { year: 'numeric', month: 'short', day: 'numeric' })}</b>
	// 		<i>{event.title}</i>
	// 	</li>
	// );
	
	return (
		<div className=''>
			<FullCalendar
				plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
				headerToolbar={{
					left: 'prev,next today',
					center: 'title',
					right: 'dayGridMonth,timeGridWeek,timeGridDay',
				}}
				initialView='dayGridMonth'
				editable={true}
				selectable={true}
				selectMirror={true}
				dayMaxEvents={true}
				weekends={state.weekendsVisible}
				initialEvents={INITIAL_EVENTS}
				select={handleDateSelect}
				eventContent={renderEventContent} // custom render function
				eventClick={handleEventClick}
				eventsSet={handleEvents}
			/>
		</div>
	)

	// function renderSidebar() {
	// 	return (
	// 		<div className='demo-app-sidebar'>
	// 			<div className='demo-app-sidebar-section'>
	// 				<h2>Instructions</h2>
	// 				<ul>
	// 					<li>Select dates and you will be prompted to create a new event</li>
	// 					<li>Drag, drop, and resize events</li>
	// 					<li>Click an event to delete it</li>
	// 				</ul>
	// 			</div>
	// 			<div className='demo-app-sidebar-section'>
	// 				<label>
	// 					<input
	// 						type='checkbox'
	// 						checked={state.weekendsVisible}
	// 						onChange={handleWeekendsToggle}
	// 					></input>
	// 					toggle weekends
	// 				</label>
	// 			</div>
	// 			<div className='demo-app-sidebar-section'>
	// 				<h2>All Events ({state.currentEvents.length})</h2>
	// 				<ul>{state.currentEvents.map(renderSidebarEvent)}</ul>
	// 			</div>
	// 		</div>
	// 	);
}

export default CalendarIndex
