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
import AdminPage from './pages/AdminPage';
import AdminIndex from './pages/admins/AdminIndex';
import AdminUsers from './pages/admins/AdminUsers';
import AdminDepartments from './pages/admins/AdminDepartments';
import AdminGroups from './pages/admins/AdminGroups';
import AdminBoards from './pages/admins/AdminBoards';
import AdminSchedules from './pages/admins/AdminSchedules';
import AdminUsersDetail from './pages/admins/AdminUsersDetail';
import AdminBoardsUpdate from './pages/admins/AdminBoardsUpdate';
import AdminBoardsCreate from './pages/admins/AdminBoardsCreate';
import AdminSchedulesUpdate from './pages/admins/AdminSchedulesUpdate';

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
            path:'mypage',
            element: <MyPage />,
            loader: myPageLoader,
          },
        ],
      },
      // 주소록 라우터
      {
        path: 'address',
        element: <AddressPage />
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
      // 관리자 라우터
      {
        path: 'admin',
        element: <AdminPage />,
        children: [
          {
            path: '',
            element: <AdminIndex />
          },
          {
            path: 'users',
            element: <AdminUsers />
          },
          {
            path: 'users/:userId',
            element: <AdminUsersDetail />
          },
          {
            path: 'departments',
            element: <AdminDepartments />
          },
          {
            path: 'groups',
            element: <AdminGroups />
          },
          {
            path: 'boards',
            element: <AdminBoards />
          },
          {
            path: 'boards/create',
            element: <AdminBoardsCreate />
          },
          {
            path: 'boards/update/:boardId',
            element: <AdminBoardsUpdate />
          },
          {
            path: 'schedules',
            element: <AdminSchedules />
          },
          {
            path: 'schedules/update/:scheduleId',
            element: <AdminSchedulesUpdate />
          },
        ]
      },
    ]
  },
]);

function AppProvider() {
  return <RouterProvider router={router} />;
}

export default AppProvider;
