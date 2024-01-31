import axios, { AxiosResponse } from 'axios'
import { useState, FormEvent } from 'react'
import { useLoaderData, Form, redirect } from 'react-router-dom'
import { TextInput, Label, Button } from 'flowbite-react'
import { formDataToRequestData } from '../utils/api'
import { getAccessToken } from '../utils/auth'

interface UserDetailResponse {
  result: string
  data: {
    userId: number
    userEid: string
    userNameFirst: string
    userNameLast: string
    userEmail: string
    userTel: string
    userAddress: string
    userGender: string
  }
}

const API_URL = 'https://suhyeon.site/api/user/mypage'

function MyPage() {
  const userDetailLoad = useLoaderData() as UserDetailResponse['data']
  const [userDetail, setUserDetail] = useState(userDetailLoad)

  function handleInfoChange(key: string, value: string) {
    setUserDetail((prevInfo: UserDetailResponse['data']) => ({
      ...prevInfo,
      [key]: value,
    }))
  }

  function handleSubmit(event: FormEvent) {
    event.preventDefault()

    const userDetailFormData = new FormData(event.target as HTMLFormElement)
    const userDetailRequestData = formDataToRequestData(userDetailFormData)

    axios
      .put(API_URL, userDetailRequestData, {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
        },
      })
      .then((response) => {
        console.log(response.data)
      })
  }

  return (
    <div className="p-16">
      <h1 className="text-2xl m-4">마이페이지</h1>
      <p>사번 : {userDetail.userEid}</p>
      <p>이름 : {userDetail.userNameFirst}</p>
      <p>성 : {userDetail.userNameLast}</p>
      <p>이메일 : {userDetail.userEmail}</p>
      <p>성별 : {userDetail.userGender}</p>
      <Form onSubmit={handleSubmit}>
        <Label htmlFor="userTel">전화번호</Label>
        <TextInput
          id="userTel"
          type="text"
          name="userTel"
          onChange={(e) => handleInfoChange('userTel', e.target.value)}
          value={userDetail.userTel}
        />
        <Label htmlFor="userAddress">주소</Label>
        <TextInput
          id="userAddress"
          type="text"
          name="userAddress"
          onChange={(e) => handleInfoChange('userAddress', e.target.value)}
          value={userDetail.userAddress}
        />
        <Button className="my-8" type="submit">
          수정
        </Button>
      </Form>
    </div>
  )
}

export default MyPage

async function myPageLoader() {
  if (!getAccessToken()){
    return redirect('/user/login')
  }
  try {
    const response: AxiosResponse<UserDetailResponse> = await axios.get(
      API_URL,
      {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
        },
      },
    )
    return response.data.data
  } catch (error) {
    console.error(error)
  }  
}

export { myPageLoader }
