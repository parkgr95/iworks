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
import BoardMy from './pages/boards/BoardMy';

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
          {
            path: 'my/:userId',
            element: <BoardMy />
          },
        ]
      },
      // 캘린더 라우터
      // {
      //   path: 'schedule',
      //   element: <SchedulePage />
      // },
    ]
  },
]);

function AppProvider() {
  return <RouterProvider router={router} />;
}

export default AppProvider;
