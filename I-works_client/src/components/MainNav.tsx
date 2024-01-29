import { Link } from "react-router-dom"

function MainNav() {
  
  return (
    <nav className="max-full flex justify-between p-4 bg-mainBlue h-14">
      <div className="flex items-center space-x-3">
        <span className="self-center text-2xl text-white font-semibold">I-Works</span>
      </div>
      <ul className="flex items-center font-semibold p-3.5 text-white">
        <li>
          <Link to="/board" className="block py-2 px-4">게시판</Link>
        </li>
        <li>
          <Link to="#" className="block py-2 px-4">채팅</Link>
        </li>
        <li>
          <Link to="#" className="block py-2 px-4">주소록</Link>
        </li>
        <li>
          <Link to="#" className="block py-2 px-4">캘린더</Link>
        </li>
      </ul>
      <div className="flex items-center text-white p-3.5">
        <Link to="#" className="font-semibold">로그아웃</Link>
      </div>
    </nav>
  )
}

export default MainNav