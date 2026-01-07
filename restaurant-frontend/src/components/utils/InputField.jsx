import './InputField.css'

function InputField({ label, name, inputType, value, handleChange, placeholder, error }) {
    return (
        <div className={`input-field ${error ? "input-field--error" : ""}`}>
            <label className="input-field__label" htmlFor={name}>
                {label}
            </label>

            <input
                id={name}
                name={name}
                type={inputType}
                value={value}
                placeholder={placeholder}
                onChange={handleChange}
                className="input-field__input"
            />

            {error && <p className="input-field__error-text">{error}</p>}
        </div>
    );
}


export default InputField;