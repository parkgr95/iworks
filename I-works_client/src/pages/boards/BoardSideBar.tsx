import { ChangeEvent, useState } from "react"
import { Outlet, Link, useNavigate } from "react-router-dom"
import { Button } from "flowbite-react"

function BoardSideBar() {
  // 네비게이션
  const navigate = useNavigate()
  const moveToCreate = () => {
    navigate(`/board/create`)
  }
  
  // 검색
  const [seachKeyword, setSearchKeyword] = useState<string>('')
  const onSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchKeyword(event.target.value)
  }
  const moveToSearch = () => {
    navigate('/board/search/' + seachKeyword)
  }
  
  //토글
  const [open, setOpen] = useState(true);
  const toggleOpen = () => setOpen((cur) => !cur)

  return (
    <div className="flex h-full">
      <div className="flex flex-col items-center border-r-2 m-0 px-3 position-absolute w-auto flex-shrink-0">
        <div className="flex justify-center items-center w-full h-20">
          <Button onClick={moveToCreate} className="h-12 w-full bg-mainBlue text-white">
            <span>글쓰기</span>
          </Button>
        </div>
        <div className="flex justify-center items-center w-full h-12 border-b-2 ">
          <ul className="flex justify-center">
            <li className="px-4">최신글</li>
            <li className="px-4">중요</li>
            <li className="px-4">내 게시글</li>
          </ul>
        </div>
        <div className="w-full my-2 border-b-2">
          <button onClick={toggleOpen} type="button" className="flex items-center w-full p-2 text-base text-gray-900" aria-controls="dropdown-example" data-collapse-toggle="dropdown-example">
            <span className="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap">전체게시판</span>
          </button>
          <ul id="dropdown-example" className={`${open ? '' : 'hidden'} py-2 space-y-2`}>
            <li>
              <Link to="#" className="flex items-center w-full p-2 text-mainBlack pl-11">공지게시판</Link>
            </li>
            <li>
              <Link to="#" className="flex items-center w-full p-2 text-mainBlack pl-11">자유게시판</Link>
            </li>
            <li>
              <Link to="#" className="flex items-center w-full p-2 text-mainBlack pl-11">부서게시판</Link>
            </li>
            <li>
              {/* 토글 + 맵함수로 추가 필요 */}
              <Link to="#" className="flex items-center w-full p-2 text-mainBlack pl-11">그룹게시판</Link>
            </li>
          </ul>
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
              <input onChange={onSearchChange} type="search" id="default-search" className="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-200" placeholder="게시글 검색" required />
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

export default BoardSideBar;
