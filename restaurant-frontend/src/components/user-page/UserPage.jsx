import './UserPage.css'
import ReservationScheduler from "./ReservationScheduler.jsx";
import NextReservation from "./NextReservation.jsx";
import List from "../list/List.jsx";
import ReservationListItem from "../list/ReservationListItem.jsx";
import {useNavigate} from "react-router-dom";
import {findClosestDate, formatDate, formatTime} from "../../util/date-time-util.jsx";
import {useState} from "react";
import {validateReservationEdit} from "../../validation/validateReservations.js";
import InternalError from "../error/InternalError.jsx";

function UserPage({refProp}) {
    const navigate = useNavigate();
    const [error, setError] = useState(null);
    const [status, setStatus] = useState(null);

    if(status === 'internal') return <InternalError/>

    return <div className="grid-container">
        <section className="grid-item1 card-container">
            <h3 className="card-header center-aligned">My reservations</h3>
            <List
                fetchItems={async ({page, size}) => {
                    try {
                        const res = await fetch(`http://localhost:8080/reservations?page=${page}&size=${size}`, {
                            credentials: "include"
                        });
                        if(res.status === 500) return setStatus('internal');
                        return res.json();
                    }
                    catch(err) {
                        console.log(err);
                    }
                }
                }
                renderItem={item => (
                    <ReservationListItem
                        key={item.id}
                        reservation={item}
                        onClick={() => navigate(`/reservations/${item.id}`)}
                    />
                )}
            />

        </section>
        <section className="grid-item2 card-container">
            <NextReservation/>
        </section>
        <section ref={refProp} className="grid-item3">
            <ReservationScheduler
                initTime={formatTime(findClosestDate())}
                initDate={formatDate(findClosestDate())}
                initGuests={1}
                title="Make a reservation"
                onSchedule={async (date, selectedTimeSlot, guests) => {
                    const validationResult = validateReservationEdit(date, selectedTimeSlot, guests);
                    if(!validationResult.valid) {
                        setError(validationResult.error);
                        return;
                    }
                    setError(null);

                    const res = await fetch("http://localhost:8080/reservations",
                        {method: "POST",
                            credentials : "include",
                            headers: {"Content-Type": "application/json"},
                            body: JSON.stringify(
                                {reservationStart: `${date}T${selectedTimeSlot}`,
                                    numberOfGuests: guests}
                            )
                        });

                    if(!res.ok) {
                        if(res.status === 401) {
                            navigate("/login");
                            return;
                        }
                        const apiError = await res.json();
                        setError(apiError.message);
                    }
                }}
            ></ReservationScheduler>
            {error && <div className="error-banner">{error}</div>}
        </section>
    </div>
}

export default UserPage;