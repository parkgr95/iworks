import { redirect } from "react-router-dom"
import { getAccessToken } from "./auth"
import axios, { AxiosResponse } from "axios"
import { API_URL } from "./api"

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
      return response.data.data
    } catch (error) {
      console.error(error)
      return null
    }
  }

export { getUserDetailInfo }