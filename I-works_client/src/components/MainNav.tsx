import { Link, useNavigate } from "react-router-dom"
import { FaRegCircleUser } from "react-icons/fa6";
import { useState } from "react";
import { useUser } from "../utils/userInfo";

function MainNav() {
  const loginedUser = useUser()
  const navigate = useNavigate()
  const [isDropdownOpen, setIsDropdownOpen] = useState(false)

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  }

  const closeDropdown = () => {
    setIsDropdownOpen(false);
  }

  function handleLogout() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    return navigate('/user/login');
  }
  
  return (
    <nav className="max-full flex justify-between p-4 bg-mainBlue h-14">
      <div className="flex items-center space-x-3">
        <span onClick={() => { navigate("/board") }} className="self-center text-2xl cursor-pointer text-white font-semibold">I-Works</span>
      </div>
      <ul className="flex items-center font-semibold p-3.5 text-white">
        <li>
          <Link to="/board" className="block py-2 px-4">게시판</Link>
        </li>
        <li>
          <Link to="#" className="block py-2 px-4">채팅</Link>
        </li>
        <li>
          <Link to="/address" className="block py-2 px-4">주소록</Link>
        </li>
        <li>
          <Link to="#" className="block py-2 px-4">캘린더</Link>
        </li>
        <li>
          <Link to="/schedule" className="block py-2 px-4">할 일</Link>
        </li>
      </ul>
      <div className="flex items-center md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
        <div className="relative" onClick={toggleDropdown}>
          <button
            type="button"
            className="flex text-sm bg-gray-800 rounded-full md:me-0 focus:ring-4 focus:ring-gray-300"
            id="user-menu-button"
            aria-expanded={isDropdownOpen}
          >
            <FaRegCircleUser size={35} color="white" />
          </button>
          {/* Dropdown menu */}
          {isDropdownOpen && (
            <div
              className="z-4- absolute right-0 mt-3 w-48 text-base list-none bg-white divide-y divide-gray-100 rounded-lg shadow dark:bg-gray-700 dark:divide-gray-600"
              id="user-dropdown"
              onClick={closeDropdown}
            >
              {/* Dropdown content */}
              <div className="px-4 py-3">
                <span className="block text-sm text-gray-900 dark:text-white">{loginedUser?.userNameLast}{loginedUser?.userNameFirst}</span>
                <span className="block text-sm text-gray-500 truncate dark:text-gray-400">사원번호 : {loginedUser?.userEid}</span>
              </div>
              <ul className="py-2" aria-labelledby="user-menu-button">
                <li>
                  <Link to="/user/mypage" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">마이페이지</Link>
                </li>
                <li>
                  <button onClick={handleLogout} className="block w-full text-start px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">로그아웃</button>
                </li>
                <li>
                  <Link to="/admin" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Admin</Link>
                </li>
              </ul>
            </div>
          )}
        </div>
      </div>
    </nav>
  )
}

export default MainNav