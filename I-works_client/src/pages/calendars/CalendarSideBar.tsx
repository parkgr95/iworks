import { Outlet } from 'react-router-dom'

function CalendarSideBar() {
  // 네비게이션

  return (
    <div className="flex h-full">
      <div className="flex flex-col items-center border-r-2 m-0 px-3 position-absolute w-72 flex-shrink-0"></div>
      <div className="flex flex-col px-40 pt-4 overflow-auto flex-grow">
        <div className="mb-20 mt-6">
          <Outlet />
        </div>
      </div>
    </div>
  )
}

export default CalendarSideBar
