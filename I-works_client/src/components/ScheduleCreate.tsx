import {
  RefObject,
  forwardRef,
  useImperativeHandle,
  useRef,
  FormEvent,
  useState,
  useEffect,
} from 'react'
import { createPortal } from 'react-dom'
import { Form } from 'react-router-dom'
import { Button, TextInput, Textarea, Label, Select } from 'flowbite-react'
import { formDataToRequestData } from '../utils/api'
import axios from 'axios'
import { UserType } from '../interface/UserType'
import { getAccessToken } from '../utils/auth'

const API_URL = 'https://suhyeon.site/api/schedule/'
const API_DEPT = 'https://suhyeon.site/api/address/department/all'
const API_USR = 'https://suhyeon.site/api/address/user/all'

const scheduleDivisionList: { [key: string]: string } = {
  행사: '1',
  업무: '2',
  '개인일정(병가)': '3',
  '개인일정(외출)': '4',
  '개인일정(휴가)': '5',
}

export interface ScheduleCreateRef {
  open: () => void
}

interface dept {
  departmentName: string
  departmentId: string
}

// interface responseList {
//   result: string
//   data: dept | userType[]
// }

// interface reqDate {
//   startDate:string
//   endDate:string
// }

const ScheduleCreate = forwardRef(function ScheduleCreatePage(_props, ref) {
  const dialog = useRef<HTMLDialogElement>(null)
  const formRef = useRef<HTMLFormElement>(null)
  const [departmentList, setDepartmentList] = useState<dept[]>()
  const [userList, setUserList] = useState<UserType[]>()
  const [assignDepartment, setAssignDepartment] = useState<Boolean>(true)

  // const mySchedule = useLoaderData()


  useEffect(() => {
    axios
      .all([axios.get(API_DEPT), axios.get(API_USR)])
      .then(
        axios.spread((dept, usr) => {
          setDepartmentList(dept.data.data)
          setUserList(usr.data.data)
        }),
      )
      .catch((_error) => alert('불러오기 실패'))
  }, [])

  useImperativeHandle(
    ref,
    () => ({
      open() {
        dialog.current?.showModal()
      },
    }),
    [],
  )

  function handleSelectedDepartment() {
    setAssignDepartment((prev) => !prev)
  }

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event?.preventDefault()

    const scheduleFormData = new FormData(event.currentTarget)
    const scheduleAssigneeCategoryCodeId = assignDepartment ? '1' : '2'
    scheduleFormData.append(
      'scheduleAssigneeCategoryCodeId',
      scheduleAssigneeCategoryCodeId,
    )

    // const test = scheduleFormData.get('departmentId')

    // const testl = departmentList?.find(dept => dept.departmentName == test) as dept

    // scheduleFormData.set('departmentId', testl.departmentId)

    const scheduleRequestData = formDataToRequestData(scheduleFormData)

    axios
      .post(API_URL, scheduleRequestData, {
        headers: {
          Authorization:
            'Bearer ' + getAccessToken()
        },
      })
      .then((response) => {
        alert(response.data.data)
        formRef.current?.reset()
      })
      .catch((error) => {
        console.log(error.response.data.data)
      })
  }

  return createPortal(
    <dialog
      className="rounded-xl p-10 w-2/3 max-w-xl min-w-fit"
      ref={dialog as RefObject<HTMLDialogElement>}
    >
      <h1 className="text-3xl text-center mb-10">할 일 생성</h1>
      <Form ref={formRef} className="flex flex-col" onSubmit={handleSubmit}>
        <div className="max-w-md">
          <div className="mb-2 block">
            {/* 수정: id와 name을 일치시켜줍니다. */}
            <Label htmlFor="scheduleDivisionCodeId">구분</Label>
            <Select
              id="scheduleDivisionCodeId"
              name="scheduleDivisionCodeId"
              required
            >
              {Object.entries(scheduleDivisionList).map(
                ([scheduleDivisionName, scheduleDivisionCodeId], _index) => (
                  <option
                    key={scheduleDivisionCodeId}
                    value={scheduleDivisionCodeId}
                  >
                    {scheduleDivisionName}
                  </option>
                ),
              )}
            </Select>
          </div>
          <div>
            <Label className="text-lg" htmlFor="scheduleTitle">
              제목
            </Label>
            <TextInput
              type="text"
              name="scheduleTitle"
              required
              id="scheduleTitle"
            />
          </div>
          <Label htmlFor="scheduleAssigneeId">
            {assignDepartment ? '부서' : '담당자'}
          </Label>
          <div className="flex gap-2">
            {assignDepartment ? (
              <>
                <div className="flex-1">
                  <Select id="scheduleAssigneeId" name="scheduleAssigneeId">
                    {departmentList?.map((value) => {
                      return (
                        <option
                          key={value.departmentId}
                          value={value.departmentId}
                          label={value.departmentName}
                        />
                      )
                    })}
                  </Select>
                </div>
              </>
            ) : (
              <div className="flex-1">
                <Select id="scheduleAssigneeId" name="scheduleAssigneeId">
                  {userList?.map((value) => {
                    return (
                      <option
                        key={value.userEid}
                        value={value.userEid}
                        label={value.userNameLast + value.userNameFirst}
                      />
                    )
                  })}
                </Select>
              </div>
            )}
            <Button onClick={handleSelectedDepartment}>
              {assignDepartment ? '개인' : '부서'} 선택
            </Button>
          </div>

          <div>
            <Label className="text-lg" htmlFor="schedulePriority">
              우선순위
            </Label>
            <Select id="schedulePriority" name="schedulePriority" required>
              <option value={'H'}>높음</option>
              <option value={'M'}>중간</option>
              <option value={'L'}>낮음</option>
            </Select>
          </div>
          <div>
            <Label className="text-lg" htmlFor="scheduleContent">
              내용
            </Label>
            <Textarea name="scheduleContent" required id="scheduleContent" />
          </div>
          <div>
            <Label className="text-lg" htmlFor="scheduleStartDate">
              시작 일시
            </Label>
            <TextInput
              type="datetime-local"
              name="scheduleStartDate"
              required
              id="scheduleStartDate"
            />
          </div>
          <div>
            <Label className="text-lg" htmlFor="scheduleEndDate">
              종료 일시
            </Label>
            <TextInput
              type="datetime-local"
              name="scheduleEndDate"
              required
              id="scheduleEndDate"
            />
          </div>
          <div>
            <Label className="text-lg" htmlFor="schedulePlace">
              장소
            </Label>
            <TextInput
              type="text"
              name="schedulePlace"
              required
              id="schedulePlace"
            />
          </div>
          <div>
            <Label className="text-lg" htmlFor="meetingDate">
              회의 일시
            </Label>
            <TextInput
              type="datetime-local"
              name="meetingDate"
              required
              id="meetingDate"
            />
          </div>
          <Button className="mt-10" type="submit">
            할 일 생성
          </Button>
        </div>
      </Form>
    </dialog>,
    document.getElementById('modal') as HTMLElement,
  )
})

export default ScheduleCreate


