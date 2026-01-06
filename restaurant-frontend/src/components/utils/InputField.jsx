import './Field.css'

function InputField ({label, name, inputType, value, handleChange, placeholder, error}) {
    return (
        <div className="field">
            <span className="label">{label}</span>
            <input placeholder={placeholder} name={name} onChange={handleChange} type={inputType} value={value} />
            <p style={{color : "red", fontSize : "medium"}}>{error}</p>
        </div>
    )
}

export default InputField;