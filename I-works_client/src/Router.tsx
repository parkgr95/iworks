import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import RootLayout from './pages/RootLayout';
import LoginPage from './pages/LoginPage';
import AddressPage from "./pages/AddressPage";
import MyPage from "./pages/MyPage";

import BoardPage from './pages/BoardPage'
import BoardIndex from './pages/boards/BoardIndex'
import BoardList from './pages/boards/BoardList'
import BoardCreate from './pages/boards/BoardCreate'
import BoardUpdate from './pages/boards/BoardUpdate'
import BoardDetail from './pages/boards/BoardDetail'
import BoardSearch from './pages/boards/BoardSearchPage'
import BoardMy from './pages/boards/BoardMy'
import BoardNew from './pages/boards/BoardNew'
import BoardBookmark from './pages/boards/BoardBookmark'

import SchedulePage from './pages/SchedulePage'
import { getMyPageData } from './utils/User'

import AddressIndex from './pages/addresses/AddressIndex'
import AddressSelect from './components/AddressSelect'
import AddressList from './pages/addresses/AddressList'
import GroupCreate from './pages/addresses/GroupCreate'
import GroupList from './pages/addresses/GroupList'
import GroupDetail from './pages/addresses/GroupDetail'
import GroupUpdate from './pages/addresses/GroupUpdate'

import CalendarPage from './pages/CalendarPage';
import CalendarIndex from './pages/calendars/CalendarIndex';

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
import AdminGroupsCreate from './pages/admins/AdminGroupsCreate';
import AdminGroupsUpdate from './pages/admins/AdminGroupsUpdate';

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      {
        path: 'user',
        children: [
          {
            path: 'login',
            element: <LoginPage />,
          },
          {
            path: 'mypage',
            element: <MyPage />,
            loader: getMyPageData,
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
            element: <AddressIndex />,
          },
          {
            path: ':departmentId',
            element: <AddressList />,
          },
          {
            path: 'create',
            element: <GroupCreate />,
          },
          {
            path: 'group',
            element: <GroupList />,
          },
          {
            path: 'group/:groupId',
            element: <GroupDetail />,
          },
          {
            path: 'group/update/:groupId',
            element: <GroupUpdate />,
          },
        ],
      },
      // 게시판 라우터
      {
        path: 'board',
        element: <BoardPage />,
        children: [
          {
            path: '',
            element: <BoardIndex />,
          },
          {
            path: ':boardCategoryCodeId/:boardOwnerId',
            element: <BoardList />,
          },
          {
            path: 'create',
            element: <BoardCreate />,
          },
          {
            path: 'update/:boardId',
            element: <BoardUpdate />,
          },
          {
            path: ':boardId',
            element: <BoardDetail />,
          },
          {
            path: 'search/:searchKeyword',
            element: <BoardSearch />,
          },
          {
            path: 'my/:userId',
            element: <BoardMy />,
          },
          {
            path: 'new',
            element: <BoardNew />,
          },
          {
            path: 'bookmark',
            element: <BoardBookmark />,
          },
        ],
      },
      {
        path: 'calendar',
        element: <CalendarPage />,
        children: [
          {
            path: '',
            element: <CalendarIndex />
          },
        ]
      },
      {
        path: 'schedule',
        element: <SchedulePage />,
        loader: getMyPageData,
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
            path: 'groups/create',
            element: <AdminGroupsCreate />
          },
          {
            path: 'groups/update/:groupId',
            element: <AdminGroupsUpdate />
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
  // 공통 라우터
  {
    path: '/popup/address/select',
    element: <AddressSelect />,
  }
])

function AppProvider() {
  return <RouterProvider router={router} />
}

export default AppProvider
