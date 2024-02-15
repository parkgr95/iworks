import axios from 'axios'
import { API_URL } from './api'

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
    const response = await axios.get<CodeListResponse>(CODE_URL + '/position')
    return response.data.data
  } catch {
    alert('포지션 코드 불러오기 실패')
    return null
  }
}

export async function getStatusCodeList() {
  try {
    const response = await axios.get<CodeListResponse>(CODE_URL + '/status')
    return response.data.data
  } catch {
    alert('상태 코드 불러오기 실패')
    return null
  }
}

export async function getCategoryCodeList() {
  try {
    const response = await axios.get<CodeListResponse>(CODE_URL + '/category')
    return response.data.data
  } catch {
    alert('카테고리 코드 불러오기 실패')
    return null
  }
}

export async function getTargetCodeList() {
  try {
    const response = await axios.get<CodeListResponse>(CODE_URL + '/target')
    return response.data.data
  } catch {
    alert('타겟 코드 불러오기 실패')
    return null
  }
}

export async function getScheduleDivisionCodeList() {
  try{
    const response = await axios.get<CodeListResponse>(CODE_URL + '/schedule-division')
    return response.data.data
  } catch {
    alert('할일 분류 코드 불러오기 실패')
  }
  
}
