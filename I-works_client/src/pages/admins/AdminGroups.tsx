import axios from "axios";
import { useState, useEffect, FormEvent } from "react";
import { Button } from "flowbite-react";
import { useNavigate } from "react-router";
import { getAccessToken } from "../../utils/auth";

interface GroupType {
  teamId: number,
  teamName: string
  teamLeader: number,
  teamDescription: string
  teamCreator: number,
  teamCreatedAt: string
  teamUpdatedAt: string
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
  const navigate = useNavigate()
  const [groupList, setGroupList] = useState<GroupType[]>([]);
  const [users, setUsers] = useState<UserType[]>([]);

  useEffect(() => {
    axios.get(`https://suhyeon.site/api/admin/team/all`, {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    }).then((res) => {
        setGroupList(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });

    async function getUsers() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/user/all`, {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          },
        });
        setUsers(res.data.data);
      } catch (err) {
        console.log(err);
      }
    }

    getUsers();
  }, []);

  function deleteGroup(teamId:number, event: FormEvent) {
    event.preventDefault()
    const isConfirmed = window.confirm('그룹을 삭제하시겠습니까?');
    if (!isConfirmed) {
      return; // 사용자가 취소한 경우 함수를 종료합니다.
    }
    axios.delete(`https://suhyeon.site/api/admin/team/${teamId}`, {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
      .then(() => {
        alert('삭제되었습니다.')
        window.location.reload()
      })
      .catch((err) => {
        console.log(err)
      })
  }
  return (
    <>
      <div className="flex justify-between items-center text-2xl font-bold mt-10">
        <h1 className="text-2xl font-bold">그룹 관리</h1>
        <Button onClick={() => {navigate("/admin/groups/create")}} className="bg-mainGreen text-white">
          <span>그룹 생성</span>
        </Button>
      </div>
      <div className="flex justify-between border-2 w- h-[32rem] mt-5">
        <div className="relative overflow-auto w-full">
          <table className="w-full text-sm text-center rtl:text-right text-gray-500">
            <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
              <tr>
                <th scope="col" className="px-6 py-3">
                  그룹 이름
                </th>
                <th scope="col" className="px-6 py-3">
                  그룹 설명
                </th>
                <th scope="col" className="px-6 py-3">
                  그룹 리더
                </th>
                <th scope="col" className="px-6 py-3">
                </th>
              </tr>
            </thead>
            <tbody>
              {groupList.map((group) => {
                const user: UserType | undefined = users.find((user) => user.userId === group.teamLeader);
                return (
                  <tr key={group.teamId} className=" bg-white border-b hover:bg-gray-100">
                    <th scope="row" className="px-6 py-4 text-gray-900 whitespace-nowrap ">
                      <div className="">
                        {group.teamName}
                      </div>
                    </th>
                    <td className="px-6 py-4">
                      {group.teamDescription}
                    </td>
                    <td className="px-6 py-4">
                      {user ? user.userNameLast + user.userNameFirst : 'unKnown'}
                    </td>
                    <td className="w-48 py-4">
                      <div className="flex items-center justify-center">
                        <Button onClick={() => {navigate(`/admin/groups/update/${group.teamId}`)}} className="bg-mainBlue text-white">
                          <span>수정</span>
                        </Button>
                        <Button onClick={(event) => deleteGroup(group.teamId, event)} className="bg-rose-700 ml-2">삭제</Button>
                      </div>
                    </td>
                  </tr>
                )
              })}
            </tbody>
          </table>
        </div>
      </div>
    </>
  )
}

export default AdminDepartments;