import { redirect } from 'react-router-dom'
import { getAccessToken } from './auth'
import axios, { AxiosResponse } from 'axios'
import { API_URL } from './api'
import { DepartmentInfo, getDepartmentAllList } from './Address'

export interface UserDetailResponse {
  result: string
  data: {
    userId: number
    userPosition: string
    departmentName: string
    userEid: string
    userNameFirst: string
    userNameLast: string
    userEmail: string
    userTel: string
    userAddress: string
    userGender: string
    userPassword: string
    departmentId: number
  }
}

export interface UserDetail {
  userId: number
  userPosition: string
  departmentName: string
  userEid: string
  userNameFirst: string
  userNameLast: string
  userEmail: string
  userTel: string
  userAddress: string
  userGender: string
  userPassword: string
  departmentId: number
}

async function getUserDetailInfo() {
  if (!getAccessToken()) {
    return redirect('/user/login')
  }
  try {
    const response: AxiosResponse<UserDetailResponse> = await axios.get(
      API_URL + '/user/mypage',
      {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
        },
      },
    )

    const userDetail = response.data.data

    const deptResponse = (await getDepartmentAllList()) as DepartmentInfo[]

    const departmentId = deptResponse.find(
      (dept) => dept.departmentName == userDetail.departmentName,
    ) as DepartmentInfo

    userDetail.departmentId = departmentId.departmentId

    return userDetail
  } catch (error) {
    console.error(error)
    return null
  }
}

export { getUserDetailInfo }
