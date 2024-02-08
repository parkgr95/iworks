import { Button, ListGroup } from 'flowbite-react'
import ScheduleCreate, { ScheduleCreateRef } from './ScheduleCreate'
import { useRef } from 'react'
import { IoMdCheckmarkCircleOutline } from "react-icons/io";
import { RiUserReceived2Line, RiUserShared2Line } from "react-icons/ri";

function ScheduleSideBar() {
  const dialog = useRef<ScheduleCreateRef>(null)

  function handleModal() {
    dialog.current?.open()
  }

  return (
    <div className="flex h-full flex-col items-center border-r-2 m-0 px-3 position-absolute w-56 flex-shrink-0">
      <div className="flex justify-center items-center w-full h-20">
        <ScheduleCreate ref={dialog} />
        <Button className="flex-1 bg-mainGreen" onClick={handleModal}>
          할 일 생성
        </Button>
      </div>
      <div className="w-full my-2 pb-2">
        <ListGroup className='' >
          <ListGroup.Item href="#" icon={IoMdCheckmarkCircleOutline}>            
            전체 할 일
          </ListGroup.Item>
          <ListGroup.Item href='#' icon={RiUserReceived2Line}>
            담당 할 일
          </ListGroup.Item>
          <ListGroup.Item href="#" icon={RiUserShared2Line}>
            요청 할 일
          </ListGroup.Item>
        </ListGroup>
      </div>
    </div>
  )
}

export default ScheduleSideBar
