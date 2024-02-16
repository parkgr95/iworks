import axios from "axios"
import { useState, useEffect } from "react"
import { Link } from "react-router-dom"
import { getAccessToken } from "../../utils/auth"

interface GroupType {
  teamId: string
  teamName: string
}

function GroupList() {
  const [teamList, setTeamList] = useState<GroupType[]>([])

  useEffect(() => {
    async function getTeamList() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/team/all`, {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          }
        })
        setTeamList(res.data.data)
      }
      catch (err) {
        console.log(err)
      }
    }
    getTeamList()
  }, [])

  return (
    <div className="">
      <h1 className="mb-4 text-2xl font-semibold">그룹 목록</h1>
      {teamList.map((team) => (
        <div className="border-b-2 pb-2 mb-6 text-xl" key={team.teamId}>
            <Link to={`/address/group/${team.teamId}`}>{team.teamName}</Link>
        </div>
      ))}
    </div>
  )
}

export default GroupList
