import './App.css'
import UserPage from "./components/UserPage.jsx";
import {useState} from "react";
import ReservationDetails from "./components/ReservationDetails.jsx";
import QuickActions from "./components/QuickActions.jsx";
import NabBar from "./components/TopBanner.jsx";

function App() {
    const [view, setView] = useState("home");
    const views = {
        home: <UserPage onViewDetails={() => setView("reservation")} />,
        reservation: <ReservationDetails id="25f4a95c-b578-4b20-9f13-cf272fc6b594" onBack={() => setView("home")} />
    };

    return (
        <>
            <header>
                <QuickActions/>
                {view === 'home' ? <NabBar/> : null}
            </header>
            <main>
                {views[view]}
            </main>
        </>
    )
}

export default App
