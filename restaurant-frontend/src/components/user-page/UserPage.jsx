import './UserPage.css'
import MakeReservation from "./MakeReservation.jsx";
import NextReservation from "./NextReservation.jsx";
import ReservationList from "../reservation-list/ReservationList.jsx";
import {RefreshProvider} from "./RefreshContext.jsx";

function UserPage({refProp}) {
    return <RefreshProvider>
        <div className="grid-container">
            <ReservationList></ReservationList>
            <section className="grid-item2 card-container">
                <NextReservation/>
            </section>
            <section ref={refProp} className="grid-item3">
                <MakeReservation></MakeReservation>
            </section>
        </div>
    </RefreshProvider>
}

export default UserPage;