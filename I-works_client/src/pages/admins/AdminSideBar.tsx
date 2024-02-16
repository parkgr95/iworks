import { useState } from "react"
import { Outlet, Link } from "react-router-dom"
import { PiCaretDownThin } from "react-icons/pi"

function BoardSideBar() {
  // 토글
  const [allOpen, setAllOpen] = useState(true);
  const toggleAllOpen = () => setAllOpen((cur) => !cur)

  const [serviceOpen, setserviceOpen] = useState(true);
  const toggleServiceOpen = () => setserviceOpen((cur) => !cur)

  return (
    <div className="flex h-full">
      <div className="flex flex-col items-center border-r-2 px-3 position-absolute w-72 flex-shrink-0">
        <div className="w-full my-2 pb-2 mt-10">
          <button onClick={toggleAllOpen} type="button" className="flex items-center w-full p-2 text-base text-gray-900" aria-controls="ropdownAll" data-collapse-toggle="dropdownAll">
            <div className="flex items-center">
              <span className="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap font-semibold text-xl">구성원</span>
              <PiCaretDownThin className="ml-2" />
            </div>
          </button>
          <ul id="dropdownAll" className={`${allOpen ? '' : 'hidden'} space-y-2`}>
            <li>
              <Link to="/admin/users" className="flex items-center w-full text-mainBlack pl-11 text-lg">구성원 관리</Link>
            </li>
            <li>
              <Link to="/admin/departments" className="flex items-center w-full text-mainBlack pl-11 text-lg">부서 관리</Link>
            </li>
            <li>
              <Link to="/admin/groups" className="flex items-center w-full text-mainBlack pl-11 text-lg">그룹 관리</Link>
            </li>
          </ul>
        </div>
        <div className="w-full my-2">
          <button onClick={toggleServiceOpen} type="button" className="flex items-center w-full p-2 text-base text-gray-900" aria-controls="ropdownAll" data-collapse-toggle="dropdownAll">
            <div className="flex items-center">
              <span className="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap font-semibold text-xl">서비스</span>
              <PiCaretDownThin className="ml-2" />
            </div>
          </button>
          <ul id="dropdownAll" className={`${serviceOpen ? '' : 'hidden'} space-y-2`}>
            <li>
              <Link to="/admin/boards" className="flex items-center w-full text-mainBlack pl-11 text-lg">게시판 관리</Link>
            </li>
            <li>
              <Link to="/admin/schedules" className="flex items-center w-full text-mainBlack pl-11 text-lg">할 일 관리</Link>
            </li>
          </ul>
        </div>
      </div>
      <div className="flex flex-col px-40 pt-4 overflow-auto flex-grow">
        <div className="mb-20">
          <Outlet />
        </div>
      </div>
    </div>
  )
}

export default BoardSideBar;
