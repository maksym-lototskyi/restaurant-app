import profile from '../assets/user.png';

function NabBar(){
    return <div>
        <div className="quick-actions">
            <button>Login</button>
            <button>Register</button>
            <button>
                <img src={profile} style={{width: 24, fill : "white"}} alt="Profile"/>
            </button>
        </div>
        <nav className="nav-bar">
            <h1 className="welcome-text">Your table is waiting</h1>
            <button className="book-button">Make a reservation</button>
        </nav>
    </div>
}

export default NabBar