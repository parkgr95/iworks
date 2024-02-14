import { Button, Modal } from "flowbite-react";
import { useState, useEffect, FormEvent, ChangeEvent } from "react";
import axios from "axios";
import { Form, useNavigate } from "react-router-dom";

interface ModalProps {
  show: boolean;
  onClose: () => void;
}

interface UserData {
  userId: number,
  userEid: string,
  userNameFirst: string,
  userNameLast: string,
  departmentName: string,
  departmentId: string,
  positionCodeName: null,
  positionCodeId: null,
  userTel: string,
  userEmail: string
}

const AdminDepartmentsCreate: React.FC<ModalProps> = ({ show, onClose }) => {
  const navigate = useNavigate()

  const [formData, setFormData] = useState({
    departmentName: '',
    departmentDesc: '',
    departmentTelFirst: '',
    departmentTelMiddle: '',
    departmentTelLast: '',
  });

  const [userAll, setUserAll] = useState<UserData[]>([])
  const [departmentLeader, setdepartmentLeader] = useState<UserData>();
  const { departmentName, departmentDesc, departmentTelFirst, departmentTelMiddle, departmentTelLast } = formData;

  const onInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };
  // 부서장 선택
  const handleDepartmentLeaderSelect = (userId: number) => {
    const selectedLeader = userAll.find(user => user.userId === userId);
    if (selectedLeader) {
      setdepartmentLeader(selectedLeader);
    }
  };

  useEffect(() => {
    axios.get(`https://suhyeon.site/api/admin/user/`)
      .then((res) => {
        setUserAll(res.data.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }, []);

  // api 요청
  function handleCreate(event: FormEvent) {
    event.preventDefault()

    axios
      .post("https://suhyeon.site/api/admin/department/", {
        "departmentLeaderId": departmentLeader?.userId,
        "departmentName": departmentName,
        "departmentDesc": departmentDesc,
        "departmentTelFirst": departmentTelFirst,
        "departmentTelMiddle": departmentTelMiddle,
        "departmentTelLast": departmentTelLast
      })
      .then((res) => {
        navigate("../")
        onClose()
        console.log(res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }


  return (
    <Modal
      show={show}
      onClose={onClose}
      size="lg"
    >
      <h1 className="mt-6 mb-2 pl-8 font-semibold text-lg">부서 만들기</h1>
      <div className="flex flex-col items-center py-10 mx-6 mb-6 border-2">
        <Form className="w-80">
          <div className="flex flex-col my-2">
            <label className="mb-2 text-lg" htmlFor="departmentName">부서 이름</label>
            <input
              onChange={onInputChange}
              className="h-8 w-full"
              type="text"
              name="departmentName"
              value={departmentName} // 상태값을 입력값으로 설정
              placeholder="부서 이름을 입력해주세요."
              id="departmentName"
              required
            />
          </div>
          <div className="flex flex-col my-2">
            <label className="mb-2 text-lg" htmlFor="departmentDesc">부서 설명</label>
            <input
              onChange={onInputChange}
              className="h-8 w-full"
              type="text"
              name="departmentDesc"
              value={departmentDesc} // 상태값을 입력값으로 설정
              placeholder="부서 설명을 입력해주세요."
              id="departmentDesc"
              required
            />
          </div>
          <div className="flex flex-col my-2">
            <label className="mb-2 text-lg" htmlFor="departmentLeader">부서장 선택</label>
            <select
              onChange={(e) => handleDepartmentLeaderSelect(parseInt(e.target.value))}
              className="h-10 w-full"
              name="departmentLeader"
              id="departmentLeader"
              required
            >
              <option value="">부서장을 선택해주세요.</option>
              {userAll.map((user) => (
                <option key={user.userId} value={user.userId}>{user.userNameLast}{user.userNameFirst}</option>
              ))}
            </select>
          </div>
          <span className="text-lg">전화번호</span>
          <div className="flex mt-2">
            <div className="">
              <input
                onChange={onInputChange}
                className="h-8 w-full"
                type="text"
                name="departmentTelFirst"
                value={departmentTelFirst} // 상태값을 입력값으로 설정
                id="departmentTelFirst"
                required
              />
            </div>
            <span className="mx-2">-</span>
            <div className="">
              <input
                onChange={onInputChange}
                className="h-8 w-full"
                type="text"
                name="departmentTelMiddle"
                value={departmentTelMiddle} // 상태값을 입력값으로 설정
                id="departmentTelFirst"
                required
              />
            </div>
            <span className="mx-2">-</span>
            <div className="">
              <input
                onChange={onInputChange}
                className="h-8 w-full"
                type="text"
                name="departmentTelLast"
                value={departmentTelLast} // 상태값을 입력값으로 설정
                id="departmentTelLast"
                required
              />
            </div>
          </div>
          <div className="flex justify-end mt-4">
            <Button onClick={() => { onClose() }} className="bg-mainBlue mr-2">취소</Button>
            <Button onClick={handleCreate} className="bg-mainGreen" type="submit">생성</Button>
          </div>
        </Form>
      </div>
    </Modal>
  );
};

export default AdminDepartmentsCreate;