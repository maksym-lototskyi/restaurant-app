import './Field.css'

function InputField ({label, name, inputType, value, handleChange}) {
    return (
        <div className="field">
            <span className="label">{label}</span>
            <input name={name} onChange={handleChange} type={inputType} value={value} />
        </div>
    )
}

export default InputField;