import './UserPage.css'
import MakeReservation from "./MakeReservation.jsx";
import NextReservation from "./NextReservation.jsx";
import ReservationList from "../reservation-list/ReservationList.jsx";

function UserPage({onViewDetails}) {
    return <div className="grid-container">
        <ReservationList customerId="2fcfbf0e-e836-49e6-bfb1-95167ee7deed" onSelect={onViewDetails}></ReservationList>
        <section className="grid-item2 card-container">
            <NextReservation onViewDetails={onViewDetails}/>
        </section>
            <MakeReservation></MakeReservation>
    </div>
}

export default UserPage;