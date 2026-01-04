import './UserPage.css'
import MakeReservation from "./MakeReservation.jsx";
import NextReservation from "./NextReservation.jsx";
import ReservationList from "../reservation-list/ReservationList.jsx";
import {RefreshProvider} from "./RefreshContext.jsx";

function UserPage({onViewDetails, customerId, refProp}) {
    return <RefreshProvider>
        <div className="grid-container">
            <ReservationList customerId={customerId} onSelect={onViewDetails}></ReservationList>
            <section className="grid-item2 card-container">
                <NextReservation onViewDetails={onViewDetails} customerId={customerId}/>
            </section>
            <section ref={refProp} className="grid-item3">
                <MakeReservation customerId={customerId}></MakeReservation>
            </section>
        </div>
    </RefreshProvider>
}

export default UserPage;