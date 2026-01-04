export default function TimeSlotsSelector({loading, timeSlots, onSelect}){
    return <>
        {loading ? <p>Loading...</p> : timeSlots.length === 0 ? <p>No time slots were found</p> :
            <div className="time-slots">
                {timeSlots.map((slot, i) => (
                    <button onClick={(e) => onSelect(e)
                    } key={i} value={slot.toString().substring(0, 5)} className="secondary-button confirm-button">{slot.toString().substring(0, 5)}</button>))}
            </div>}
    </>
}