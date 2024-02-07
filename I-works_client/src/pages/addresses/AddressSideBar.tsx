import { ChangeEvent, useEffect, useState } from "react"
import { Outlet, Link, useNavigate } from "react-router-dom"
import { Button } from "flowbite-react"
import axios from "axios"

interface orginizationType {
  departmentName?: string,
  departmentId?: string,
  teamName?: string,
  teamId?: string,
}

function AddressSideBar() {
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

  // 토글
  const [allOpen, setAllOpen] = useState(true);
  const toggleAllOpen = () => setAllOpen((cur) => !cur)

  const [departmentOpen, setDepartmentOpen] = useState(true);
  const toggleDepartmentOpen = () => setDepartmentOpen((cur) => !cur)

  // // 부서, 팀 받아오기
  const [departmentList, setDepartmentList] = useState<orginizationType[]>([])


  useEffect(() => {
      axios.get('https://suhyeon.site/api/address/department/all')
      .then((res) => {
        setDepartmentList(res.data.data);
      })
      .catch((err) => {
        console.error(err)
      });
  }, []);

  return (
    <div className="flex h-full">
      <div className="flex flex-col items-center border-r-2 m-0 px-3 position-absolute w-72 flex-shrink-0">
        <div className="flex justify-center items-center w-full h-20 border-b-2">
          <Button onClick={moveToCreate} className="h-12 w-full bg-mainBlue text-white">
            <span>그룹 추가</span>
          </Button>
        </div>
        <div className="w-full my-2 border-b-2 pb-2">
          <button onClick={toggleAllOpen} type="button" className="flex items-center w-full p-2 text-base text-gray-900" aria-controls="ropdownAll" data-collapse-toggle="dropdownAll">
            <span className="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap font-semibold">조직도</span>
          </button>
          <ul id="dropdownAll" className={`${allOpen ? '' : 'hidden'} space-y-2`}>
            <li>
              <button onClick={toggleDepartmentOpen} type="button" className="flex items-center w-full pl-8 text-base text-gray-900" aria-controls="dropdownDepartment" data-collapse-toggle="dropdownDepartment">
                <Link to={`/address`} className="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap">A208</Link>
              </button>
              <ul id="dropdownDepartment" className={`${departmentOpen ? '' : 'hidden'} space-y-2`}>
                {departmentList.map((dept) => (
                  <li key={dept.departmentId}>
                    <Link to={`/address/${dept.departmentId}`} className="flex items-center w-full text-mainBlack pl-16 pt-2 text-sm">{dept.departmentName}</Link>
                  </li>
                ))}
              </ul>
            </li>
            <li>
              <Link to={`/address/group`} className="ms-3 pl-8">그룹</Link>
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
              <input onChange={onSearchChange} type="search" id="default-search" className="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-200" placeholder="연락처 검색" required />
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

export default AddressSideBar;
