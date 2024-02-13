import axios from 'axios'
import { useState, FormEvent } from 'react'
import { useLoaderData, Form } from 'react-router-dom'
import { TextInput, Label, Button } from 'flowbite-react'
import { API_URL, formDataToRequestData } from '../utils/api'
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

const MYPAGE_URL = API_URL + '/user/mypage'

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
      .put(MYPAGE_URL, userDetailRequestData, {
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
      <h1 className="text-2xl">마이페이지</h1>
      <div className="grid md:grid-cols-2 my-12 gap-3">
        <div className="grid grid-cols-2">
          <div className="text-center">사번</div>
          <div>{userDetail.userEid}</div>
        </div>
        <div className="grid grid-cols-2">
          <div className="text-center">이름</div>
          <div>{userDetail.userNameFirst}</div>
        </div>
        <div className="grid grid-cols-2">
          <div className="text-center">성</div>
          <div>{userDetail.userNameLast}</div>
        </div>
        <div className="grid grid-cols-2">
          <div className="text-center">이메일</div>
          <div>{userDetail.userEmail}</div>
        </div>
        <div className="grid grid-cols-2">
          <div className="text-center">부서</div>
          <div>{userDetail.departmentName}</div>
        </div>
        <div className="grid grid-cols-2">
          <div className="text-center">직급</div>
          <div>{userDetail.userPosition.slice(5)}</div>
        </div>
        <div className="grid grid-cols-2">
          <div className="text-center">성별</div>
          <div>{userDetail.userGender}</div>
        </div>
      </div>
      <Form className="grid justify-stretch" onSubmit={handleSubmit}>
        <div className="grid md:grid-cols-2 my-12 gap-8">
          <div>
            <Label htmlFor="userTel">전화번호</Label>
            <TextInput
              id="userTel"
              type="text"
              name="userTel"
              onChange={(e) => handleInfoChange('userTel', e.target.value)}
              value={userDetail.userTel}
            />
          </div>
          <div>
            <Label htmlFor="userAddress">주소</Label>
            <TextInput
              id="userAddress"
              type="text"
              name="userAddress"
              onChange={(e) => handleInfoChange('userAddress', e.target.value)}
              value={userDetail.userAddress}
            />
          </div>
          <div>
            <Label htmlFor="userPassword">비밀번호</Label>
            <TextInput
              id="userPassword"
              type="password"
              name="userPassword"
              onChange={(e) => handleInfoChange('userPassword', e.target.value)}
            />
          </div>
          <div>
            <Label htmlFor="userPasswordCheck">비밀번호 확인</Label>
            <TextInput
              id="userPasswordCheck"
              type="password"
              name="userPasswordCheck"
            />
          </div>
        </div>
        <Button className="justify-self-end" type="submit">
          수정
        </Button>
      </Form>
    </div>
  )
}

export default MyPage