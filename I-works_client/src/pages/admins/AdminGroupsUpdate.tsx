import axios from "axios"
import { ChangeEvent, FormEvent, useEffect, useState } from "react"
import { Form, useNavigate, useParams } from "react-router-dom"
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
  positionCodeName: null,
  positionCodeId: null,
  userTel: string,
  userEmail: string
}

interface GroupType {
  teamId: string
  teamName: string
  teamDescription: string
  teamLeader: number
  teamUsers: {
    teamUserId: number
    userDto: {
      userId: number
      userNameLast: string
      userNameFirst: string
      userEmail: string
      userTel: string
      userPosition: string
      departmentName: string
    }
  }[],
}

function AdminGroupsUpdate() {
  const navigate = useNavigate()
  const { groupId = '' } = useParams<{ groupId: string }>()

  // 그룹 이름, 그룹 설명
  const [teamName, setTeamName] = useState<string>('')
  const [teamDescription, setTeamDescription] = useState<string>()
  const [groupDetail, setgroupDetail] = useState<GroupType>({
    teamId: '',
    teamName: '',
    teamDescription: '',
    teamLeader: 0,
    teamUsers: [],
  })
  const [teamMemberData, setteamMemberData] = useState<UserData[]>([])

  let groupLeaderName = ""
  if (groupDetail.teamLeader) {
    const leader = groupDetail.teamUsers?.find(user => user.userDto.userId == groupDetail.teamLeader);
    if (leader) {
      groupLeaderName = leader.userDto.userNameLast + leader.userDto.userNameFirst + " " + "/" + " " + leader.userDto.departmentName
    }
  }

  const onNameChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTeamName(event.target.value)
  }
  const onDescriptionChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setTeamDescription(event.target.value)
  }
  // 기존 정보 받아오기
  useEffect(() => {
    async function getGroupDetail() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/admin/team/${groupId}`)
        const groupDetailData: GroupType = res.data.data
        setgroupDetail(groupDetailData)
        setTeamName(groupDetailData.teamName)
        setTeamDescription(groupDetailData.teamDescription)
        const teamMembers: UserData[] = groupDetailData.teamUsers?.map((teamUser) => ({
          userId: teamUser.userDto.userId,
          userEid: '',
          userNameFirst: teamUser.userDto.userNameFirst,
          userNameLast: teamUser.userDto.userNameLast,
          departmentName: teamUser.userDto.departmentName,
          departmentId: '',
          positionCodeName: null,
          positionCodeId: null,
          userTel: teamUser.userDto.userTel,
          userEmail: teamUser.userDto.userEmail
        }));
        setteamMemberData(teamMembers);
      }
      catch (err) {
        console.log(err)
      }
    }
    
    getGroupDetail()
  }, [groupId])
  
  const targetIdArray = teamMemberData?.map(user => user.userId)
  const originIdArray = groupDetail?.teamUsers?.map(user => user.userDto.userId);
  const newMembersIds = targetIdArray?.filter(id => !originIdArray.includes(id));

  // 수정 요청
  function handleUpdate(event: FormEvent) {
    event.preventDefault()
    const teamLeader = groupDetail.teamLeader

    axios
      .put(`https://suhyeon.site/api/address/team/${groupId}`, {
        "teamName": teamName,
        "teamLeaderId": teamLeader,
        "teamDescription": teamDescription
      },
        {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          },
        })
      .then((res) => {
        console.log(res.data);
        if (newMembersIds.length > 0) {
          return axios.post(`https://suhyeon.site/api/address/team/user/${groupId}`,
            {
              userIds: newMembersIds
            },
            {
              headers: {
                Authorization: 'Bearer ' + getAccessToken(),
              },
            });
        } else {
          // 빈 프로미스 대신에 아무 값이나 반환하여 프로미스 형태로 반환
          return Promise.resolve(null);
        }
      })
      .then((res) => {
        navigate("../")
        console.log(res?.data); // null이나 다른 값에 대한 응답 확인
      })
      .catch((err) => {
        console.log(err)
      })
  }

  function deleteTeamMember(userId: number) {
    const isConfirmed = window.confirm('정말 삭제하시겠습니까?');
    if (!isConfirmed) {
      return; // 사용자가 취소한 경우 함수를 종료합니다.
    }

    axios
      .delete(`https://suhyeon.site/api/address/team/user/${groupId}`,
        {
          data: { "targetId": userId },
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          },
        })
      .then(() => {
        const updatedTeamMembers = teamMemberData.filter(user => user.userId !== userId);
        setteamMemberData(updatedTeamMembers);
        alert('팀 멤버가 성공적으로 삭제되었습니다.')
      })
      .catch((err) => {
        alert(err.response.data.message)
        console.log(userId)
      });
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

  function removeTeamMember(userId: number) {
    const updatedTeamMembers = teamMemberData.filter(user => user.userId !== userId);
    setteamMemberData(updatedTeamMembers);
  }

  useEffect(() => {
    return () => {
      window.removeEventListener('message', handleMessage2);
    };
  }, []);

  return (
    <div>
      <p className="text-2xl mb-6">그룹 추가</p>
      <Form>
        <div className="flex flex-col my-2">
          <label className="mb-2 text-lg" htmlFor="teamName">그룹명</label>
          <input onChange={onNameChange} className="h-8 w-full" value={teamName} type="text" name="teamName" placeholder="그룹명을 입력해주세요." id="teamName" required />
        </div>
        <div className="flex flex-col my-2">
          <label className="mb-2 text-lg" htmlFor="teamDesc">그룹설명</label>
          <textarea onChange={onDescriptionChange} value={teamDescription} style={{ height: "70px" }} className="h-8 w-full" name="teamDesc" placeholder="설명을 입력해주세요." id="teamDesc" required />
        </div>
        <div className="flex items-center">
          <h3 className="text-lg">그룹 리더 : </h3>
          <span className="ml-1 p-2 bg-mainGray">{groupLeaderName}</span>
        </div>
        <div>
          <div className="flex justify-between items-center text-lg mb-2 mt-2">
            <h3>그룹 멤버</h3>
            <Button className="bg-mainGreen" onClick={openPopup2}>주소록</Button>
          </div>
          {teamMemberData?.length > 0 ? (
            <div className="flex flex-col border-2 h-40 p-4 overflow-auto">
              <ul>
                {teamMemberData.map((user) => (
                  <li className="flex justify-between bg-mainGray p-2 mb-2" key={user.userId}>
                    <div>{user.userNameLast}{user.userNameFirst} / {user.departmentName}</div>
                    {user.userId !== groupDetail.teamLeader && (
                      <button onClick={() => {
                        if (originIdArray.includes(user.userId)) {
                          deleteTeamMember(user.userId);
                        } else {
                          removeTeamMember(user.userId);
                        }
                      }}>
                        <RiDeleteBin2Line size={18} />
                      </button>
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
          <Button onClick={handleUpdate} className="bg-mainGreen" type="submit">수정</Button>
          <Button onClick={() => { navigate(`/address/group/${groupId}`) }} className="bg-mainBlue mr-2">취소</Button>
        </div>
      </Form>
    </div>
  )
}

export default AdminGroupsUpdate;