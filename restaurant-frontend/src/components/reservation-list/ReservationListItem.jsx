function ReservationListItem({ reservation, onClick}) {
    const start = new Date(reservation.startTime);
    const end = new Date(reservation.endTime);

    return (
        <li className="reservation-item" onClick={onClick}>
                <strong>{start.toLocaleDateString()}</strong>
                <div>
                    {start.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })}
                    {" – "}
                    {end.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })}
                </div>

            <span>{reservation.numberOfGuests} guests</span>
        </li>
    );
}

export default ReservationListItem;