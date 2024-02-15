import axios from 'axios'
import { useState, FormEvent, useEffect, ChangeEvent } from 'react'
import { Form, useParams, useNavigate } from 'react-router-dom'
import { TextInput, Label, Button, Select } from 'flowbite-react'
import { getAccessToken, getNewAccessToken } from '../../utils/auth'

interface UserDetailType {
  userId: number
  departmentId: number
  departmentName: string
  userPosition: string
  userEid: string
  userNameFirst: string
  userNameLast: string
  userEmail: string
  userTel: string
  userAddress: string
  userGender: string
  userCreatedAt: string
  userUpdatedAt: string
  userDeletedAt: null
  userIsDeleted: false
  userRole: string[]
  userStaus: null
}

interface orginizationType {
  departmentName: string
  departmentId: number
}

function AdminUsersDetail() {
  const navigate = useNavigate()
  const { userId = '' } = useParams<{ userId: string }>()
  const [userDetail, setUserDetail] = useState<UserDetailType>()
  const [formData, setFormData] = useState({
    userEmail: '',
    userTel: '',
    userAddress: '',
    userGender: '',
    userNameFirst: '',
    userNameLast: '',
    departmentId: 0,
  })
  const {
    userNameLast,
    userNameFirst,
    userGender,
    userEmail,
    userTel,
    userAddress,
    departmentId,
  } = formData

  const [departmentList, setDepartmentList] = useState<orginizationType[]>([])

  useEffect(() => {
    async function getGroupDetail() {
      try {
        const res = await axios.get(
          `https://suhyeon.site/api/admin/user/detail/${userId}`,
          {
            headers: {
              Authorization: 'Bearer ' + getAccessToken(),
            },
          },
        )
        setUserDetail(res.data.data)
        setFormData({
          userEmail: res.data.data.userEmail,
          userTel: res.data.data.userTel,
          userAddress: res.data.data.userAddress,
          userGender: res.data.data.userGender,
          userNameFirst: res.data.data.userNameFirst,
          userNameLast: res.data.data.userNameLast,
          departmentId: res.data.data.departmentId,
        })
      } catch (err) {
        console.log(err)
      }
    }
    axios
      .get('https://suhyeon.site/api/address/department/all', {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
        },
      })
      .then((res) => {
        setDepartmentList(res.data.data)
      })
      .catch((err) => {
        console.log(err)
      })

    getGroupDetail()
  }, [userId])

  const onInputChange = (
    event: ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    const { name, value } = event.target
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }))
    console.log(userAddress)
  }

  function handleUpdate(event: FormEvent) {
    event.preventDefault()

    axios
      .put(
        `https://suhyeon.site/api/admin/user/update/${userId}`,
        {
          userEmail: userEmail,
          userTel: userTel,
          userAddress: userAddress,
          userGender: userGender,
          userNameFirst: userNameFirst,
          userNameLast: userNameLast,
          userDepartmentId: departmentId,
        },
        {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          },
        },
      )
      .then((res) => {
        navigate('/admin/users')
        console.log(res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  function handleDelete(event: FormEvent) {
    event.preventDefault()
    axios
      .put(
        `https://suhyeon.site/api/admin/user/delete/${userId}`,
        {},
        {
          headers: {
            Authorization: 'Bearer ' + getAccessToken(),
          },
        },
      )
      .then((res) => {
        navigate('/admin/users')
        console.log(res.data)
      })
      .catch((err) => {
        const error = err.response.data
        if (error.result == 'failed') {
          alert(error.message)
        } else if (error.react == 'expired') {
        }
        console.log(err)
      })
  }
  return (
    <div className="p-16">
      <h1 className="text-2xl">유저 상세 페이지</h1>
      <Form className="grid justify-stretch">
        <div className="grid md:grid-cols-2 my-12 gap-8">
          <div>
            <div className="">사번 : {userDetail?.userEid}</div>
          </div>
          <div>
            <div className="">
              활성화 여부 : {userDetail?.userIsDeleted ? '비활성화' : '활성화'}
            </div>
          </div>
          <div>
            <Label htmlFor="userNameLast">성 / </Label>
            <Label htmlFor="userNameFirst">이름</Label>
            <div className="flex">
              <TextInput
                id="userNameLast"
                type="text"
                name="userNameLast"
                onChange={onInputChange}
                value={userNameLast}
              />
              <div className="w-2"></div>
              <TextInput
                id="userNameFirst"
                type="text"
                name="userNameFirst"
                onChange={onInputChange}
                value={userNameFirst}
              />
            </div>
          </div>
          <div>
            <Label htmlFor="userGender">성별</Label>
            <TextInput
              id="userGender"
              type="text"
              name="userGender"
              onChange={onInputChange}
              value={userGender}
            />
          </div>
          <div>
            <Label htmlFor="userEmail">이메일</Label>
            <TextInput
              id="userEmail"
              type="text"
              name="userEmail"
              onChange={onInputChange}
              value={userEmail}
            />
          </div>
          <div>
            <Label htmlFor="userTel">전화번호</Label>
            <TextInput
              id="userTel"
              type="text"
              name="userTel"
              onChange={onInputChange}
              value={userTel}
            />
          </div>
          <div>
            <Label htmlFor="userAddress">주소</Label>
            <TextInput
              id="userAddress"
              type="text"
              name="userAddress"
              onChange={onInputChange}
              value={userAddress}
            />
          </div>
          <div>
            <Label htmlFor="departmentId">부서</Label>
            <Select
              name="departmentId"
              required
              value={departmentId}
              onChange={onInputChange}
            >
              {departmentList.map((departmentInfo) => (
                <option
                  key={departmentInfo.departmentId}
                  value={departmentInfo.departmentId}
                >
                  {departmentInfo.departmentName}
                </option>
              ))}
            </Select>
          </div>
        </div>
        <div className="flex justify-end">
          <Button
            onClick={handleUpdate}
            className="bg-mainGreen mr-2"
            type="submit"
          >
            수정
          </Button>
          <Button
            onClick={handleDelete}
            className="bg-rose-700 mr-2"
            type="submit"
          >
            삭제
          </Button>
          <Button
            onClick={() => {
              navigate('/admin/users')
            }}
            className="bg-mainBlue"
            type="submit"
          >
            취소
          </Button>
        </div>
      </Form>
    </div>
  )
}

export default AdminUsersDetail
