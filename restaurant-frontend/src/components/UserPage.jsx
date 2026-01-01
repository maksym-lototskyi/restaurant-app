import WeeklyReservationsChart from "./WeeklyReservationsChart.jsx";
import './UserPage.css'
import {useEffect, useState} from "react";
import Field from "./Field.jsx";

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
        fetch("http://localhost:8080/reservations/0365f599-96a4-4002-9918-d2d100f9f184/next")
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
            <h2 className="card-header center-aligned">Popular days</h2>
            <div className="card-body center-aligned">
                <WeeklyReservationsChart data={data}></WeeklyReservationsChart>
            </div>
        </div>
        <div className="grid-item2 card-container">
            {loadingReservation ? <p>Loading...</p> :
                <>
                    <h2 className="card-header center-aligned">Your next reservation</h2>
                    <div className="card-body center-aligned">
                        {nextReservation ? <>
                                <Field label="numberOfGuests">{nextReservation.numberOfGuests}</Field>
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
        <div className="grid-item3"></div>
    </div>
}

export default UserPage;