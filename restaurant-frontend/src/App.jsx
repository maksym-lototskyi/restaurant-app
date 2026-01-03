import './App.css'
import UserPage from "./components/user-page/UserPage.jsx";
import {useState} from "react";
import ReservationDetails from "./components/ReservationDetails.jsx";
import QuickActions from "./components/header/QuickActions.jsx";
import NabBar from "./components/header/TopBanner.jsx";

function App() {
    const [view, setView] = useState("home");
    const [selectedReservationId, setSelectedReservationId] = useState(null);

    const handleViewDetails = (id) => {
        setSelectedReservationId(id);
        setView("reservation");
    };

    return (
        <>
            <QuickActions/>
            <header>
                {view === 'home' ? <NabBar/> : null}
            </header>
            <main>
                {view === 'home' && <UserPage onViewDetails={handleViewDetails} />}
                {view === 'reservation' && selectedReservationId && (
                    <ReservationDetails id={selectedReservationId} onBack={() => setView("home")} />
                )}
            </main>
        </>
    );
}

export default App
