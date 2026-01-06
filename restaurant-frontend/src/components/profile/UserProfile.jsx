import Field from "../utils/Field.jsx";
import InputField from "../utils/InputField.jsx";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import SecondaryButton from "../utils/SecondaryButton.jsx";
import {validateUserProfile} from "../../util/validationUtil.js";

function UserProfile() {
    const [user, setUser] = useState(null);
    const [form, setForm] = useState(null);
    const [loading, setLoading] = useState(true);
    const [isEdit, setIsEdit] = useState(false);
    const [errors, setErrors] = useState({});
    const [error, setError] = useState("");

    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        fetch("http://localhost:8080/profile", {
            credentials: "include"
        })
            .then(async res => {
                if(res.ok) return res.json();
                if(res.status === 401) {
                    navigate("/login");
                    return;
                }

                const apiError = await res.json();
                setError(apiError.message || "Something went wrong");
                setErrors(apiError.fieldErrors || {});
            })
            .then(data => {
                if (!data) return;

                setUser(data);
                setForm({...data, password: ""});
                setLoading(false);
            });
    }, [id, navigate]);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleEditConfirm = async () => {
        const {isValid, errors} = validateUserProfile(form);
        if (!isValid) {
            setErrors(errors);
            return;
        }
        await fetch(`http://localhost:8080/profile`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify(form)
        });

        setUser(form);
        setIsEdit(false);
    };

    if (loading) return <p>Loading...</p>;
    if (!user) return <p>User not found</p>;

    return (
        <section className="card-container center">
            <h2 className="card-header">User Profile</h2>

            <div className="card-body start-aligned">
                {isEdit ? (
                    <>
                        {error && <div className="error-banner">{error}</div>}
                        <InputField label="First name" name="firstName" value={form.firstName} handleChange={handleChange} error={errors.firstName}/>
                        <InputField label="Last name" name="lastName" value={form.lastName} handleChange={handleChange} error={errors.lastName}/>
                        <InputField label="Email" name="email" value={form.email} inputType="email" handleChange={handleChange} error={errors.email}/>
                        <InputField label="Password" name="password" inputType="password" value={form.password} handleChange={handleChange} placeholder="Leave blank to keep current password" error={errors.password}/>
                    </>
                ) : (
                    <>
                        <Field label="First name:">{user.firstName}</Field>
                        <Field label="Last name:">{user.lastName}</Field>
                        <Field label="Email:">{user.email}</Field>
                    </>
                )}
            </div>

            <div className="card-footer">
                {isEdit ? (
                    <>
                        <SecondaryButton type="confirm" onClick={handleEditConfirm}>Confirm</SecondaryButton>
                        <SecondaryButton onClick={() => setIsEdit(false)}>Cancel</SecondaryButton>
                    </>
                ) : (
                    <>
                        <SecondaryButton onClick={() => navigate("/")}>Back</SecondaryButton>
                        <SecondaryButton onClick={() => setIsEdit(true)}>Edit profile</SecondaryButton>
                    </>
                )}
            </div>
        </section>
    );
}

export default UserProfile;
