import {
  createContext,
  useContext,
  useState,
  useEffect,
  ReactNode,
} from 'react';
import axios from 'axios';
import { getAccessToken } from './auth';

// 유저 정보의 타입 정의
interface UserData {
  userId: number
  userIsDeleted: number
  userPosition: string;
  departmentName: string;
  userEid: string;
  userNameFirst: string;
  userNameLast: string;
  userEmail: string;
  userTel: string;
  userAddress: string;
  userGender: string;
  userPassword: string;
}

interface UserProviderProps {
  children: ReactNode;
}

// Context 생성
const UserContext = createContext<UserData | null>(null);

export function UserProvider({ children }: UserProviderProps) {
  const [user, setUser] = useState<UserData | null>(null);

  // 유저 정보를 가져오는 함수
  const accessToken = getAccessToken()

  useEffect(() => {
    async function getUser() {
      if (!accessToken) {
        console.log("로그인이 필요합니다")
        return
      }

      try {
        const res = await axios.get(`https://suhyeon.site/api/user/mypage`, {
          headers: {
            Authorization: 'Bearer ' + accessToken,
          },
        })
        setUser(res.data.data);
      } catch (err) {
        console.log(err);
      }
    }

    getUser();
  }, [accessToken])

  return <UserContext.Provider value={user}>{children}</UserContext.Provider>
}

// Custom Hook으로 간단하게 Context 사용
export const useUser = (): UserData | null => useContext(UserContext);