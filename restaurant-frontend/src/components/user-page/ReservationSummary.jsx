import {FiCalendar, FiClock, FiUser} from "react-icons/fi";
import ReservationSummaryItem from "./ReservationSummaryItem.jsx";

export default function ReservationSummary({time, date, guests}){
    return <div style={{display: "flex", gap : "1rem"}}>
        <ReservationSummaryItem>
            <FiClock size={34}></FiClock>
            {time}
        </ReservationSummaryItem>
        <ReservationSummaryItem>
            <FiCalendar size={34}></FiCalendar>
            {date}
        </ReservationSummaryItem>
        <ReservationSummaryItem>
            <FiUser size={34}></FiUser>
            {guests} {guests === 1 ? 'person' : 'people'}
        </ReservationSummaryItem>
    </div>
}