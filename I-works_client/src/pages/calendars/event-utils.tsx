import { EventInput } from '@fullcalendar/core'

// api의 역할
let eventGuid = 0
const todayStr = new Date().toISOString().replace(/T.*$/, '') // YYYY-MM-DD of today

export const INITIAL_EVENTS: EventInput[] = [
  {
    id: createEventId(),
    title: 'All-day event',
    start: todayStr
  },
  {
    id: createEventId(),
    title: 'Timed event',
    start: todayStr + 'T12:00:00'
  }
]

export function createEventId() {
  return String(eventGuid++)
}


// // 서버에서 가져온 일정 데이터를 기반으로 초기 이벤트 배열을 생성하는 함수
// async function fetchServerEvents(): Promise<EventInput[]> {
//   try {
//     // 서버 API 호출 등을 통해 일정 데이터를 가져옴
//     const response = await fetch('/api/events');
//     const data = await response.json();

//     // 가져온 데이터를 이벤트 배열로 변환
//     return data.map((event: any) => ({
//       id: event.id,
//       title: event.title,
//       start: event.start,
//       // 필요에 따라 다른 이벤트 속성들을 매핑
//     }));
//   } catch (error) {
//     console.error('Error fetching events from the server:', error);
//     return [];
//   }
// }

// // 초기 이벤트 배열을 생성하는 함수
// export async function getInitialEvents(): Promise<EventInput[]> {
//   // 서버에서 가져온 일정 데이터
//   const serverEvents = await fetchServerEvents();

//   // 서버에서 가져온 데이터와 초기 이벤트를 합침
//   const initialEvents: EventInput[] = [
//     ...serverEvents,
//     {
//       id: createEventId(),
//       title: 'Default Event',
//       start: new Date().toISOString(),
//     },
//   ];

//   return initialEvents;
// }
