import { Outlet } from 'react-router-dom';
import MainNav from '../components/MainNav';

function RootLayout() {

	return (
		<>
			<header>
				<MainNav />
			</header>
			<main>
				<Outlet />
			</main>
		</>
	);
}

export default RootLayout;
