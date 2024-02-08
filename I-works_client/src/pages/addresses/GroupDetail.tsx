import axios from "axios"
import { Button } from "flowbite-react"
import { FormEvent, useEffect, useState } from "react"
import { useParams, useNavigate } from "react-router-dom"
import { getAccessToken } from "../../utils/auth"



interface GroupType {
  teamId: string
  teamName: string
  teamDescription: string
  teamLeader: string
  teamUsers: {
    teamUserId: string
    userDto: {
      userNameLast: string
      userNameFirst: string
      userEmail: string
      userTel: string
      userPosition: string
      departmentName: string
    }
  }[],
}

function GroupDetail() {
  const { groupId = '' } = useParams<{ groupId: string }>()
  const [groupDetail, setgroupDetail] = useState<GroupType>({
    teamId: '',
    teamName: '',
    teamDescription: '',
    teamLeader: '',
    teamUsers: [],
  })
  const navigate = useNavigate()

  useEffect(() => {
    // 상세 페이지 정보 받아오기
    async function getGroupDetail() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/team/${groupId}`)
        setgroupDetail(res.data.data)
      }
      catch (err) {
        console.log(err)
      }
    }

    getGroupDetail()
  }, [groupId])

  const moveToUpdate = () => {
    navigate('/address/group/update/' + groupId)
  }

  function deleteGroup(event: FormEvent) {
    event.preventDefault()

    axios
      .delete(`https://suhyeon.site/api/address/team/${groupId}`, {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
        },
      })
      .then(() => {
        alert('삭제되었습니다.')
        navigate('/address/group')
      })
      .catch((err) => {
        console.log(err)
      })
  }

  let groupLeaderName = ""
  if (groupDetail.teamLeader) {
    const leader = groupDetail.teamUsers.find(user => user.teamUserId === groupDetail.teamLeader);
    if (leader) {
      groupLeaderName = leader.userDto.userNameLast + leader.userDto.userNameFirst;
    }
  }

  return (
    <div className="flex flex-col">
      <div className="h-10 mb-4 text-sm text-gray-500 border-b-2">
        <p>그룹 상세 정보</p>
      </div>
      <div className="flex justify-between items-center mb-4">
        <p className="text-3xl font-semibold">{groupDetail.teamName}</p>
      </div>
      <div>
        <h1 className="font-semibold text-lg">그룹 리더</h1>
        {groupLeaderName}
      </div>
      <h1 className="font-semibold text-lg">그룹 멤버</h1>
      <div className="flex flex-col border-2 h-full p-4 overflow-auto">
        <ul>
          {groupDetail.teamUsers.map((user) => (
            <li key={user.teamUserId}>{user.userDto.userNameLast}{user.userDto.userNameLast}</li>
          ))}
        </ul>
      </div>
      <div className="flex justify-end my-6">
        <Button className="" onClick={moveToUpdate}>수정</Button>
        <Button className="ml-4" onClick={deleteGroup}>삭제</Button>
      </div>
    </div>
  )
}

export default GroupDetail