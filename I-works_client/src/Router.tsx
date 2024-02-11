import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import RootLayout from './pages/RootLayout';
import LoginPage from './pages/LoginPage';
import AddressPage from "./pages/AddressPage";
import MyPage, { myPageLoader } from "./pages/MyPage";
import { logoutAction } from './pages/LogoutPage';
import BoardPage from './pages/BoardPage';
import BoardIndex from './pages/boards/BoardIndex';
import BoardList from './pages/boards/BoardList';
import BoardCreate from './pages/boards/BoardCreate';
import BoardUpdate from './pages/boards/BoardUpdate';
import BoardDetail from './pages/boards/BoardDetail';
import BoardSearch from './pages/boards/BoardSearchPage';

import AddressIndex from './pages/addresses/AddressIndex';
import AddressSelect from './components/AddressSelect';
import AddressList from './pages/addresses/AddressList';
import GroupCreate from './pages/addresses/GroupCreate';
import GroupList from './pages/addresses/GroupList';
import GroupDetail from './pages/addresses/GroupDetail';
import GroupUpdate from './pages/addresses/GroupUpdate';

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      //
      {
        path: 'user',
        children: [
          {
            path: 'login',
            element: <LoginPage />,
          },
          {
            path: 'logout',
            action: logoutAction,
          },
          {
            path: 'mypage',
            element: <MyPage />,
            loader: myPageLoader,
          },
        ],
      },
      // 주소록 라우터
      {
        path: 'address',
        element: <AddressPage />,
        children: [
          {
            path: '',
            element: <AddressIndex />
          },
          {
            path: ':departmentId',
            element: <AddressList />
          },
          {
            path: 'create',
            element: <GroupCreate />
          },
          {
            path: 'group',
            element: <GroupList />
          },
          {
            path: 'group/:groupId',
            element: <GroupDetail />
          },
          {
            path: 'group/update/:groupId',
            element: <GroupUpdate />
          },
        ]
      },
      // 게시판 라우터
      {
        path: 'board',
        element: <BoardPage />,
        children: [
          {
            path: '',
            element: <BoardIndex />
          },
          {
            path: ':boardCategoryCodeId/:boardOwnerId',
            element: <BoardList />
          },
          {
            path: 'create',
            element: <BoardCreate />
          },
          {
            path: 'update/:boardId',
            element: <BoardUpdate />
          },
          {
            path: ':boardId',
            element: <BoardDetail />
          },
          {
            path: 'search/:searchKeyword',
            element: <BoardSearch />
          },
        ]
      },
      // 캘린더 라우터
    ]
  },
  // 공통 라우터
  {
    path: '/popup/address/select',
    element: <AddressSelect />
  },
]);

function AppProvider() {
  return <RouterProvider router={router} />;
}

export default AppProvider;
