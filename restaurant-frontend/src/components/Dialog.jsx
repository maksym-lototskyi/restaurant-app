import SecondaryButton from "./SecondaryButton.jsx";

function Dialog({isOpen, onClose, onConfirm, title, children}) {
    if (!isOpen) {
        return null;
    }
    return (
        <div className="dialog-container">
            <div className="dialog card-container">
                <h1 className="card-header">{title}</h1>
                <div className="card-body start-aligned">
                    {children}
                </div>
                <div className="card-footer">
                    <SecondaryButton onClick={onClose}>Close</SecondaryButton>
                    <SecondaryButton type='confirm' className="secondary-button confirm-button" onClick={onConfirm}>Confirm</SecondaryButton>
                </div>
            </div>
        </div>
    )
}

export default Dialog;