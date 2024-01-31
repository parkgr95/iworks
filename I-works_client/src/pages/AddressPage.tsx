import { useRef } from 'react'
import SignupPage, { SignupRef } from './SignupPage' // Signup 컴포넌트의 ref 타입을 가져옴
import { Button } from 'flowbite-react'

function AddressPage() {
  const dialog = useRef<SignupRef>(null) // useRef의 제네릭에 SignupRef 타입 추가

  function handleModal() {
    dialog.current?.open() // null 체크를 통한 안전한 호출

    // 혹시 모달이 열렸을 때 추가로 수행해야 할 작업이 있다면 여기에 추가
  }

  return (
    <>
      <h1 className="text-xl">Address Page</h1>
      {/* ref 속성을 통해 ref를 전달 */}
      <SignupPage ref={dialog} />
      <Button onClick={handleModal}>구성원 추가</Button>
    </>
  )
}

export default AddressPage
