import './UserPage.css'
import ReservationScheduler from "./ReservationScheduler.jsx";
import NextReservation from "./NextReservation.jsx";
import ReservationList from "../reservation-list/ReservationList.jsx";

function UserPage({refProp}) {
    return <div className="grid-container">
        <ReservationList
            title="My reservations"
            fetchReservations={({ page, size }) =>
                fetch(`/reservations?page=${page}&size=${size}`, {
                    credentials: "include"
                }).then(r => r.json())
            }
        />
        <section className="grid-item2 card-container">
                <NextReservation/>
            </section>
            <section ref={refProp} className="grid-item3">
                <ReservationScheduler></ReservationScheduler>
            </section>
        </div>
}

export default UserPage;