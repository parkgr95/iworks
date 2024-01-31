import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import RootLayout from './pages/RootLayout';
import LoginPage from './pages/LoginPage';
import AddressPage from "./pages/AddressPage";

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
        ],
      },
      {
        path: 'address',
        element: <AddressPage />
      }
      //   {
      //     index: true,
      //     element: <HomePage />,
      //   },
      //   {
      //     path: 'test',
      //     element: <TestPage />,
      //   },
      //   {
      //     path: 'logout',
      //     action: logoutAction,
      //   },
      //   {
      //     path: 'board',
      //     element: <BoardPage />,
      //     children: [
      //       {
      //         path: '',
      //         element: <BoardIndex />
      //       },
      //       {
      //         path: 'create',
      //         element: <BoardCreate />
      //       },
      //       {
      //         path: 'update/:boardId',
      //         element: <BoardUpdate />
      //       },
      //       {
      //         path: ':boardId',
      //         element: <BoardDetail />
      //       },
      //     ]
      //   }

    //   {
    //     index: true,
    //     element: <HomePage />,
    //   },
    //   {
    //     path: 'test',
    //     element: <TestPage />,
    //   },
    //   {
    //     path: 'login',
    //     element: <LoginPage />,
    //   },
    //   {
    //     path: 'logout',
    //     action: logoutAction,
    //   },
    //   {
    //     path: 'user',
    //     children: [
    //       {
    //         path: ':id',
    //         element: <UserDetail />
    //       }
    //     ]
    //   },
    //   {
    //     path: 'board',
    //     element: <BoardPage />,
    //     children: [
    //       {
    //         path: '',
    //         element: <BoardIndex />
    //       },
    //       {
    //         path: 'create',
    //         element: <BoardCreate />
    //       },
    //       {
    //         path: 'update/:boardId',
    //         element: <BoardUpdate />
    //       },
    //       {
    //         path: ':boardId',
    //         element: <BoardDetail />
    //       },
    //     ]
    //   }
    ]
  },
]);

function AppProvider() {
  return <RouterProvider router={router} />;
}

export default AppProvider;
