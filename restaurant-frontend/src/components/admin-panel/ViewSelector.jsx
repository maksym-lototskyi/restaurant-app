import './ViewSelector.css';
import ViewSelectorItem from "./ViewSelectorItem.jsx";

export default function ViewSelector({view, setView}) {
    return <div className="view-selector">
        <ViewSelectorItem onClick={() => setView("reservations")} active={view === "reservations"} name="Reservations"/>
        <ViewSelectorItem onClick={() => setView("tables")} active={view === "tables"} name="Tables"/>
        <ViewSelectorItem onClick={() => setView("users")} active={view === "users"} name="Users"/>
    </div>
}