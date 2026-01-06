import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function RootRedirect() {
    const navigate = useNavigate();

    useEffect(() => {
        navigate("/reservations");
    }, [navigate]);

    return null;
}

export default RootRedirect;
