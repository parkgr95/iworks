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

    if (
      userDetailFormData.get('userPassword') !==
      userDetailFormData.get('userPasswordCheck')
    ) {
      alert('비밀번호가 비밀번호 확인과 다릅니다.')
      return
    }

    userDetailFormData.delete('userPasswordCheck')

    const userDetailRequestData = formDataToRequestData(userDetailFormData)

    axios
      .put(API_URL, userDetailRequestData, {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
        },
      })
      .then((_response) => {
        alert('회원정보 수정이 완료되었습니다.')
      })
      .catch((error) => {
        alert(error)
      })
  }

  return (
    <div className="p-16">
      <h1 className="text-2xl m-4">마이페이지</h1>
      <p>사번 : {userDetail.userEid}</p>
      <p>이름 : {userDetail.userNameFirst}</p>
      <p>성 : {userDetail.userNameLast}</p>
      <p>이메일 : {userDetail.userEmail}</p>
      <p>부서 : {userDetail.departmentName}</p>
      <p>직급 : {userDetail.userPosition}</p>
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
        <Label htmlFor="userPassword">비밀번호</Label>
        <TextInput
          id="userPassword"
          type="password"
          name="userPassword"
          onChange={(e) => handleInfoChange('userPassword', e.target.value)}
        />
        <Label htmlFor="userPasswordCheck">비밀번호 확인</Label>
        <TextInput
          id="userPasswordCheck"
          type="password"
          name="userPasswordCheck"
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
  if (!getAccessToken()) {
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
    console.log(response.data.data)
    return response.data.data
  } catch (error) {
    console.error(error)
    return null
  }
}

export { myPageLoader }
