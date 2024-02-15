import axios from 'axios'
import { API_URL } from './api'
import { getAccessToken } from './auth'

const CODE_URL = API_URL + '/code'

interface CodeListResponse{
  result: string
  data: CodeData[]
}

export interface CodeData {
  codeId:number
  codeName: string
}

export async function getPositionCodeList() {
  try {
    const response = await axios.get<CodeListResponse>(CODE_URL + '/position', {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
    return response.data.data
  } catch {
    alert('포지션 코드 불러오기 실패')
    return null
  }
}

export async function getStatusCodeList() {
  try {
    const response = await axios.get<CodeListResponse>(CODE_URL + '/status', {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
    return response.data.data
  } catch {
    alert('상태 코드 불러오기 실패')
    return null
  }
}

export async function getCategoryCodeList() {
  try {
    const response = await axios.get<CodeListResponse>(CODE_URL + '/category', {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
    return response.data.data
  } catch {
    alert('카테고리 코드 불러오기 실패')
    return null
  }
}

export async function getTargetCodeList() {
  try {
    const response = await axios.get<CodeListResponse>(CODE_URL + '/target', {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
    return response.data.data
  } catch {
    alert('타겟 코드 불러오기 실패')
    return null
  }
}

export async function getScheduleDivisionCodeList() {
  try{
    const response = await axios.get<CodeListResponse>(CODE_URL + '/schedule-division', {
      headers: {
        Authorization: 'Bearer ' + getAccessToken(),
      },
    })
    return response.data.data
  } catch {
    alert('할일 분류 코드 불러오기 실패')
  }
  
}
