import Field from "../utils/Field.jsx";
import SecondaryButton from "../utils/SecondaryButton.jsx";
import {useEffect, useState} from "react";
import {useRefresh} from "./RefreshContext.jsx";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../auth/AuthContext.jsx";
import InternalError from "../error/InternalError.jsx";
import ForbiddenError from "../error/Forbidden.jsx";

export default function NextReservation() {
    const [nextReservation, setNextReservation] = useState(null);
    const [status, setStatus] = useState("loading");
    const [error, setError] = useState(null);

    const { user } = useAuth();
    const { version } = useRefresh();
    const navigate = useNavigate();

    useEffect(() => {
        async function fetchNextReservation() {
            try {
                const res = await fetch(`http://localhost:8080/reservations/next`, {
                    credentials: "include",
                });

                if (res.status === 401) return navigate("/login");
                if (res.status === 403) {
                    setStatus("forbidden");
                    return;
                }

                if (!res.ok) {
                    const apiError = await res.json();
                    setError(apiError.message);
                    return setStatus(res.status === 404 ? "not found" : "internal error");
                }

                const data = await res.json();
                setNextReservation(data);
                setStatus("done");
            } catch (err) {
                console.error(err);
                setError(err.message);
                setStatus("internal error");
            }
        }

        fetchNextReservation();
    }, [user, version, navigate]);

    if (status === "loading") return <p>Loading...</p>;
    if (status === "internal error") return <InternalError message={error} />;
    if (status === "forbidden") return <ForbiddenError />;

    let start, end;
    if (nextReservation) {
        start = new Date(nextReservation.startTime);
        end = new Date(nextReservation.endTime);
    }

    return (
        <>
            <h3 className="card-header center-aligned">My next reservation</h3>
            <div className="card-body center-aligned">
                {status === 'not found' ? (
                    <p>No upcoming reservations</p>
                ) : (
                    <>
                        <Field label="Number of Guests">{nextReservation.numberOfGuests}</Field>
                        <Field label="Date">{start.toLocaleDateString()}</Field>
                        <Field label="Time">
                            {start.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })}
                            {" – "}
                            {end.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })}
                        </Field>
                    </>
                )}
            </div>

            {nextReservation && (
                <div className="card-footer">
                    <SecondaryButton
                        onClick={() => navigate(`/reservations/${nextReservation.id}`)}
                    >
                        View Details
                    </SecondaryButton>
                </div>
            )}
        </>
    );
}
