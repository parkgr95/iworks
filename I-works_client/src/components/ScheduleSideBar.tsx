import { Button } from 'flowbite-react'
import ScheduleCreate, { ScheduleCreateRef } from './ScheduleCreate'
import { useRef } from 'react'
import { RiBuilding4Line, RiUser3Line, RiGroupLine, RiListCheck } from "react-icons/ri";




const listStyle = "flex p-3 pl-6 gap-2"

function ScheduleSideBar(props:any) {

  const handleMode = props.handleMode
  const currentMode = props.currentMode

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
      <div className="w-full my-2 p-2">
        <ul className='rounded-lg overflow-hidden border border-black' >
          <li onClick={()=>handleMode('all')} className={listStyle + ' ' + (currentMode === "all" ?'text-white bg-mainGreen' : "border-b border-black")}>
            <RiListCheck className='self-center'/>전체 할 일
          </li>
          <li onClick={()=>handleMode('user')} className={listStyle + ' ' + (currentMode === "user" ?'text-white bg-mainGreen' : "border-b border-black")}>
            <RiUser3Line className='self-center' />내 할 일
          </li>
          <li onClick={()=>handleMode('department')} className={listStyle + ' ' + (currentMode === "department" ?'text-white bg-mainGreen' :"border-b border-black")}>
            <RiBuilding4Line className='self-center'/>부서 할 일
          </li>
          <li onClick={()=>handleMode('team')} className={listStyle + ' ' + (currentMode === "team" ?'text-white bg-mainGreen' : "")}>            
            <RiGroupLine className='self-center' />팀 할 일
          </li>
        </ul>
      </div>
    </div>
  )
}

export default ScheduleSideBar
