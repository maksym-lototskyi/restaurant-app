import './QuickActions.css'
import {FiUser} from "react-icons/fi";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../AuthContext.jsx";
import {logout} from "../../api.js";
import Dialog from "../Dialog.jsx";

function QuickActions() {
    const [isScrolled, setIsScrolled] = useState(false);
    const [showDialog, setShowDialog] = useState(false);
    const navigate = useNavigate();
    const {user, refreshUser} = useAuth();

    useEffect(() => {
        const handleScrolled = () => {
            if (window.scrollY > 0) setIsScrolled(true);
            else setIsScrolled(false);
        }

        window.addEventListener("scroll", handleScrolled);

        return () => window.removeEventListener("scroll", handleScrolled);
    }, []);

    const handleLogout = async () => {
        await logout();
        await refreshUser();
        navigate('/');
    }

    return (<nav className={`quick-actions ${isScrolled ? 'scrolled' : ''}`}>
        {!user ? <>
                <button className="quick-actions-button" onClick={() => navigate("/login")}>Login</button>
                <button className="quick-actions-button">Register</button>
            </> :
            <>
                {user.roles.includes("ROLE_ADMIN") ?
                    <>
                        <button className="quick-actions-button" onClick={() => navigate("/reservations")}>Reservations</button>
                        <button className="quick-actions-button" onClick={() => navigate("/users")}>Users</button>
                        <button className="quick-actions-button" onClick={() => navigate("/tables")}>Tables</button>
                    </> : null}
                <Dialog isOpen={showDialog} onClose={() => setShowDialog(false)} onConfirm={handleLogout}
                        title="Confirmation">
                    <p>Are you sure you want to logout?</p>
                </Dialog>
                <button className="quick-actions-button" onClick={() => setShowDialog(true)}>Logout</button>
                <button className="quick-actions-button"><FiUser size={24} onClick={() => navigate("/profile")}/>
                </button>
            </>
        }
    </nav>)
}

export default QuickActions