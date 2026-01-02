import WeeklyReservationsChart from "./WeeklyReservationsChart.jsx";
import './UserPage.css'
import {useEffect, useState} from "react";
import Field from "./Field.jsx";
import MakeReservation from "./MakeReservation.jsx";

const data = [
    {day: "Monday", reservations: 10},
    {day: "Tuesday", reservations: 12},
    {day: "Wednesday", reservations: 8},
    {day: "Thursday", reservations: 15},
    {day: "Friday", reservations: 20},
    {day: "Saturday", reservations: 24},
    {day: "Sunday", reservations: 14}
]

function UserPage({onViewDetails}) {
    const [nextReservation, setNextReservation] = useState(null);
    const [loadingReservation, setLoadingReservation] = useState(true);

    useEffect(() => {
        fetch("http://localhost:8080/reservations/1d3fd690-5180-4e7a-9ad0-940e44e896c6/next")
            .then(res => {
                if (res.ok) {
                    return res.json();
                } else {
                    setNextReservation(null);
                    setLoadingReservation(false);
                    return null;
                }
            })
            .then(data => {
                if (data) {
                    setNextReservation(data);
                }
                setLoadingReservation(false);
            })
            .catch(err => {
                console.error(err);
                setNextReservation(null);
                setLoadingReservation(false);
            });
    }, []);

    let start;
    let end;
    if (nextReservation) {
        start = new Date(nextReservation.startTime);
        end = new Date(nextReservation.endTime);
    }


    return <div className="grid-container">
        <div className="grid-item1 card-container">
            <h3 className="card-header center-aligned">Popular days</h3>
            <div className="card-body center-aligned">
                <WeeklyReservationsChart data={data}></WeeklyReservationsChart>
            </div>
        </div>
        <div className="grid-item2 card-container">
            {loadingReservation ? <p>Loading...</p> :
                <>
                    <h3 className="card-header center-aligned">Your next reservation</h3>
                    <div className="card-body center-aligned">
                        {nextReservation ? <>
                                <Field label="Number of Guests">{nextReservation.numberOfGuests}</Field>
                                <Field label="Date">{start.toLocaleDateString()}</Field>

                                <Field label="Time">
                                    {start.toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"})}
                                    {" – "}
                                    {end.toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"})}
                                </Field>
                            </>
                            : <p>No upcoming reservations</p>}
                    </div>
                    <div className="card-footer">
                        <button onClick={nextReservation ? () => onViewDetails(nextReservation.id) : null}
                                className="secondary-button">{nextReservation ? `View
                            Details >` : `Make a reservation >`}</button>
                    </div>
                </>
            }
        </div>
            <MakeReservation></MakeReservation>
    </div>
}

export default UserPage;