import axios, { AxiosResponse } from 'axios';
import { useState, FormEvent } from 'react';
import { useLoaderData, Form } from 'react-router-dom';
import { TextInput, Label, Button } from 'flowbite-react';

interface UserDetailData {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  height: string;
  weight: string;
}

const API_URL = 'https://dummyjson.com/users/1';

function MyPage() {
  const userDetailLoad = useLoaderData() as UserDetailData;
  const [userDetail, setUserDetail] = useState<UserDetailData>(userDetailLoad);

  function handleInfoChange(key: string, value: string) {
    setUserDetail((prevInfo) => ({
      ...prevInfo,
      [key]: value,
    }));
  }

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();

    const fd = new FormData(event.target as HTMLFormElement);

    try {
      const response: AxiosResponse<UserDetailData> = await axios.put(
        API_URL,
        fd,
        // {
        //   headers: {
        //     'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        //   }
        // }
      );

      console.log(response.data);
    } catch (error) {
      alert(error);
    }
  }

  return (
    <div className="p-16">
      <h1 className="text-2xl m-4">마이페이지</h1>
      <p>아이디 : {userDetail.id}</p>
      <p>이름 : {userDetail.firstName}</p>
      <p>성 : {userDetail.lastName}</p>
      <p>이메일 : {userDetail.email}</p>
      <Form onSubmit={handleSubmit}>
        <Label htmlFor="height">키</Label>
        <TextInput
          id="height"
          type="text"
          name="userHeight"
          onChange={(e) => handleInfoChange('height', e.target.value)}
          value={userDetail.height}
        />
        <Label htmlFor="weight">몸무게</Label>
        <TextInput
          id="weight"
          type="text"
          name="userWeight"
          onChange={(e) => handleInfoChange('weight', e.target.value)}
          value={userDetail.weight}
        />
        <Button className="my-8" type="submit">
          수정
        </Button>
      </Form>
    </div>
  );
}

export default MyPage;

async function detailLoader() {
  try {
    const response: AxiosResponse<UserDetailData> = await axios.get(API_URL);

    return response.data;
  } catch (error) {
    console.error('데이터를 불러오는 도중 오류 발생:', error);
    throw error; // 오류를 호출자에게 전파
  }
}

export { detailLoader };
