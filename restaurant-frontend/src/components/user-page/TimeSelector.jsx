import {FiChevronDown, FiClock} from "react-icons/fi";
import {extractDateFromString, extractHoursFromTimeString, formatTime} from "../../util/date-time-util.jsx";

export default function TimeSelector({date, time, setTime, type}) {
    return <div className={`wrapper ${type === 'select' ? 'select' : ''}`}>
        <FiClock size={35}/>
        <select className="selector" value={time} onChange={(e) => setTime(e.target.value)}>
            {
                Array.from({length: (20 - extractHoursFromTimeString(formatTime(extractDateFromString(date))))}, (_, i) => {
                    const hour = i + extractHoursFromTimeString(formatTime(extractDateFromString(date)));
                    return (<option key={hour} value={`${hour.toString().padStart(2, "0")}:00`}>
                        {hour.toString().padStart(2, "0")}:00
                    </option>);
                })}
        </select>
        <FiChevronDown size={20} className="right-icon"/>
    </div>
}