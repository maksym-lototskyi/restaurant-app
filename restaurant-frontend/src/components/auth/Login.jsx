import InputField from "../utils/InputField.jsx";
import {useState} from "react";
import SecondaryButton from "../utils/SecondaryButton.jsx";
import {useNavigate} from "react-router-dom";
import {useAuth} from "./AuthContext.jsx";
import {validateLogin} from "../../validation/validateUser.js";

export default function Login(){
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const [error, setError] = useState(null);
    const [errors, setErrors] = useState({});
    const {refreshUser} = useAuth();

    const handleSubmit = async () => {
        const validationResult = validateLogin(email, password);
        if(!validationResult.isValid){
            setErrors(validationResult.errors);
            return;
        }
        setErrors({});
        try{
            const res = await fetch("http://localhost:8080/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                credentials: "include",
                body: new URLSearchParams({
                    email: email,
                    password: password
                })
            });

            if(res.ok){
                await refreshUser();
                return navigate("/");
            }
            if(res.status === 401){
                setError("Invalid Credentials");
            }
            if(res.status === 400){
                const apiError = await res.json();
                setErrors(apiError.fieldErrors || {});
            }
        }
        catch (err){
            console.log(err);
        }
    }

    return <div className="card-container center">
        <h2 className="card-header">Login</h2>
        <div className="card-body">
            {error && <div className="error-banner">{error}</div>}
            <InputField inputType="text" label="Email" value={email} handleChange={(e) => setEmail(e.target.value)} error={errors?.email}></InputField>
            <InputField inputType="password" label="Password" value={password} handleChange={(e) => setPassword(e.target.value)} error={errors?.password}></InputField>
        </div>
        <div className="card-footer">
            <SecondaryButton onClick={handleSubmit}>Submit</SecondaryButton>
            <SecondaryButton type="cancel" onClick={() => navigate("/")}>Cancel</SecondaryButton>
        </div>
    </div>
}