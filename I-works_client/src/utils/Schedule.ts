import axios from "axios"
import { API_URL } from "./api"
import { getAccessToken, tokenLoader } from "./auth"


interface ScheduleResponse {
  result:string
  data: ScheduleData[]
}

export interface ScheduleData {
  scheduleId: number
  scheduleAssigneeId: number
  scheduleAssigneeCategoryId:number
  scheduleAssigneeCategoryName: string
  scheduleDivisionName: string
  scheduleTitle: string
  schedulePriority: string
  scheduleStartDate: string
  scheduleEndDate: string
  scheduleIsFinish: boolean
  scheduleIsFinishdAt: string | null
}

const MY_SCH_URL = API_URL + '/schedule-assign'


export async function getMyScheduleList(startDate:string, endDate:string) {
  try{
    const response =  await axios.post<ScheduleResponse>(MY_SCH_URL + '/task/date', 
    {
      startDate, endDate
    },
    {
      headers: { Authorization : 'Bearer ' + getAccessToken()}
    }
    )
    return response.data.data
  } catch {
    return tokenLoader()
  }
}