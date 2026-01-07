import { useState } from "react";
import { useNavigate } from "react-router-dom";
import InputField from "./utils/InputField.jsx";
import SecondaryButton from "./utils/SecondaryButton.jsx";
import {validateUser} from "../validation/validateUser.js";

export default function Register() {
    const [form, setForm] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: ""
    });

    const [errors, setErrors] = useState({});
    const [error, setError] = useState(null);

    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
        setErrors({ ...errors, [e.target.name]: null });
    };

    const handleSubmit = async () => {
        const validationResult = validateUser(form);
        if(Object.keys(validationResult) > 0){
            setErrors({validationResult});
        }
        setError(null);
        setErrors({});

        try {
            const res = await fetch("http://localhost:8080/register", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(form)
            });

            if (!res.ok) {
                const apiError = await res.json();

                setError(apiError.message);

                if (apiError.fieldErrors) {
                    setErrors(apiError.fieldErrors);
                }
                return;
            }

            navigate("/login");
        } catch (err) {
            console.error(err);
            setError("Unexpected error occurred");
        }
    };

    return (
        <div className="card-container center">
            <h2 className="card-header">Register</h2>

            <div className="card-body">
                {error && <div className="error-banner">{error}</div>}

                <InputField
                    inputType="text"
                    label="First Name"
                    name="firstName"
                    value={form.firstName}
                    handleChange={handleChange}
                    error={errors.firstName}
                />

                <InputField
                    inputType="text"
                    label="Last Name"
                    name="lastName"
                    value={form.lastName}
                    handleChange={handleChange}
                    error={errors.lastName}
                />

                <InputField
                    inputType="email"
                    label="Email"
                    name="email"
                    value={form.email}
                    handleChange={handleChange}
                    error={errors.email}
                />

                <InputField
                    inputType="password"
                    label="Password"
                    name="password"
                    value={form.password}
                    handleChange={handleChange}
                    error={errors.password}
                />
            </div>

            <div className="card-footer">
                <SecondaryButton onClick={handleSubmit}>
                    Register
                </SecondaryButton>
                <SecondaryButton type="cancel" onClick={() => navigate("/")}>
                    Cancel
                </SecondaryButton>
            </div>
        </div>
    );
}
