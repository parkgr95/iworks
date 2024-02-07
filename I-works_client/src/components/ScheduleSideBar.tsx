import { Button } from 'flowbite-react'
import ScheduleCreate, { ScheduleCreateRef } from './ScheduleCreate'
import { useRef } from 'react'
import { Link } from 'react-router-dom'

function ScheduleSideBar() {
  const dialog = useRef<ScheduleCreateRef>(null)

  function handleModal() {
    dialog.current?.open()
  }

  return (
    <div className="flex h-full flex-col items-center border-r-2 m-0 px-3 position-absolute w-72 flex-shrink-0">
      <div className="flex justify-center items-center w-full h-20">
        <ScheduleCreate ref={dialog} />
        <Button className="flex-1 bg-mainGreen" onClick={handleModal}>
          할 일 생성
        </Button>
      </div>
      <div className="w-full my-2 border-b-2 pb-2">
        <ul>
          <li>
            <Link
              to="#"
              className="flex items-center w-full text-mainBlack pl-11"
            >
              전체 할 일
            </Link>
          </li>
          <li>
            <Link
              to="#"
              className="flex items-center w-full text-mainBlack pl-11"
            >
              담당 할 일
            </Link>
          </li>
          <li>
            <Link
              to="#"
              className="flex items-center w-full text-mainBlack pl-11"
            >
              요청 할 일
            </Link>
          </li>
        </ul>
      </div>
    </div>
  )
}

export default ScheduleSideBar
