import axios from 'axios'
import { redirect } from 'react-router-dom'
import ScheduleSideBar from '../components/ScheduleSideBar'

const API_SCH = 'https://suhyeon.site/api/schedule-assign/102/task/date'

function SchedulePage() {


  return (
    <div className='flex h-full'>
      <ScheduleSideBar />
      <div>
        <h1>할일 페이지</h1>
      </div>
    </div>
  )
}

export default SchedulePage

async function scheduleLoader() {
  const data1 = {
    startDate: '2024-01-03T10:00',
    endDate: '2024-08-01T18:00',
  }
  try {
    const response = await axios.get(API_SCH, {
      params: { body: data1 },
    })

    return response.data
  } catch (_error) {
    alert('에러!')
    return redirect('/')
  }
}

export { scheduleLoader }