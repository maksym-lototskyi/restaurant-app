import WeeklyReservationsChart from "./WeeklyReservationsChart.jsx";

const data = [
    {day : "Monday", reservations : 10},
    {day : "Tuesday", reservations : 12},
    {day : "Wednesday", reservations : 8},
    {day : "Thursday", reservations : 15},
    {day : "Friday", reservations : 20},
    {day : "Saturday", reservations : 24},
    {day : "Sunday", reservations : 14}
]

function UserPage() {
    return <div className="grid-container">
        <div className="grid-item1 grid-item" style={{padding:20, display: 'flex', justifyContent:'center', alignItems: 'center', flexDirection : "column", gap : "1rem"}}>
            <h2>Popular days</h2>
            <WeeklyReservationsChart data={data}></WeeklyReservationsChart>
        </div>
        <div className="grid-item2 grid-item"></div>
        <div className="grid-item3 grid-item"></div>
    </div>
}

export default UserPage;