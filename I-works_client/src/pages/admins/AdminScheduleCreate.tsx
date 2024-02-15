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
  import {
    Button,
    TextInput,
    Textarea,
    Label,
    Select,
    Toast,
    ToggleSwitch,
  } from 'flowbite-react'
  import { API_URL, formDataToRequestData } from '../../utils/api'
  import axios from 'axios'
  import { UserType } from '../../interface/UserType'
  import {
    getDepartmentAllList,
    getTeamAllList,
    getUserAllList,
  } from '../../utils/Address'
  import { AiOutlineClose } from 'react-icons/ai'
  import { CodeData, getScheduleDivisionCodeList } from '../../utils/Code'
  
  const SCH_URL = API_URL + 'admin/schedule/'
  
  export interface ScheduleCreateRef {
    open: () => void
  }
  
  export interface AssigneeType {
    categoryCodeId: number
    assigneeId: number
  }
  
  interface DepartmentInfo {
    departmentName: string
    departmentId: number
  }
  
  interface TeamInfo {
    teamName: string
    teamId: number
  }
  
  const labelClass = 'text-md font-bold p-1'
  const inputClass = 'mt-1'
  
  const AdminScheduleCreate = forwardRef(function ScheduleCreatePage(_props, ref) {
    // 모달
    const dialog = useRef<HTMLDialogElement>(null)
    const formRef = useRef<HTMLFormElement>(null)
  
    // 받아올 유저, 부서, 팀, 분류 리스트
    const [userList, setUserList] = useState<UserType[]>([])
    const [departmentList, setDepartmentList] = useState<DepartmentInfo[]>([])
    const [teamList, setTeamList] = useState<TeamInfo[]>([])
    const [scheduleDivisionList, setScheduleDivisionList] = useState<CodeData[]>(
      [],
    )
  
    // 입력한 담당 유저, 부서, 팀 리스트
    const [assigneeUserList, setAssigneeUserList] = useState<UserType[]>([])
    const [assigneeDepartmentList, setAssigneeDepartmentList] = useState<
      DepartmentInfo[]
    >([])
    const [assigneeTeamList, setAssigneeTeamList] = useState<TeamInfo[]>([])
  
    // 화상 회의 생성 여부
    const [toggleCreateMeeting, setToggleCreateMeeting] = useState<boolean>(false)
  
    // 생성 위한 기본 정보 (부서, 팀, 유저, 분류 리스트) 받아오기
    useEffect(() => {
      const fetchData = async () => {
        const departmentResponse =
          (await getDepartmentAllList()) as DepartmentInfo[]
        const userResponse = (await getUserAllList()) as UserType[]
        const teamResponse = (await getTeamAllList()) as TeamInfo[]
        const scheduleDivisionResponse =
          (await getScheduleDivisionCodeList()) as CodeData[]
        setDepartmentList(departmentResponse)
        setUserList(userResponse)
        setTeamList(teamResponse)
        setScheduleDivisionList(scheduleDivisionResponse)
      }
      fetchData()
    }, [])
  
    // 모달창 관련
    useImperativeHandle(
      ref,
      () => ({
        open() {
          dialog.current?.showModal()
        },
      }),
      [],
    )
  
    // 담당 추가 로직
    function handleAddAssignee(event: any, target: string) {
      if (event.target.value == 'none') {
        return
      }
      if (target == 'user') {
        if (assigneeUserList.find((info) => info.userId == event.target.value)) {
          alert('이미 추가된 유저입니다.')
          return
        }
  
        const selectedUser: UserType = userList.find(
          (user) => user.userId == event.target.value,
        )!
  
        setAssigneeUserList((prev) => {
          return [...prev, selectedUser]
        })
      } else if (target == 'department') {
        if (
          assigneeDepartmentList.find(
            (info) => info.departmentId == event.target.value,
          )
        ) {
          alert('이미 추가된 부서입니다.')
          return
        }
  
        const selectedDepartment: DepartmentInfo = departmentList.find(
          (department) => department.departmentId == event.target.value,
        )!
  
        setAssigneeDepartmentList((prev) => {
          return [...prev, selectedDepartment]
        })
      } else if (target == 'team') {
        if (assigneeTeamList.find((info) => info.teamId == event.target.value)) {
          console.log(assigneeTeamList)
          alert('이미 추가된 팀입니다.')
          return
        }
  
        const selectedTeam: TeamInfo = teamList.find(
          (team) => team.teamId == event.target.value,
        )!
  
        setAssigneeTeamList((prev) => {
          return [...prev, selectedTeam]
        })
      }
    }
  
    // 담당 삭제 로직
    function handleDeleteAssignee(id: number, target: string) {
      if (target == 'user') {
        setAssigneeUserList((prev) => {
          return prev.filter((info) => info.userId != id)
        })
      } else if (target == 'department') {
        setAssigneeDepartmentList((prev) => {
          return prev.filter((info) => info.departmentId != id)
        })
      } else if (target == 'team') {
        setAssigneeTeamList((prev) => {
          return prev.filter((info) => info.teamId != id)
        })
      }
    }
  
    // 엔터키로 제출 방지
    const handleKeyDown = (event: React.KeyboardEvent<HTMLFormElement>) => {
      if (event.key === 'Enter') {
        event.preventDefault() // 폼 제출을 방지합니다.
      }
    }
  
    // 할일 생성 로직
    function handleSubmit(event: FormEvent<HTMLFormElement>) {
      event?.preventDefault()
  
      const scheduleFormData = new FormData(event.currentTarget)
  
      let assigneeInfos: AssigneeType[] = []
  
      // 선택된 담당들을 하나의 리스트로 정리
      for (const user of assigneeUserList) {
        assigneeInfos.push({ categoryCodeId: 5, assigneeId: user.userId })
      }
  
      for (const department of assigneeDepartmentList) {
        assigneeInfos.push({
          categoryCodeId: 6,
          assigneeId: department.departmentId,
        })
      }
  
      for (const team of assigneeTeamList) {
        assigneeInfos.push({ categoryCodeId: 7, assigneeId: team.teamId })
      }
  
      // 폼데이터 변환
      const scheduleRequestData: any = formDataToRequestData(scheduleFormData)
  
      // 담당자 및 회의 추가 여부 추가
      scheduleRequestData.assigneeInfos = assigneeInfos
      scheduleRequestData.isCreateMeeting = toggleCreateMeeting
  
      console.log(scheduleRequestData)
  
      // 폼 전송
  
      axios
        .post(SCH_URL, scheduleRequestData)
        .then((response) => {
          alert(response.data.data)
          formRef.current?.reset()
          setAssigneeDepartmentList([])
          setAssigneeUserList([])
          setAssigneeTeamList([])
        })
        .catch((error) => {
          console.log(error.response.data.data)
        })
    }
  
    return createPortal(
      <dialog
        className="rounded-xl p-10 w-2/3 max-w-xl min-w-fit "
        ref={dialog as RefObject<HTMLDialogElement>}
      >
        <h1 className="text-3xl text-center mb-4 font-bold">할 일 생성</h1>
        <Form
          className="grid grid-col gap-4"
          ref={formRef}
          onSubmit={handleSubmit}
          onKeyDown={handleKeyDown}
        >
          {/* 할 일 구분 */}
          <div className="mb-2 block">
            <Label className={labelClass} htmlFor="scheduleDivisionCodeId">
              구분
            </Label>
            <Select
              className={inputClass}
              id="scheduleDivisionCodeId"
              name="scheduleDivisionCodeId"
              required
            >
              {scheduleDivisionList.map((scheduleDivisionData) => {
                return (
                  <option
                    key={scheduleDivisionData.codeId}
                    value={scheduleDivisionData.codeId}
                  >
                    {scheduleDivisionData.codeName}
                  </option>
                )
              })}
            </Select>
          </div>
  
          {/* 제목 */}
          <div>
            <Label className={labelClass} htmlFor="scheduleTitle">
              제목
            </Label>
            <TextInput
              className={inputClass}
              type="text"
              name="scheduleTitle"
              required
              id="scheduleTitle"
            />
          </div>
  
          {/* 담당 부서 */}
          <div>
            <Label className={labelClass}>담당 부서</Label>
            <div className="flex flex-wrap gap-2">
              {assigneeDepartmentList.map((info) => {
                return (
                  <Toast
                    className="w-fit text-black text-sm mb-2 px-2 py-1 bg-red-100"
                    key={info.departmentId}
                  >
                    {info.departmentName}{' '}
                    <Button
                      onClick={() =>
                        handleDeleteAssignee(info.departmentId, 'department')
                      }
                      className="size-6 p-0 ml-1 text-black bg-red-100"
                    >
                      <AiOutlineClose />
                    </Button>
                  </Toast>
                )
              })}
            </div>
            <Select
              className={inputClass}
              onChange={(event) => handleAddAssignee(event, 'department')}
            >
              <option value="none">선택해주세요</option>
              {departmentList.map((info) => {
                return (
                  <option key={info.departmentId} value={info.departmentId}>
                    {info.departmentName}
                  </option>
                )
              })}
            </Select>
          </div>
  
          {/* 담당 팀 */}
          <div>
            <Label className={labelClass}>담당 팀</Label>
            <div className="flex flex-wrap gap-2">
              {assigneeTeamList.map((info) => {
                return (
                  <Toast
                    className="w-fit text-black text-sm my-2 px-2 py-1 bg-lime-100"
                    key={info.teamId}
                  >
                    {info.teamName}{' '}
                    <Button
                      onClick={() => handleDeleteAssignee(info.teamId, 'team')}
                      className="size-6 p-0 ml-1 text-black bg-lime-100"
                    >
                      <AiOutlineClose />
                    </Button>
                  </Toast>
                )
              })}
            </div>
            <Select
              className={inputClass}
              onChange={(event) => handleAddAssignee(event, 'team')}
            >
              <option value="none">선택해주세요</option>
              {teamList.map((info) => {
                return (
                  <option key={info.teamId} value={info.teamId}>
                    {info.teamName}
                  </option>
                )
              })}
            </Select>
          </div>
  
          {/* 담당자 */}
          <div>
            <Label className={labelClass}>담당자</Label>
            <div className="flex flex-wrap gap-2">
              {assigneeUserList.map((info) => {
                return (
                  <Toast
                    className="w-fit text-black text-sm my-2 px-2 py-1 bg-teal-100"
                    key={info.userId}
                  >
                    {info.userNameLast + info.userNameFirst} (
                    {info.departmentName})
                    <Button
                      onClick={() => handleDeleteAssignee(info.userId, 'user')}
                      className="size-6 p-0 ml-1 text-black bg-teal-100"
                    >
                      <AiOutlineClose />
                    </Button>
                  </Toast>
                )
              })}
            </div>
            <Select
              className={inputClass}
              onChange={(event) => handleAddAssignee(event, 'user')}
            >
              <option value="none">선택해주세요</option>
              {userList.map((info) => {
                return (
                  <option key={info.userId} value={info.userId}>
                    {info.userNameLast + info.userNameFirst} (
                    {info.departmentName})
                  </option>
                )
              })}
            </Select>
          </div>
  
          {/* 우선순위 */}
          <div>
            <Label className={labelClass} htmlFor="schedulePriority">
              우선순위
            </Label>
            <Select
              className={inputClass}
              id="schedulePriority"
              name="schedulePriority"
              required
            >
              <option value={'H'}>높음</option>
              <option value={'M'}>중간</option>
              <option value={'L'}>낮음</option>
            </Select>
          </div>
  
          {/* 내용 */}
          <div>
            <Label className={labelClass} htmlFor="scheduleContent">
              내용
            </Label>
            <Textarea
              className={inputClass}
              name="scheduleContent"
              required
              id="scheduleContent"
            />
          </div>
  
          {/* 시작일시 */}
          <div>
            <Label className={labelClass} htmlFor="scheduleStartDate">
              시작 일시
            </Label>
            <TextInput
              className={inputClass}
              type="datetime-local"
              name="scheduleStartDate"
              required
              id="scheduleStartDate"
            />
          </div>
  
          {/* 종료 일시 */}
          <div>
            <Label className={labelClass} htmlFor="scheduleEndDate">
              종료 일시
            </Label>
            <TextInput
              className={inputClass}
              type="datetime-local"
              name="scheduleEndDate"
              required
              id="scheduleEndDate"
            />
          </div>
  
          {/* 장소 */}
          <div>
            <Label className={labelClass} htmlFor="schedulePlace">
              장소
            </Label>
            <TextInput
              className={inputClass}
              type="text"
              name="schedulePlace"
              required
              id="schedulePlace"
            />
          </div>
  
          {/* 회의 여부 */}
          <div className="">
            <ToggleSwitch
              id="isCreateMeeting"
              name="isCreateMeeting"
              checked={toggleCreateMeeting}
              label="화상 회의 여부"
              onChange={setToggleCreateMeeting}
            />
          </div>
  
          {/* 회의 일시 */}
          {toggleCreateMeeting && (
            <div>
              <Label className={labelClass} htmlFor="meetingDate">
                회의 일시
              </Label>
              <TextInput
                className={inputClass}
                type="datetime-local"
                name="meetingDate"
                id="meetingDate"
              />
            </div>
          )}
  
          {/* 버튼 */}
          <Button className="bg-mainGreen mt-4" type="submit">
            할 일 생성
          </Button>
        </Form>
      </dialog>,
      document.getElementById('modal') as HTMLElement,
    )
  })
  
  export default AdminScheduleCreate
  