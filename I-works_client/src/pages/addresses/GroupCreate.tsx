import axios from "axios"
import { ChangeEvent, FormEvent, useEffect, useState } from "react"

import { Form, useNavigate } from "react-router-dom"
import { Button } from "flowbite-react"
import { getAccessToken } from "../../utils/auth"
import { useUser } from "../../utils/userInfo"
import { RiDeleteBin2Line } from "react-icons/ri";

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

function GroupCreate() {
  const loginedUser = useUser()
  const navigate = useNavigate()

  // 그룹 이름, 그룹 설명
  const [teamName, setTeamName] = useState<string>('')
  const [teamDescription, setTeamDescription] = useState<string>('')
  const [teamMemberData, setteamMemberData] = useState<UserData[]>([]);

  const onNameChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTeamName(event.target.value)
  }
  const onDescriptionChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setTeamDescription(event.target.value)
  }

  // api 요청
  function handleCreate(event: FormEvent) {
    event.preventDefault()
    const teamLeader = loginedUser?.userId
    const targetIdArray = teamMemberData.map(user => user.userId);
    if (!loginedUser) {
      // 유저 정보가 없을 경우에 대한 처리
      return navigate(`/login`)
    }

    if (teamLeader !== loginedUser.userId) {
      alert("그룹 리더가 로그인한 사용자와 일치하지 않습니다.");
      return;
    }

    axios
      .post("https://suhyeon.site/api/address/team/create", {
        "teamName": teamName,
        "teamLeader": teamLeader,
        "teamDescription": teamDescription
      },
        {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          },
        })
      .then((res) => {
        const teamId = res.data.data.teamId
        if (!teamId) {
          console.log(res)
        }
        // 그룹리더가 아니면 동작 안함 ==> 리더 선택 창은 작성자가 되어야한다
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
        navigate('/address/group')
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
    if (loginedUser) {
      // 로그인한 사용자의 정보만 가진 객체를 생성
      const user: UserData = {
        userId: loginedUser.userId,
        userEid: loginedUser.userEid,
        userNameFirst: loginedUser.userNameFirst,
        userNameLast: loginedUser.userNameLast,
        departmentName: loginedUser.departmentName,
        departmentId: '',
        positionCodeName: null,
        positionCodeId: null,
        userTel: loginedUser.userTel,
        userEmail: loginedUser.userEmail
      };
      // 로그인한 사용자 정보를 팀 멤버 데이터에 추가
      setteamMemberData([user]);
    }
    return () => {
      window.removeEventListener('message', handleMessage2);
    };
  }, [loginedUser]);



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

        <div className="flex items-center">
          <h3 className="text-lg">그룹 리더 : </h3>
          {loginedUser ? (
            <span className="ml-1 p-2 bg-mainGray">{loginedUser.userNameLast}{loginedUser.userNameFirst} / {loginedUser.departmentName}</span>
          ) : (
            <span className="">유저정보 없음</span>
          )}
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
                    {user.userId !== loginedUser?.userId && (
                      <button onClick={() => removeTeamMember(user.userId)}><RiDeleteBin2Line size={18}/></button>
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

export default GroupCreate;