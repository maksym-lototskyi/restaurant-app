import './App.css'
import UserPage from "./components/UserPage.jsx";
import {useState} from "react";
import ReservationDetails from "./components/ReservationDetails.jsx";
import QuickActions from "./components/QuickActions.jsx";
import NabBar from "./components/TopBanner.jsx";

function App() {
    const [view, setView] = useState("home");
    const [selectedReservationId, setSelectedReservationId] = useState(null);

    const handleViewDetails = (id) => {
        setSelectedReservationId(id);
        setView("reservation");
    };

    return (
        <>
            <header>
                <QuickActions/>
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
