import axios from "axios";
import { useState, useEffect, FormEvent, ChangeEvent } from "react";
import { Button, Modal } from "flowbite-react";
import { Form, useNavigate } from "react-router-dom";
import { getAccessToken } from "../../utils/auth";

interface ModalProps {
  show: boolean;
  onClose: () => void;
  departmentId: number;
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

const AdminDepartmentsUpdate: React.FC<ModalProps> = ({ show, onClose, departmentId }) => {
  const navigate = useNavigate()

  const [formData, setFormData] = useState({
    departmentName: '',
    departmentDesc: '',
    departmentTelFirst: '',
    departmentTelMiddle: '',
    departmentTelLast: '',
    departmentLeaderId: '',
  });

  const [userAll, setUserAll] = useState<UserData[]>([])
  const [departmentLeaderId, setDepartmentLeaderId] = useState<string>(''); // 수정된 부분
  const { departmentName, departmentDesc, departmentTelFirst, departmentTelMiddle, departmentTelLast } = formData;

  const onInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  useEffect(() => {
    axios.get(`https://suhyeon.site/api/admin/user/`, {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
      .then((res) => {
        setUserAll(res.data.data)
      })
      .catch((err) => {
        console.log(err)
      })

    axios.get(`https://suhyeon.site/api/admin/department/${departmentId}`, {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
      .then((res) => {
        setFormData({
          departmentName: res.data.data.departmentName,
          departmentDesc: res.data.data.departmentDesc,
          departmentTelFirst: res.data.data.departmentTelFirst,
          departmentTelMiddle: res.data.data.departmentTelMiddle,
          departmentTelLast: res.data.data.departmentTelLast,
          departmentLeaderId: String(res.data.data.departmentLeaderId),
        });
        setDepartmentLeaderId(String(res.data.data.departmentLeaderId)); // 수정된 부분
      })
      .catch((err) => {
        console.log(err)
      })
  }, [departmentId]);

  function handleUpdate(event: FormEvent) {
    event.preventDefault()

    axios
      .put(`https://suhyeon.site/api/admin/department/${departmentId}`, {
        "departmentLeaderId": departmentLeaderId,
        "departmentName": departmentName,
        "departmentDesc": departmentDesc,
        "departmentTelFirst": departmentTelFirst,
        "departmentTelMiddle": departmentTelMiddle,
        "departmentTelLast": departmentTelLast,
        "departmentIsUsed": 1
      }, {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
        },
      })
      .then((res) => {
        navigate("/admin/departments")
        window.location.reload()
        onClose()
        console.log(res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  function handleDelete(event: FormEvent) {
    event.preventDefault()

    axios
      .put(`https://suhyeon.site/api/admin/department/${departmentId}`, {
        "departmentLeaderId": departmentLeaderId,
        "departmentName": departmentName,
        "departmentDesc": departmentDesc,
        "departmentTelFirst": departmentTelFirst,
        "departmentTelMiddle": departmentTelMiddle,
        "departmentTelLast": departmentTelLast,
        "departmentIsUsed": 0
      }, {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
        },
      })
      .then((res) => {
        navigate("/admin/departments")
        window.location.reload()
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
      <h1 className="mt-6 mb-2 pl-8 font-semibold text-lg">부서 수정하기</h1>
      <div className="flex flex-col items-center py-10 mx-6 mb-6 border-2">
        <Form className="w-80">
          <div className="flex flex-col my-2">
            <label className="mb-2 text-lg" htmlFor="departmentName">부서 이름</label>
            <input
              onChange={onInputChange}
              className="h-8 w-full"
              type="text"
              name="departmentName"
              value={departmentName}
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
              value={departmentDesc}
              placeholder="부서 설명을 입력해주세요."
              id="departmentDesc"
              required
            />
          </div>
          <div className="flex flex-col my-2">
            <label className="mb-2 text-lg" htmlFor="departmentLeader">부서장 선택</label>
            <select
              onChange={(e) => setDepartmentLeaderId(e.target.value)}
              className="h-10 w-full"
              name="departmentLeader"
              id="departmentLeader"
              value={departmentLeaderId}
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
                value={departmentTelFirst}
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
                value={departmentTelMiddle}
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
                value={departmentTelLast}
                id="departmentTelLast"
                required
              />
            </div>
          </div>
          <div className="flex justify-end mt-4">
            <Button onClick={handleUpdate} className="bg-mainGreen mr-2" type="submit">수정</Button>
            <Button onClick={handleDelete} className="bg-rose-700  mr-2" type="submit">삭제</Button>
            <Button onClick={() => { onClose() }} className="bg-mainBlue">취소</Button>
          </div>
        </Form>
      </div>
    </Modal>
  );
};

export default AdminDepartmentsUpdate;