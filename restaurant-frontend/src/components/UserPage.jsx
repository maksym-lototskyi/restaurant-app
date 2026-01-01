import WeeklyReservationsChart from "./WeeklyReservationsChart.jsx";
import './UserPage.css'

const data = [
    {day : "Monday", reservations : 10},
    {day : "Tuesday", reservations : 12},
    {day : "Wednesday", reservations : 8},
    {day : "Thursday", reservations : 15},
    {day : "Friday", reservations : 20},
    {day : "Saturday", reservations : 24},
    {day : "Sunday", reservations : 14}
]

function UserPage({onViewDetails}) {
    return <div className="grid-container">
        <div className="grid-item1 grid-item">
            <h2>Popular days</h2>
            <WeeklyReservationsChart data={data}></WeeklyReservationsChart>
        </div>
        <div className="grid-item2 grid-item">
            <h2>Your next reservation</h2>
            <button onClick={onViewDetails} className="secondary-button">View Details &gt;</button>
        </div>
        <div className="grid-item3 grid-item"></div>
    </div>
}

export default UserPage;