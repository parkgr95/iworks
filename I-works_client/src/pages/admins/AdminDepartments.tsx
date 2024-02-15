import axios from "axios";
import { useState, useEffect } from "react";
import { Button } from "flowbite-react";
import AdminDepartmentsCreate from "./AdminDepartmentsCreate";
import AdminDepartmentsUpdate from "./AdminDepartmentsUpdate";

interface DepartmentType {
  departmentCreatedAt: string;
  departmentDesc: string;
  departmentId: number;
  departmentIsUsed: boolean;
  departmentLeaderId: number;
  departmentName: string;
  departmentTelFirst: string;
  departmentTelLast: string;
  departmentTelMiddle: string;
  departmentUpdatedAt: string;
}

interface UserType {
  userId: number;
  userEid: string;
  userNameFirst: string;
  userNameLast: string;
  departmentName: string;
  departmentId: string;
  positionCodeName: null;
  positionCodeId: null;
  userTel: string;
  userEmail: string;
}

function AdminDepartments() {
  const [departmentList, setDepartmentList] = useState<DepartmentType[]>([]);
  const [users, setUsers] = useState<UserType[]>([]);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [selectedDepartmentId, setSelectedDepartmentId] = useState<number | null>(null);

  const openModal = (departmentId: number) => {
    setSelectedDepartmentId(departmentId !== 0 ? departmentId : null); // departmentId가 0이 아닐 때만 선택한 departmentId를 설정하고, 그렇지 않을 경우에는 null로 초기화
    setModalIsOpen(true);
  };

  const closeModal = () => {
    setModalIsOpen(false);
  };

  useEffect(() => {
    axios.get(`https://suhyeon.site/api/admin/department/`)
      .then((res) => {
        setDepartmentList(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });

    async function getUsers() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/user/all`);
        setUsers(res.data.data);
      } catch (err) {
        console.log(err);
      }
    }

    getUsers();
  }, []);

  return (
    <>
      <div className="flex justify-between items-center text-2xl font-bold mt-10">
        <h1 className="text-2xl font-bold">부서 관리</h1>
        <Button onClick={() => openModal(0)} className="bg-mainGreen text-white">
          <span>부서 생성</span>
        </Button>
        <AdminDepartmentsCreate
          show={modalIsOpen}
          onClose={closeModal}
        />
      </div>
      <div className="flex justify-between border-2 w- h-[32rem] mt-5">
        <div className="relative overflow-auto w-full">
          <table className="w-full text-sm text-center rtl:text-right text-gray-500">
            <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
              <tr>
                <th scope="col" className="px-6 py-3">
                  부서 이름
                </th>
                <th scope="col" className="px-6 py-3">
                  부서 설명
                </th>
                <th scope="col" className="px-6 py-3">
                  부서장
                </th>
                <th scope="col" className="px-6 py-3">
                  부서 전화번호
                </th>
                <th scope="col" className="px-6 py-3">
                </th>
              </tr>
            </thead>
            <tbody>
              {departmentList.map((department) => {
                const user: UserType | undefined = users.find((user) => user.userId === department.departmentLeaderId);
                return (
                  <tr key={department.departmentId} className=" bg-white border-b hover:bg-gray-100">
                    <th scope="row" className="px-6 py-4 text-gray-900 whitespace-nowrap ">
                      <div className="">
                        {department.departmentName}
                      </div>
                    </th>
                    <td className="px-6 py-4">
                      {department.departmentDesc}
                    </td>
                    <td className="px-6 py-4">
                      {user ? user.userNameLast + user.userNameFirst : 'unKnown'}
                    </td>
                    <td className="px-6 py-4">
                      {department.departmentTelFirst + "-" + department.departmentTelMiddle + "-" + department.departmentTelLast}
                    </td>
                    <td className="w-48 py-4">
                      <div className="flex items-center">
                        <Button onClick={() => openModal(department.departmentId)} className="bg-mainBlue text-white">
                          <span>수정</span>
                        </Button>
                        <span className="ml-4 font-semibold">{ department.departmentIsUsed ? "활성화" : "비활성화" }</span>
                      </div>
                    </td>
                  </tr>
                )
              })}
            </tbody>
          </table>
        </div>
      </div>
      {selectedDepartmentId !== null && (
        <AdminDepartmentsUpdate
          show={modalIsOpen}
          onClose={closeModal}
          departmentId={selectedDepartmentId}
        />
      )}
    </>
  )
}

export default AdminDepartments;