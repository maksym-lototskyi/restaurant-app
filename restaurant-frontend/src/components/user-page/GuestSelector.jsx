import {FiChevronDown, FiUser} from "react-icons/fi";

export default function GuestSelector({guests, setGuests}) {
    return <div className="wrapper guests-picker">
        <FiUser size={35}/>
        <select className="selector" value={guests}
                onChange={(e) => setGuests(e.target.value)}>
            {Array.from({length: 15}, (_, i) => (<option key={i} value={i + 1}>
                {i + 1} {i === 0 ? 'person' : 'people'}
            </option>))}
        </select>
        <FiChevronDown size={20} className="right-icon"/>
    </div>
}