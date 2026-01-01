import profile from "../assets/user.png";
import './QuickActions.css'
import {useEffect, useState} from "react";

function QuickActions(){
    const [isScrolled, setIsScrolled] = useState(false);

    useEffect(() => {
        const handleScrolled = () =>{
            if(window.scrollY > 0) setIsScrolled(true);
            else setIsScrolled(false);
        }

        window.addEventListener("scroll", handleScrolled);

        return () => window.removeEventListener("scroll", handleScrolled);
    }, []);

    return (<div className={`quick-actions ${isScrolled ? 'scrolled' : ''}`}>
        <button>Login</button>
        <button>Register</button>
        <button>
            <img src={profile} alt="Profile"/>
        </button>
    </div>)
}

export default QuickActions