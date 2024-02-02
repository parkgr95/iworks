import { Outlet } from 'react-router-dom';
import MainNav from '../components/MainNav';

function RootLayout() {

  return (
    <>
      <header>
        <MainNav />
      </header>
      <main className='h-full'>
        <Outlet />
      </main>
    </>
  );
}

export default RootLayout;
