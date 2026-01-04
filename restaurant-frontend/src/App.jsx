import './App.css'
import UserPage from "./components/user-page/UserPage.jsx";
import {useRef, useState} from "react";
import ReservationDetails from "./components/ReservationDetails.jsx";
import QuickActions from "./components/header/QuickActions.jsx";
import TopBanner from "./components/header/TopBanner.jsx";

function App() {
    const [view, setView] = useState("home");
    const [selectedReservationId, setSelectedReservationId] = useState(null);
    const targetRef = useRef(null);

    const scrollToTarget = () => {
        targetRef.current?.scrollIntoView({ behavior: "smooth"});
    };

    const handleViewDetails = (id) => {
        setSelectedReservationId(id);
        setView("reservation");
    };

    return (
        <>
            <QuickActions/>
            <header>
                {view === 'home' ? <TopBanner scrollToTarget={scrollToTarget}/> : null}
            </header>
            <main>
                {view === 'home' && <UserPage onViewDetails={handleViewDetails} refProp={targetRef} customerId="bba7d89e-70bf-4d12-a482-62ee662b662e"/>}
                {view === 'reservation' && selectedReservationId && (
                    <ReservationDetails id={selectedReservationId} onBack={() => setView("home")} />
                )}
            </main>
        </>
    );
}

export default App
