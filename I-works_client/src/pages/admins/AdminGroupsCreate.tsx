import axios from "axios"
import { ChangeEvent, FormEvent, useEffect, useState } from "react"

import { Form, useNavigate } from "react-router-dom"
import { Button } from "flowbite-react"
import { getAccessToken } from "../../utils/auth"
import { RiDeleteBin2Line } from "react-icons/ri";

interface UserData {
  userId: number,
  userEid: string,
  userNameFirst: string,
  userNameLast: string,
  departmentName: string,
  departmentId: string,
  positionCodeName: string,
  positionCodeId: string,
  userTel: string,
  userEmail: string
}

function AdminGroupsCreate() {
  const navigate = useNavigate()

  // 그룹 이름, 그룹 설명
  const [teamName, setTeamName] = useState<string>('')
  const [teamDescription, setTeamDescription] = useState<string>('')
  const [teamMemberData, setteamMemberData] = useState<UserData[]>([]);
  const [groupLeader, setGroupLeader] = useState<UserData>()

  const onNameChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTeamName(event.target.value)
  }
  const onDescriptionChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setTeamDescription(event.target.value)
  }

  // api 요청
  function handleCreate(event: FormEvent) {
    event.preventDefault()
    const teamLeader = groupLeader?.userId
    const targetIdArray = teamMemberData.map(user => user.userId);

    axios
      .post("https://suhyeon.site/api/admin/team/", {
        "teamName": teamName,
        "teamLeader": teamLeader,
        "teamDescription": teamDescription
      })
      .then((res) => {
        const teamId = res.data.data.teamId
        if (!teamId) {
          alert("팀ID가 존재하지 않습니다.")
        }
        return axios.post(`https://suhyeon.site/api/address/team/user/${teamId}`,
          {
            userIds: targetIdArray
          },
          {
            headers: {
              Authorization: 'Bearer ' + getAccessToken(),
            },
          });
      })
      .then((res) => {
        navigate('/admin/groups')
        console.log(res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }
  // 팝업에서 선택된 사용자를 기존의 팀 멤버 데이터에 추가하는 함수
  const addSelectedUsersToTeamMembers = (selectedUsers: UserData[]) => {
    // 선택된 사용자와 기존의 팀 멤버 데이터를 병합하여 새로운 배열 생성
    const updatedTeamMembers = [...teamMemberData, ...selectedUsers];
    // 중복된 사용자를 제거하여 최종 팀 멤버 데이터 업데이트
    const uniqueTeamMembers = updatedTeamMembers.filter((user, index, self) =>
      index === self.findIndex((u) => (
        u.userId === user.userId
      ))
    );
    setteamMemberData(uniqueTeamMembers);
  };

  function removeTeamMember(userId: number) {
    const updatedTeamMembers = teamMemberData.filter(user => user.userId !== userId);
    setteamMemberData(updatedTeamMembers);
  }

  // 팝업창 로직
  function openPopup2() {
    const popup: Window | null = window.open('/popup/address/select', '유저 선택', 'width=800,height=700,top=100');
    if (popup) {
      popup.addEventListener('beforeunload', () => {
        window.removeEventListener('message', handleMessage2);
      });
      window.postMessage({ type: 'OPEN_POPUP', payload: {} }, window.location.href);
      window.addEventListener('message', handleMessage2);
    } else {
      console.error('팝업을 열 수 없습니다.');
    }
  }

  const handleMessage2 = (event: MessageEvent) => {
    const { type, payload } = event.data;
    if (type === 'SEND_DATA') {
      // 부모 창에서 전달받은 데이터 처리
      addSelectedUsersToTeamMembers(payload.selectedUsers);
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

    return () => {
      window.removeEventListener('message', handleMessage2);
    };
  }, []);

  // 그룹 리더 선택
  const [userAll, setUserAll] = useState<UserData[]>([])

  const handleGroupLeaderSelect = (userId: number) => {
    const selectedLeader = userAll.find(user => user.userId === userId);
    if (selectedLeader) {
      setGroupLeader(selectedLeader)
      setteamMemberData([selectedLeader])
    }
  };

  return (
    <div>
      <p className="text-2xl mb-6">그룹 추가</p>
      <Form>
        <div className="flex flex-col my-2">
          <label className="mb-2 text-lg" htmlFor="teamName">그룹명</label>
          <input onChange={onNameChange} className="h-8 w-full" type="text" name="teamName" placeholder="그룹명을 입력해주세요." id="teamName" required />
        </div>
        <div className="flex flex-col my-2">
          <label className="mb-2 text-lg" htmlFor="teamDesc">그룹설명</label>
          <textarea onChange={onDescriptionChange} style={{ height: "70px" }} className="h-8 w-full" name="teamDesc" placeholder="설명을 입력해주세요." id="teamDesc" required />
        </div>

        <div className="flex flex-col my-2">
          <label className="mb-2 text-lg" htmlFor="departmentLeader">그룹 리더</label>
          <select
            onChange={(e) => handleGroupLeaderSelect(parseInt(e.target.value))}
            className="h-10 w-full"
            name="departmentLeader"
            id="departmentLeader"
            required
          >
            <option value="">그룹 리더를 선택해주세요..</option>
            {userAll.map((user) => (
              <option key={user.userId} value={user.userId}>{user.userNameLast}{user.userNameFirst}</option>
            ))}
          </select>
        </div>
        <div>
          <div className="flex justify-between items-center text-lg mb-2 mt-2">
            <h3>그룹 멤버</h3>
            <Button className="bg-mainGreen" onClick={openPopup2}>주소록</Button>
          </div>
          {teamMemberData.length > 0 ? (
            <div className="flex flex-col border-2 h-40 p-4 overflow-auto">
              <ul>
                {teamMemberData.map((user) => (
                  <li className="flex justify-between bg-mainGray p-2 mb-2" key={user.userId}>
                    <div>{user.userNameLast}{user.userNameFirst} / {user.departmentName}</div>
                    {user.userId !== groupLeader?.userId && (
                      <button onClick={() => removeTeamMember(user.userId)}><RiDeleteBin2Line size={18} /></button>
                    )}
                  </li>
                ))}
              </ul>
            </div>
          ) : (
            <div className="border-2 h-40"></div>
          )}
        </div>
        <div className="flex justify-end mt-4">
          <Button className="bg-mainBlue mr-2">취소</Button>
          <Button onClick={handleCreate} className="bg-mainGreen" type="submit">추가</Button>
        </div>
      </Form>
    </div>
  )
}

export default AdminGroupsCreate;