import { Outlet, useNavigate } from "react-router-dom"
import { Button } from "flowbite-react"

function CalendarSideBar() {
  // 네비게이션
  const navigate = useNavigate()
  const moveToCreate = () => {
    navigate(`#`)
  }

  return (
    <div className="flex h-full">
      <div className="flex flex-col items-center border-r-2 m-0 px-3 position-absolute w-72 flex-shrink-0">
        <div className="flex justify-center items-center w-full h-20">
          <Button onClick={moveToCreate} className="h-12 w-full bg-mainBlue text-white">
            <span>일정 추가</span>
          </Button>
        </div>
      </div>
      <div className="flex flex-col px-40 pt-4 overflow-auto flex-grow">
        <div className="mb-20 mt-6">
          <Outlet />
        </div>
      </div>
    </div >
  )
}

export default CalendarSideBar;
