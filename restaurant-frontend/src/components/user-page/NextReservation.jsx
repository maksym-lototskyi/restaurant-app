import Field from "../Field.jsx";
import SecondaryButton from "../SecondaryButton.jsx";
import {useEffect, useState} from "react";

export default function NextReservation({onViewDetails}) {
    const [nextReservation, setNextReservation] = useState(null);
    const [loadingReservation, setLoadingReservation] = useState(true);

    useEffect(() => {
        fetch("http://localhost:8080/reservations/2fcfbf0e-e836-49e6-bfb1-95167ee7deed/next")
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

    return <>
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
                {nextReservation ?
                    <div className="card-footer">
                        <SecondaryButton onClick={() => onViewDetails(nextReservation.id)}>View Details</SecondaryButton>
                    </div> : null}
            </>
        }
    </>
}