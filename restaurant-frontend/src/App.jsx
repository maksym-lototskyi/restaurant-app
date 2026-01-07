import './App.css'
import UserPage from "./components/user-page/UserPage.jsx";
import ReservationDetails from "./components/ReservationDetails.jsx";
import QuickActions from "./components/header/QuickActions.jsx";
import TopBanner from "./components/header/TopBanner.jsx";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Login from "./components/auth/Login.jsx";
import {useRef} from "react";
import {useAuth} from "./components/auth/AuthContext.jsx";
import UserProfile from "./components/profile/UserProfile.jsx";
import {RefreshProvider} from "./components/user-page/RefreshContext.jsx";
import AdminPanel from "./components/admin-panel/AdminPanel.jsx";
import TableDetails from "./components/admin-panel/TableDetails.jsx";
import UserDetails from "./components/admin-panel/UserDetails.jsx";
import Register from "./components/auth/Register.jsx";
import CreateTable from "./components/admin-panel/CreateTable.jsx";

function App() {
    const targetRef = useRef(null);
    const {user, isAuthenticated, loading} = useAuth();

    const scrollToTarget = () => {
        targetRef.current?.scrollIntoView({behavior: "smooth"});
    };

    return (
        <Router>
            <RefreshProvider>
                {!loading &&
                    <>
                        <QuickActions/>
                        <Routes>
                            <Route path="/" element={
                                !user?.roles.includes("ROLE_ADMIN") ?
                                    <>
                                        <TopBanner scrollToTarget={scrollToTarget}/>
                                        {isAuthenticated ? <UserPage refProp={targetRef}/> : null}
                                    </> : <AdminPanel/>
                            }/>
                            {isAuthenticated ? <>
                                    <Route path="/profile" element={<UserProfile/>}/>
                                    <Route path="/reservations/:id" element={<ReservationDetails/>}/>
                                </> :
                                <>
                                    <Route path="/register" element={<Register/>}/>
                                    <Route path="/login" element={<Login/>}/>
                                </>
                            }
                            {user?.roles.includes("ROLE_ADMIN") ?
                                <>
                                    <Route path="/tables/:id" element={<TableDetails/>}/>
                                    <Route path="/users/:id" element={<UserDetails/>}/>
                                    <Route path="/tables/new" element={<CreateTable/>}/>
                                </> : null}
                        </Routes>
                    </>
                }
            </RefreshProvider>
        </Router>
    );
}

export default App
