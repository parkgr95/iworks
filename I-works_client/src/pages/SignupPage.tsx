import {
  forwardRef,
  useImperativeHandle,
  useRef,
  FormEvent,
  RefObject,
} from 'react'
import { createPortal } from 'react-dom'
import { Form } from 'react-router-dom'
import axios from 'axios'
import { TextInput, Button, Radio, Label } from 'flowbite-react'
import { formDataToRequestData } from '../utils/api'

export interface SignupRef {
  open: () => void
}

const API_URL = 'https://suhyeon.site/api/user/join'

const Signup = forwardRef<SignupRef>(function Signup(_props, ref) {
  const dialog = useRef<HTMLDialogElement>(null)
  const formRef = useRef<HTMLFormElement>(null);

  useImperativeHandle(
    ref,
    () => ({
      open() {
        dialog.current?.showModal()
      },
    }),
    [],
  )

  function handleSignUp(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()

    const signupFormData = new FormData(event.currentTarget)
    const signupRequestData = formDataToRequestData(signupFormData)

    axios
      .post(API_URL, signupRequestData)
      .then((response) => {
        alert(response.data.data.message)
        formRef.current?.reset()        
      })
      .catch((error) => {
        alert(error.response.data.data)
      })
  }

  return createPortal(
    <dialog
      className="rounded-xl p-10 w-2/3 max-w-xl min-w-fit"
      ref={dialog as RefObject<HTMLDialogElement>}
    >
      <h1 className="text-3xl text-center mb-10">구성원 추가</h1>
      <Form ref={formRef} className="flex flex-col gap-4" onSubmit={handleSignUp}>
        <div>
          <Label className="text-lg">사번</Label>
          <TextInput type="text" name="userEid" required />
        </div>
        <div>
          <Label className="text-lg">이름</Label>
          <TextInput type="text" name="userNameFirst" required />
        </div>
        <div>
          <Label className="text-lg">성</Label>
          <TextInput type="text" name="userNameLast" required />
        </div>
        <div>
          <Label className="text-lg">부서</Label>
          <TextInput type="text" name="userDepartmentId" required />
        </div>
        <div>
          <Label className="text-lg">직급</Label>
          <TextInput type="text" name="userPositionCodeId" required />
        </div>
        <div>
          <Label className="text-lg">이메일</Label>
          <TextInput type="email" name="userEmail" required />
        </div>
        <div>
          <Label className="text-lg">전화번호</Label>
          <TextInput type="text" name="userTel" required/>
        </div>
        <div>
          <Label className="text-lg">주소</Label>
          <TextInput
            type="text"
            name="userAddress"
            placeholder="주소"
            required
          />
        </div>
        <div className="flex gap-4">
          <div className="">
            <Radio id="male" name="userGender" value="M" />
            <Label htmlFor="male">남성</Label>
          </div>
          <div>
            <Radio id="female" name="userGender" value="F" />
            <Label htmlFor="female">여성</Label>
          </div>
        </div>
        <Button className="mt-10" type="submit">
          구성원 추가
        </Button>
      </Form>
    </dialog>,
    document.getElementById('modal')! as HTMLElement,
  )
})

export default Signup
