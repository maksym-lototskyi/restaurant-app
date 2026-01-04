import {FiCalendar} from "react-icons/fi";
import {extractDateFromString, formatTime} from "../../util/date-time-util.jsx";

export default function DateSelector({date, setDate, setTime}){
    return <div className="wrapper date">
        <FiCalendar size={35}/>
        <input
            className="selector"
            type="date"
            value={date}
            min={new Date().toISOString().split("T")[0]}
            onChange={(e) => {
                setDate(e.target.value)
                setTime(formatTime(extractDateFromString(e.target.value)))
            }}
        />
    </div>
}