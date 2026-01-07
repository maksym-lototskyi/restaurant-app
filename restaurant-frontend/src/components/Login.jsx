import InputField from "./utils/InputField.jsx";
import {useState} from "react";
import SecondaryButton from "./utils/SecondaryButton.jsx";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../AuthContext.jsx";

export default function Login(){
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const [error, setError] = useState(null);
    const {refreshUser} = useAuth();

    const handleSubmit = async () => {
        try{
            const res = await fetch("http://localhost:8080/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                credentials: "include",
                body: new URLSearchParams({
                    username: email,
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
        }
        catch (err){
            console.log(err);
        }
    }

    return <div className="card-container center">
        <h2 className="card-header">Login</h2>
        <div className="card-body">
            {error && <div className="error-banner">{error}</div>}
            <InputField inputType="text" label="Username" value={email} handleChange={(e) => setEmail(e.target.value)}></InputField>
            <InputField inputType="password" label="Password" value={password} handleChange={(e) => setPassword(e.target.value)}></InputField>
        </div>
        <div className="card-footer">
            <SecondaryButton onClick={handleSubmit}>Submit</SecondaryButton>
            <SecondaryButton type="cancel" onClick={() => navigate("/")}>Cancel</SecondaryButton>
        </div>
    </div>
}