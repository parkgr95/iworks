// import axios from "axios"
// import { ChangeEvent, FormEvent, useEffect, useState } from "react"

// import { Form, useNavigate } from "react-router-dom"
// import { Button } from "flowbite-react"
// import { getAccessToken } from "../../utils/auth"
// import { useUser } from "../../utils/userInfo"

// interface UserData {
//   userId: number,
//   userEid: string,
//   userNameFirst: string,
//   userNameLast: string,
//   departmentName: string,
//   departmentId: string,
//   positionCodeName: null,
//   positionCodeId: null,
//   userTel: string,
//   userEmail: string
// }

// function GroupUpdate() {
//   const loginedUser = useUser()
//   const navigate = useNavigate()
//   const [teamName, setTeamName] = useState<string>('')
//   const [teamDescription, setTeamDescription] = useState<string>()

//   const onNameChange = (event: ChangeEvent<HTMLInputElement>) => {
//     setTeamName(event.target.value)
//   }
//   const onDescriptionChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
//     setTeamDescription(event.target.value)
//   }

//   const [teamLeaderData, setteamLeaderData] = useState<UserData[]>([]);
//   const [teamMemberData, setteamMemberData] = useState<UserData[]>([]);

//   // api 요청
//   function handleCreate(event: FormEvent) {
//     event.preventDefault()
//     console.log(loginedUser)
//     const teamLeader = teamLeaderData[0].userId
//     const targetIdArray = teamMemberData.map(user => user.userId);
//     if (!loginedUser) {
//       // 유저 정보가 없을 경우에 대한 처리
//       return <div>Loading...</div>;
//     }

//     if (teamLeader !== loginedUser.userId) {
//       alert("그룹 리더가 로그인한 사용자와 일치하지 않습니다.");
//       return;
//     }
//     axios
//       .post("https://suhyeon.site/api/address/team/create", {
//         "teamName": teamName,
//         "teamLeader": teamLeader,
//         "teamDescription": teamDescription
//       },
//       {
//         headers: {
//           Authorization: 'Bearer ' + getAccessToken(),
//         },
//       })
//       .then((res) => {
//         const teamId = res.data.data.teamId
//         if (!teamId) {
//           console.log(res)
//         }
//         // 그룹리더가 아니면 동작 안함 ==> 리더 선택 창은 작성자가 되어야한다
//         return axios.post(`https://suhyeon.site/api/address/team/user/${teamId}`, targetIdArray,       
//         {
//           headers: {
//             Authorization: 'Bearer ' + getAccessToken(),
//           },
//         });
//       })
//       .then((res) => {
//         navigate("../")
//         console.log(res.data)
//       })
//       .catch((err) => {
//         console.log(err)
//       })
//   }

//   // 팝업창 로직
//   function openPopup1() {
//     const popup: Window | null = window.open('/popup/address/select', '유저 선택', 'width=800,height=700,top=100');

//     if (popup) {
//       // 팝업 창이 닫힐 때 이벤트 리스너 제거
//       popup.addEventListener('beforeunload', () => {
//         window.removeEventListener('message', handleMessage1);
//       })
  
//       // 부모 창으로 데이터를 전송
//       window.postMessage({ type: 'OPEN_POPUP', payload: {} }, window.location.href);
  
//       // 부모 창에서 메시지 수신하여 선택된 유저 정보 업데이트
//       window.addEventListener('message', handleMessage1);
//     } else {
//       console.error('팝업을 열 수 없습니다.')
//     }
//   }

//   const handleMessage1 = (event: MessageEvent) => {
//     const { type, payload } = event.data;
//     if (type === 'SEND_DATA') {
//       // 부모 창에서 전달받은 데이터 처리
//       setteamLeaderData(payload.selectedUsers);
//     }
//   };

//   function openPopup2() {
//     const popup: Window | null = window.open('/popup/address/select', '유저 선택', 'width=800,height=700,top=100');

//     if (popup) {
//       // 팝업 창이 닫힐 때 이벤트 리스너 제거
//       popup.addEventListener('beforeunload', () => {
//         window.removeEventListener('message', handleMessage2);
//       })
  
//       // 부모 창으로 데이터를 전송
//       window.postMessage({ type: 'OPEN_POPUP', payload: {} }, window.location.href);
  
//       // 부모 창에서 메시지 수신하여 선택된 유저 정보 업데이트
//       window.addEventListener('message', handleMessage2);
//     } else {
//       console.error('팝업을 열 수 없습니다.')
//     }
//   }

//   const handleMessage2 = (event: MessageEvent) => {
//     const { type, payload } = event.data;

//     if (type === 'SEND_DATA') {
//       // 부모 창에서 전달받은 데이터 처리
//       setteamMemberData(payload.selectedUsers)
//     }
//   };

//   useEffect(() => {
//     // 컴포넌트가 언마운트될 때 이벤트 리스너 제거
//     return () => {
//       window.removeEventListener('message', handleMessage1);
//       window.removeEventListener('message', handleMessage2);
//     };
//   }, []);



//   return (
//     <div>
//       <p className="text-2xl mb-6">그룹 추가</p>
//       <Form>
//         <div className="flex flex-col my-2">
//           <label className="mb-2 text-lg" htmlFor="teamName">그룹명</label>
//           <input onChange={onNameChange} className="h-8 w-full" type="text" name="teamName" placeholder="그룹명을 입력해주세요." id="teamName" required />
//         </div>
//         <div className="flex flex-col my-2">
//           <label className="mb-2 text-lg" htmlFor="teamDesc">그룹설명</label>
//           <textarea onChange={onDescriptionChange} style={{ height: "70px" }} className="h-8 w-full" name="teamDesc" placeholder="설명을 입력해주세요." id="teamDesc" required />
//         </div>

//         <div>
//           <div className="flex justify-between items-center text-lg mb-2">
//             <h3>그룹 리더</h3>
//             <Button className="bg-mainGreen" onClick={openPopup1}>주소록</Button>
//           </div>
//           {teamLeaderData.length > 0 ? (
//             <div className="flex flex-col border-2 h-40 p-4 overflow-auto">
//               <ul>
//                 {teamLeaderData.map((user) => (
//                   <li key={user.userEid}>{user.userNameLast}{user.userNameFirst}</li>
//                 ))}
//               </ul>
//             </div>
//           ) : (
//             <div className="border-2 h-40"></div>
//           )}
//         </div>
//         <div>
//           <div className="flex justify-between items-center text-lg mb-2 mt-2">
//             <h3>그룹 멤버</h3>
//             <Button className="bg-mainGreen" onClick={openPopup2}>주소록</Button>
//           </div>
//           {teamMemberData.length > 0 ? (
//             <div className="flex flex-col border-2 h-40 p-4 overflow-auto">
//               <ul>
//                 {teamMemberData.map((user) => (
//                   <li className="" key={user.userEid}>{user.userNameLast}{user.userNameFirst} </li>
//                 ))}
//               </ul>
//             </div>
//           ) : (
//             <div className="border-2 h-40"></div>
//           )}
//         </div>
//         <div className="flex justify-end mt-4">
//           <Button className="bg-mainBlue mr-2">취소</Button>
//           <Button onClick={handleCreate} className="bg-mainGreen" type="submit">추가</Button>
//         </div>
//       </Form>
//     </div>
//   )
// }

// export default GroupUpdate;