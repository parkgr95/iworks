import { Link } from "react-router-dom"

function MainNav() {
  return (
    <nav className="border-gray-200 dark:bg-gray-900">
      <div className="max-full flex flex-wrap items-center justify-between p-4 bg-gray-300">
        <div className="flex items-center space-x-3">
          <span className="self-center text-2xl font-semibold">I-Works</span>
        </div>
        <ul className="flex flex-row font-medium p-4 mt-4">
          <li>
            <Link to="#" className="block py-2 px-3">게시판</Link>
          </li>
          <li>
            <Link to="#" className="block py-2 px-3">채팅</Link>
          </li>
          <li>
            <Link to="#" className="block py-2 px-3">주소록</Link>
          </li>
          <li>
            <Link to="#" className="block py-2 px-3">캘린더</Link>
          </li>
        </ul>
        <div className="flex items-center p-4 mt-4">
          <Link to="#">로그인</Link>
        </div>
      </div>
    </nav>
  )
}

export default MainNav