import { getAccessToken, tokenLoader } from './auth'
import axios from 'axios'
import { API_URL } from './api'

export interface MyPageResponse {
  result: string
  data: MyPageData
}

export interface MyPageData {
  userId: number
  departmentId: number
  departmentName: string
  userPosition: string
  userEid: string
  userNameFirst: string
  userNameLast: string
  userEmail: string
  userTel: string
  userAddress: string
  userGender: string
  userPassword: string
}

export interface MyTeamDataListResponse {
  result:string
  data: TeamData[]
}

export interface TeamData {
  teamName: string
  teamId: number
}

const MY_PAGE_URL = API_URL + '/user/mypage'
const MY_TEAM_URL = API_URL + '/address/team/my'


async function getMyPageData() {
  try {
    const response = await axios.get<MyPageResponse>(MY_PAGE_URL, {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })

    return response.data.data
  } catch (_error) {
    return tokenLoader()
  }
}

async function getMyTeamList() {
  try {
    const response = await axios.get<MyTeamDataListResponse>(MY_TEAM_URL, {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
    return response.data.data
  } catch(_error){
    return tokenLoader()
  }
}


export { getMyPageData, getMyTeamList }
