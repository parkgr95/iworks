import { useRef } from 'react'
import ScheduleCreate, { ScheduleCreateRef } from '../components/ScheduleCreate'
import { Button } from 'flowbite-react'

function SchedulePage() {
  const dialog = useRef<ScheduleCreateRef>(null)

  function handleModal() {
    dialog.current?.open()
  }

  return (
    <>
      <h1>할 일 페이지</h1>
      <ScheduleCreate ref={dialog} />
      <Button onClick={handleModal}>할 일 생성</Button>
    </>
  )
}

export default SchedulePage
