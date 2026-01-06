import './UserPage.css'
import ReservationScheduler from "./ReservationScheduler.jsx";
import NextReservation from "./NextReservation.jsx";
import List from "../list/List.jsx";
import ReservationListItem from "../list/ReservationListItem.jsx";
import {useNavigate} from "react-router-dom";

function UserPage({refProp}) {
    const navigate = useNavigate();
    return <div className="grid-container">
        <section className="grid-item1 card-container">
            <h3 className="card-header center-aligned">My reservations</h3>
            <List
                fetchItems={({page, size}) =>
                    fetch(`http://localhost:8080/reservations?page=${page}&size=${size}`, {
                        credentials: "include"
                    }).then(r => {
                        return r.json()
                    })
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
            <ReservationScheduler></ReservationScheduler>
        </section>
    </div>
}

export default UserPage;