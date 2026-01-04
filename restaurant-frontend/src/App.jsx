import './App.css'
import UserPage from "./components/user-page/UserPage.jsx";
import ReservationDetails from "./components/ReservationDetails.jsx";
import QuickActions from "./components/header/QuickActions.jsx";
import TopBanner from "./components/header/TopBanner.jsx";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Login from "./components/Login.jsx";
import {useRef} from "react";

function App() {
    const targetRef = useRef(null);

    const scrollToTarget = () => {
        targetRef.current?.scrollIntoView({ behavior: "smooth"});
    };

    return (
        <Router>
            <QuickActions/>
            <Routes>
                <Route path="/" element={<>
                    <TopBanner scrollToTarget={scrollToTarget}/>
                    <UserPage refProp={targetRef}/>
                </>}/>
                <Route path="/reservations/:id" element={<ReservationDetails/>}/>
                <Route path="/login" element={<Login/>}/>
            </Routes>
        </Router>
    );
}

export default App
