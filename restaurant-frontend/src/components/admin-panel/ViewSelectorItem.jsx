export default function ViewSelectorItem({onClick, name, active}) {
    return (
        <button onClick={onClick} className={`view-selector-item ${active ? "active" : ""}`}>{name}</button>
    )
}