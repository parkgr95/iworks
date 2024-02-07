import { ChangeEvent, useState } from "react"
import { Outlet, useNavigate } from "react-router-dom"
import { Button } from "flowbite-react"

function CalendarSideBar() {
  // 네비게이션
  const navigate = useNavigate()
  const moveToCreate = () => {
    navigate(`/address/create`)
  }

  // 검색
  const [seachKeyword, setSearchKeyword] = useState<string>('')
  const onSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchKeyword(event.target.value)
  }
  const moveToSearch = () => {
    navigate('/board/search/' + seachKeyword)
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
        <div className="mb-4 w-60">
          <form>
            <label htmlFor="default-search" className="mb-2 text-sm font-medium text-gray-900 sr-only">Search</label>
            <div className="relative">
              <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                <svg className="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                  <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z" />
                </svg>
              </div>
              <input onChange={onSearchChange} type="search" id="default-search" className="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-200" placeholder="일정 검색" required />
              <Button onClick={moveToSearch} type="submit" className="h-9 w-20 text-white absolute end-2 bottom-2 bg-mainBlue hover:bg-mainGreen  font-medium rounded-lg text-sm py-2">검색</Button>
            </div>
          </form>
        </div>
        <div className="mb-20">
          <Outlet />
        </div>
      </div>
    </div >
  )
}

export default CalendarSideBar;
