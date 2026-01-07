import './TopBanner.css'
import {useAuth} from "../auth/AuthContext.jsx";

function TopBanner({scrollToTarget}) {
    const {isAuthenticated} = useAuth();

    return <div className="top-banner">
        <div className="top-banner-background"></div>
        <div className="top-banner-foreground">

            <div className="top-banner-content">
                <h1 className="welcome-text">Reserve. Relax. Enjoy. <br/>
                    Book your table in <span style={{color: "red"}}>seconds</span>.</h1>
                {isAuthenticated ? <button className="book-button" onClick={scrollToTarget}>Make a reservation</button> : null}
            </div>

            <div className="top-banner-image"></div>
        </div>
    </div>
}

export default TopBanner