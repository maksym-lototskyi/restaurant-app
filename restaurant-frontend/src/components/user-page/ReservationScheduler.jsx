import {useEffect, useState} from "react";
import {formatDate, findClosestDate, formatTime} from "../../util/date-time-util.jsx"
import TimeSelector from "./TimeSelector.jsx";
import DateSelector from "./DateSelector.jsx";
import GuestSelector from "./GuestSelector.jsx";
import TimeSlotsSelector from "./TimeSlotsSelector.jsx";
import Dialog from "../Dialog.jsx";
import ReservationSummary from "./ReservationSummary.jsx";
import {useRefresh} from "./RefreshContext.jsx";


function ReservationScheduler() {
    const [time, setTime] = useState(formatTime(findClosestDate()));
    const [guests, setGuests] = useState(1);
    const [date, setDate] = useState(formatDate(findClosestDate()));
    const [timeSlots, setTimeSlots] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showDialog, setShowDialog] = useState(false);
    const [selectedTimeSlot, setSelectedTimeSlot] = useState(null);
    const {version, triggerRefresh} = useRefresh();

    useEffect(() => {
        const params = new URLSearchParams({
            startDate: date, preferredStartTime: time, numberOfGuests: guests, pageSize: 6
        });

        fetch(`http://localhost:8080/availability?${params.toString()}`)
            .then(response => response.json())
            .then(data => {
                setTimeSlots(data)
                setLoading(false);
            })
    }, [date, time, guests, version]);

    const handleReservationCreate = async () => {
        setShowDialog(false);

        const res = await fetch("http://localhost:8080/reservations",
            {method: "POST",
                credentials : "include",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(
                    {reservationStart: `${date}T${selectedTimeSlot}`,
                        numberOfGuests: guests}
                )
            });
        await res.json();
        triggerRefresh();
    };

    return (<>
        <Dialog isOpen={showDialog} onClose={() => {setShowDialog(false)}} onConfirm={handleReservationCreate}
                title="You are almost done">
            <p>Please confirm the details below</p>
            <ReservationSummary time={selectedTimeSlot} date={date} guests={guests}></ReservationSummary>
        </Dialog>
        <h3 className="card-header center-aligned">Make a reservation</h3>
        <div className="grid-wrapper">
            <GuestSelector guests={guests} setGuests={setGuests}/>
            <TimeSelector time={time} setTime={setTime} date={date} type="select"/>
            <DateSelector date={date} setDate={setDate} setTime={setTime}/>
        </div>
        <h4 className="time-slots-header">Select a time slot</h4>
        <TimeSlotsSelector loading={loading} timeSlots={timeSlots} onSelect={(e) => {
            setSelectedTimeSlot(e.target.value)
            setShowDialog(true)
        }}/>
    </>);
}

export default ReservationScheduler;