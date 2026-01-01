import './TopBanner.css'

function NabBar() {
    return <nav className="top-banner">
        <div className="top-banner-background"></div>
        <div className="top-banner-foreground">

            <div className="top-banner-content">
                <h1 className="welcome-text">Reserve. Relax. Enjoy. <br/>
                    Book your table in <span style={{color: "red"}}>seconds</span>.</h1>
                <button className="book-button">Make a reservation</button>
            </div>

            <div className="top-banner-image"></div>
        </div>
    </nav>
}

export default NabBar