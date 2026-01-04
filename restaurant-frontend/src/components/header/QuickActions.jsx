import './QuickActions.css'
import {FiUser} from "react-icons/fi";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

function QuickActions(){
    const [isScrolled, setIsScrolled] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const handleScrolled = () =>{
            if(window.scrollY > 0) setIsScrolled(true);
            else setIsScrolled(false);
        }

        window.addEventListener("scroll", handleScrolled);

        return () => window.removeEventListener("scroll", handleScrolled);
    }, []);

    return (<div className={`quick-actions ${isScrolled ? 'scrolled' : ''}`}>
        <button onClick={() => navigate("/login")}>Login</button>
        <button>Register</button>
        <button>
            <FiUser size={24}/>
        </button>
    </div>)
}

export default QuickActions