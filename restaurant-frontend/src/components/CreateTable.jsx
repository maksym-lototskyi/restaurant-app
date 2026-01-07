import { useState } from "react";
import { useNavigate } from "react-router-dom";
import InputField from "./utils/InputField.jsx";
import SecondaryButton from "./utils/SecondaryButton.jsx";
import {validateTableCreate} from "../validation/validateTable.js";

export default function CreateTable() {
    const [form, setForm] = useState({
        tableNumber: "",
        floorNumber: "",
        numberOfSeats: ""
    });

    const [errors, setErrors] = useState({});
    const [error, setError] = useState(null);

    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;

        setForm({
            ...form,
            [name]: value
        });

        setErrors({
            ...errors,
            [name]: null
        });
    };

    const handleSubmit = async () => {
        setError(null);
        setErrors({});

        try {
            const validationErrors = validateTableCreate(form);
            if(Object.keys(validationErrors).length > 0) {
                setErrors(validationErrors);
                return;
            }
            const res = await fetch("http://localhost:8080/tables", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    tableNumber: form.tableNumber,
                    floorNumber: Number(form.floorNumber),
                    numberOfSeats: Number(form.numberOfSeats)
                })
            });

            if (!res.ok) {
                const apiError = await res.json();
                setError(apiError.message);

                if (apiError.fieldErrors) {
                    setErrors(apiError.fieldErrors);
                }
                return;
            }

            const createdTable = await res.json();
            navigate(`/tables/${createdTable.id}`);

        } catch (err) {
            console.error(err);
            setError("Unexpected error occurred");
        }
    };

    return (
        <div className="card-container center">
            <h2 className="card-header">Create Table</h2>

            <div className="card-body">
                {error && <div className="error-banner">{error}</div>}

                <InputField
                    inputType="text"
                    label="Table Number"
                    name="tableNumber"
                    value={form.tableNumber}
                    handleChange={handleChange}
                    error={errors.tableNumber}
                />

                <InputField
                    inputType="number"
                    label="Floor Number"
                    name="floorNumber"
                    value={form.floorNumber}
                    handleChange={handleChange}
                    error={errors.floorNumber}
                />

                <InputField
                    inputType="number"
                    label="Number of Seats"
                    name="numberOfSeats"
                    value={form.numberOfSeats}
                    handleChange={handleChange}
                    error={errors.numberOfSeats}
                />
            </div>

            <div className="card-footer">
                <SecondaryButton onClick={handleSubmit}>
                    Confirm
                </SecondaryButton>
                <SecondaryButton type="cancel" onClick={() => navigate(-1)}>
                    Cancel
                </SecondaryButton>
            </div>
        </div>
    );
}
