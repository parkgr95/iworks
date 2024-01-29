import axios from 'axios';
import { useState, ChangeEvent, FormEvent, useRef, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { Form, useNavigate } from 'react-router-dom';
import { Button, Card, TextInput } from 'flowbite-react';

interface LoginResponse {
  token: string;
}

const API_URL = 'https://dummyjson.com/auth/login';

function LoginPage() {
  const [userEid, setUserEid] = useState<string>('');
  const [userPassword, setUserPassword] = useState<string>('');
  const navigate = useNavigate();
  const dialog = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    // 페이지가 로딩될 때 showModal을 호출하여 모달을 엽니다.
    if (dialog.current) {
      dialog.current.showModal();
    }
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'Escape') {
        // ESC 키를 눌렀을 때 모달을 닫지 않도록 수정
        event.preventDefault();
        event.stopPropagation();
      }
    };

    // ESC 키 이벤트 리스너 추가
    window.addEventListener('keydown', handleKeyDown);

    return () => {
      // 컴포넌트 언마운트 시 이벤트 리스너 제거
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, []);

  const handleLogin = async (event: FormEvent) => {
    event.preventDefault();

    try {
      const response = await axios.post<LoginResponse>(API_URL, {
        username: userEid,
        password: userPassword,
        // userEid,
        // userPassword,
      });

      localStorage.setItem('authToken', response.data.token);
      // localStorage.setItem('accessToken', response.data.accessToken);
      // localStorage.setItem('refreshToken', response.data.refreshToken);
      navigate('/');
    } catch (error) {
      alert('로그인 할 수 없습니다.');
    }
  };

  return createPortal(
    <>
      <div className="modal-overlay"></div>
      <Card className="max-w-sm">
        <dialog ref={dialog} className="modal rounded-lg">
          <h1 className="text-xl font-semibold text-center mb-4">I-Works</h1>
          <Form onSubmit={handleLogin} className="flex flex-col gap-4">
            <TextInput
              onChange={(event: ChangeEvent<HTMLInputElement>) =>
                setUserEid(event.target.value)
              }
              type="text"
              name="userEid"
              id="userEid"
              placeholder="사번"
              required
            />
            <TextInput
              onChange={(event: ChangeEvent<HTMLInputElement>) =>
                setUserPassword(event.target.value)
              }
              type="password"
              name="password"
              id="password"
              placeholder="비밀번호"
              required
            />

            <Button className="bg-[#206a5d]" type="submit">
              Login
            </Button>
          </Form>
        </dialog>
      </Card>
    </>,
    document.getElementById('modal') as HTMLElement,
  );
}

export default LoginPage;
