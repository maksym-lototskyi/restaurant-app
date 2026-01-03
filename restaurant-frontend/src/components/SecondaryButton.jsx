export default function SecondaryButton({type, children, onClick}) {
    return <button className={`secondary-button ${type === 'confirm' ? 'confirm' : ''}`} onClick={onClick}>
        {children}
    </button>
}