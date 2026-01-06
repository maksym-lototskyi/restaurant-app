export default function SecondaryButton({type, children, onClick}) {
    return <button className={`secondary-button ${type === 'confirm' ? 'confirm-button' : type === 'cancel' ? 'cancel-button' : ''}`} onClick={onClick}>
        {children}
    </button>
}