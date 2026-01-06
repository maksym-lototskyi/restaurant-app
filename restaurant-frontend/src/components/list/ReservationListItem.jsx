import ListItemElement from "./ListItemElement.jsx";

function ReservationListItem({reservation, onClick}) {
    const start = new Date(reservation.startTime);
    const end = new Date(reservation.endTime);

    return (
        <li className="list-item" onClick={onClick}>
            <ListItemElement value={<strong>{start.toLocaleDateString()}</strong>}/>
            <ListItemElement value={`${start.toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"})}
                 – 
                ${end.toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"})}`}/>

            <ListItemElement value={`${reservation.numberOfGuests} guests`}/>
        </li>
    );
}

export default ReservationListItem;