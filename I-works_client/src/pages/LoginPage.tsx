import axios from 'axios';
import { useState, ChangeEvent, FormEvent, useRef, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { Form, useNavigate } from 'react-router-dom';
import { Button, Card, TextInput } from 'flowbite-react';
import iworks_logo from '../assets/iworks_logo.png';

interface LoginResponse {
  result: string;
  data: {
    grantType: string;
    accessToken: string;
    refreshToken: string;
  };
}

const API_URL = 'https://suhyeon.site/api/user/login';

function LoginPage() {
  const [userEid, setUserEid] = useState<string>('');
  const [userPassword, setUserPassword] = useState<string>('');
  const navigate = useNavigate();
  const dialog = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    if (dialog.current) {
      dialog.current.showModal();
    }
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'Escape') {
        event.preventDefault();
        event.stopPropagation();
      }
    };

    window.addEventListener('keydown', handleKeyDown);

    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, []);

  const handleLogin = async (event: FormEvent) => {
    event.preventDefault();

    try {
      const response = await axios.post<LoginResponse>(API_URL, {
        userEid,
        userPassword,
      });

      localStorage.setItem('accessToken', response.data.data.accessToken);
      localStorage.setItem('refreshToken', response.data.data.refreshToken);
      navigate('/');
    } catch (error) {
      alert('로그인 할 수 없습니다.');
    }
  };

  return createPortal(
    <>
      <div className="w-full h-full fixed bg-[#1f4068]"></div>
      <Card className="max-w-sm">
        <dialog
          ref={dialog}
          className="modal rounded-xl w-1/2 max-w-sm min-w-min p-10 bg-[#EBECF1]"
        >
          <div className="ml-auto mr-auto w-2/3 mb-10">
            <img className="inline-block" src={iworks_logo} alt="logo" />
          </div>
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
