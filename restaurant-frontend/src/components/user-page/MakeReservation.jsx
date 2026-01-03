import {useEffect, useState} from "react";
import {formatDate, findClosestDate,formatTime} from "../../util.jsx"
import TimeSelector from "./TimeSelector.jsx";
import DateSelector from "./DateSelector.jsx";
import GuestSelector from "./GuestSelector.jsx";
import TimeSlotsSelector from "./TimeSlotsSelector.jsx";


function MakeReservation() {
    const [time, setTime] = useState(formatTime(findClosestDate()));
    const [guests, setGuests] = useState(1);
    const [date, setDate] = useState(formatDate(findClosestDate()));
    const [timeSlots, setTimeSlots] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const params = new URLSearchParams({
            startDate: date, preferredStartTime : time, numberOfGuests: guests, pageNumber: 0, pageSize: 6
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
                <GuestSelector guests={guests} setGuests={setGuests}/>
                <TimeSelector time={time} setTime={setTime} date={date} type="select"/>
                <DateSelector date={date} setDate={setDate} setTime={setTime}/>
            </div>
            <h4 className="time-slots-header">Select a time slot</h4>
        <TimeSlotsSelector loading={loading} timeSlots={timeSlots}/>
        </div>);
}

export default MakeReservation;