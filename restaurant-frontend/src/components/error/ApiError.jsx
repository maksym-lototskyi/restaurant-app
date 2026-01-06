import SecondaryButton from "../utils/SecondaryButton.jsx";
import {useNavigate} from "react-router-dom";

export default function ApiError({message, title}){
    const navigate = useNavigate();
    return (
        <div className='card-container center'>
            <h2 className='card-header' style={{color : "red"}}>{title}</h2>
            <div className='card-body'>
                {message}
            </div>
            <div className='card-footer'>
                <SecondaryButton onClick={() => navigate(-1)}>Back</SecondaryButton>
            </div>
        </div>
    )
}