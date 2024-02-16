import { Form } from "react-router-dom"

function AdminNav() {
  
  return (
    <nav className="max-full flex justify-between p-4 bg-mainGray h-14">
      <div className="flex items-center space-x-3">
        <span className="self-center text-2xl text-black font-semibold">I-Works Admin</span>
      </div>
      <div className="flex items-center text-black p-3.5">
        <Form action="/user/logout" method="post">
          <button className="font-semibold">로그아웃</button>
        </Form>
      </div>
    </nav>
  )
}

export default AdminNav