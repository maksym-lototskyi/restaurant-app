import './App.css'
import UserPage from "./components/user-page/UserPage.jsx";
import ReservationDetails from "./components/ReservationDetails.jsx";
import QuickActions from "./components/header/QuickActions.jsx";
import TopBanner from "./components/header/TopBanner.jsx";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Login from "./components/Login.jsx";
import {useRef} from "react";
import {useAuth} from "./AuthContext.jsx";
import UserProfile from "./components/UserProfile.jsx";
import ReservationList from "./components/reservation-list/ReservationList.jsx";
import {RefreshProvider} from "./components/user-page/RefreshContext.jsx";
import RootRedirect from "./components/RootRedirect.jsx";

function App() {
    const targetRef = useRef(null);
    const {user, loading} = useAuth();

    const scrollToTarget = () => {
        targetRef.current?.scrollIntoView({behavior: "smooth"});
    };

    return (
        <Router>
            {!loading ? <>
                <RefreshProvider>
                    <QuickActions/>
                    <Routes>
                        <Route path="/" element={
                            user?.roles.includes("ROLE_USER") ?
                                <>
                                    <TopBanner scrollToTarget={scrollToTarget}/>
                                    <UserPage refProp={targetRef}/>
                                </> : <RootRedirect/>
                        }/>
                        <Route path="/profile" element={<UserProfile/>}/>
                        <Route path="/reservations" element={<ReservationList
                            title="All reservations"
                            fetchReservations={({page, size}) =>
                                fetch(`http://localhost:8080/reservations?page=${page}&size=${size}`, {
                                    credentials: "include"
                                }).then(r => r.json())
                            }
                        />}/>
                        <Route path="/reservations/:id" element={<ReservationDetails/>}/>
                        <Route path="/login" element={<Login/>}/>
                    </Routes>
                </RefreshProvider>
            </> : null
            }
        </Router>
    );
}

export default App
