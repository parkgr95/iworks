import {
  RefObject,
  forwardRef,
  useImperativeHandle,
  useRef,
  FormEvent,
} from 'react'
import { createPortal } from 'react-dom'
import { Form } from 'react-router-dom'
import { Button, TextInput, Textarea, Label, Select } from 'flowbite-react'
import { formDataToRequestData } from '../utils/api'
import axios from 'axios'
import { getAccessToken } from '../utils/auth'

const API_URL = 'https://suhyeon.site/api/schedule/create'

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

const ScheduleCreate = forwardRef(function ScheduleCreatePage(_props, ref) {
  const dialog = useRef<HTMLDialogElement>(null)
  const formRef = useRef<HTMLFormElement>(null)

  useImperativeHandle(
    ref,
    () => ({
      open() {
        dialog.current?.showModal()
      },
    }),
    [],
  )

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event?.preventDefault()

    const scheduleFormData = new FormData(event.currentTarget)

    if (
      scheduleFormData.get('scheduleStartDate')! >
      scheduleFormData.get('scheduleEndDate')!
    ) {
      alert('시작일자가 종료일자보다 뒤에 있습니다.')
      return
    }
    const selectedScheduleDivision = scheduleFormData.get('scheduleDivision')

    const scheduleDivisionValue =
      scheduleDivisionList[selectedScheduleDivision as string]

    scheduleFormData.append('scheduleDivisionCodeId', scheduleDivisionValue)

    // scheduleDivision 값을 FormData에서 제거
    scheduleFormData.delete('scheduleDivision')

    const scheduleRequestData = formDataToRequestData(scheduleFormData)

    axios
      .post(API_URL, scheduleRequestData, {
        headers: {
          Authorization: 'Bearer ' + getAccessToken(),
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
      <Form
        ref={formRef}
        className="flex flex-col gap-4"
        onSubmit={handleSubmit}
      >
        <div className="max-w-md">
          <div className="mb-2 block">
            {/* 수정: id와 name을 일치시켜줍니다. */}
            <Label htmlFor="scheduleDivision">구분</Label>
          </div>
          <Select id="scheduleDivision" name="scheduleDivision" required>
            <option>행사</option>
            <option>업무</option>
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
        <div>
          <Label className="text-lg" htmlFor="schedulePriority">
            우선순위
          </Label>
          <Select id="schedulePriority" name="schedulePriority" required>
            <option>H</option>
            <option>M</option>
            <option>L</option>
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
      </Form>
    </dialog>,
    document.getElementById('modal') as HTMLElement,
  )
})

export default ScheduleCreate
