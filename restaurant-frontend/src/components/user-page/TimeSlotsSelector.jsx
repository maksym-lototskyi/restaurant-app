export default function TimeSlotsSelector({loading, timeSlots}){
    return <>
        {loading ? <p>Loading...</p> : timeSlots.length === 0 ? <p>No time slots were found</p> :
            <div className="time-slots">
                {timeSlots.map((slot, i) => (
                    <button key={i} className="secondary-button confirm-button">{slot.toString().substring(0, 5)}</button>))}
            </div>}
    </>
}