import {FiCalendar, FiChevronDown, FiClock, FiUser} from "react-icons/fi";
import {useEffect, useState} from "react";

function findClosestDate() {
    const date = new Date();
    if (date.getHours() >= 20) {
        date.setDate(date.getDate() + 1);
        date.setHours(8, 0, 0, 0);
    }
    return date;
}

function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");

    return `${year}-${month}-${day}`;
}

function formatTime(date) {
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");

    return `${hours}:${minutes}`;
}

function MakeReservation() {
    const closestDate = findClosestDate();

    const [time, setTime] = useState(formatTime(closestDate));
    const [guests, setGuests] = useState(1);
    const [date, setDate] = useState(formatDate(closestDate));
    const [timeSlots, setTimeSlots] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const params = new URLSearchParams({
            startTime: `${date}T${time}:00`, numberOfGuests: guests, pageNumber: 0, pageSize: 5
        });

        fetch(`http://localhost:8080/availability?${params.toString()}`)
            .then(response => response.json())
            .then(data => {
                setTimeSlots(data)
                setLoading(false);
            })
    }, [date, time, guests]);

    return (<div className="grid-item3">
            <h3 className="card-header center-aligned">Make a reservation</h3>
            <div className="grid-wrapper">
                <div className="wrapper guests-picker">
                    <FiUser size={35}/>
                    <select className="grid-item3-input" value={guests}
                            onChange={(e) => setGuests(e.target.value)}>
                        {Array.from({length: 15}, (_, i) => (<option key={i} value={i + 1}>
                                {i + 1} {i === 0 ? 'person' : 'people'}
                            </option>))}
                    </select>
                    <FiChevronDown size={20} className="right-icon"/>
                </div>

                <div className="wrapper select">
                    <FiClock size={35}/>
                    <select className="grid-item3-input" value={time} onChange={(e) => setTime(e.target.value)}>
                        {Array.from({length: 13}, (_, i) => {
                            const hour = i + 8;
                            return (<option key={hour} value={`${hour.toString().padStart(2, "0")}:00`}>
                                    {hour.toString().padStart(2, "0")}:00
                                </option>);
                        })}
                    </select>
                    <FiChevronDown size={20} className="right-icon"/>
                </div>

                <div className="wrapper date">
                    <FiCalendar size={35}/>
                    <input
                        className="grid-item3-input"
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                    />
                </div>
            </div>
            <h4 className="time-slots-header">Select a time slot</h4>
            {loading ? <p>Loading...</p> : timeSlots.length === 0 ? <p>No time slots were found</p> :
                <div className="time-slots">
                    {timeSlots.map((slot, i) => (
                        <button key={i} className="secondary-button confirm-button">{slot}</button>))}
                </div>}
        </div>);
}

export default MakeReservation;