import axios, { AxiosResponse } from "axios";
import { API_URL } from "./api";
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
        const response = await axios.get(ADDRESS_URL+'/user/all')
        return response.data.data
    } 
    catch {

        return tokenLoader()
    }
}


export async function getDepartmentAllList() {
    
    try{
        const response : AxiosResponse<res>= await axios.get(ADDRESS_URL+'/department/all')
        return response.data.data
    } 
    catch {
        
        return tokenLoader()
    }
}


export async function getTeamAllList()  {
    
    try{
        const response  = await axios.get(ADDRESS_URL+'/team/all')
        return response.data.data
    } 
    catch {
        
        return tokenLoader()
    }
}