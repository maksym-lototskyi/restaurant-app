function Dialog({isOpen, onClose, onConfirm, title, content}) {
    if (!isOpen) {
        return null;
    }
    return (
        <div className="dialog-container">
            <div className="dialog card-container">
                <h1 className="card-header">{title}</h1>
                <div className="card-body center-aligned">
                    <p>{content}</p>
                </div>
                <div className="card-footer">
                    <button className="secondary-button" onClick={onClose}>Close</button>
                    <button className="secondary-button confirm-button" onClick={onConfirm}>Confirm</button>
                </div>
            </div>
        </div>
    )
}

export default Dialog;