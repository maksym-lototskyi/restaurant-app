import './Field.css'

function Field({label, children}){
    return (
        <div className="field">
            <span className="label">{label}</span>
            <span className="value">{children}</span>
        </div>
    )
}

export default Field;