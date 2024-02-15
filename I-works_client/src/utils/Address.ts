import axios, { AxiosResponse } from "axios";
import { API_URL, REQUEST_HEADER } from "./api";
import { tokenLoader } from "./auth";

const ADDRESS_URL = API_URL+ '/address'

export interface DepartmentInfo {
    departmentName:string
    departmentId: number
  }

interface res {
    result:string
    data : any[]
}

export async function getUserAllList () {
    
    try{
        const response = await axios.get(ADDRESS_URL+'/user/all', REQUEST_HEADER)
        return response.data.data
    } 
    catch {
        console.log('유저 올 실패!')
        return tokenLoader()
    }
}


export async function getDepartmentAllList() {
    
    try{
        const response : AxiosResponse<res>= await axios.get(ADDRESS_URL+'/department/all', REQUEST_HEADER)
        return response.data.data
    } 
    catch {
        console.log('부서 올 실패!')
        return tokenLoader()
    }
}


export async function getTeamAllList()  {
    
    try{
        const response  = await axios.get(ADDRESS_URL+'/team/all', REQUEST_HEADER)
        return response.data.data
    } 
    catch {
        console.log('팀 올 실패')
        return tokenLoader()
    }
}