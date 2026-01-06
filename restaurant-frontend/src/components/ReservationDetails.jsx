import Field from "./utils/Field.jsx";
import {useEffect, useState} from "react";
import Dialog from "./utils/Dialog.jsx";
import {useNavigate, useParams} from "react-router-dom";
import {cancelReservation} from "../api.js";
import NotFound from "./error/NotFound.jsx";
import InternalError from "./error/InternalError.jsx";
import ReservationScheduler from "./user-page/ReservationScheduler.jsx";
import {formatDate, formatTime} from "../util/date-time-util.jsx";
import ForbiddenError from "./error/Forbidden.jsx";

function ReservationDetails() {
    const [reservation, setReservation] = useState(null);
    const [status, setStatus] = useState('loading');
    const [error, setError] = useState(null);
    const [isEdit, setIsEdit] = useState(false);
    const [showDialog, setShowDialog] = useState(false);
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        async function fetchReservation() {
            try {
                const res = await fetch(`http://localhost:8080/reservations/${id}`, {credentials: "include"});
                if (res.status === 401) return navigate("/login");
                if (res.status === 403) {
                    setStatus('forbidden');
                    return;
                }
                if (!res.ok) {
                    const apiError = await res.json();
                    setError(apiError.message);
                    return setStatus(res.status === 404 ? "not found" : "internal error");
                }
                const data = await res.json();
                setReservation(data);
                setStatus("done");
            } catch (err) {
                console.error(err);
                setError(err.message);
                setStatus("internal error");
            }
        }

        fetchReservation();
    }, [id, navigate]);


    const handleEditConfirm = async (date, selectedTimeSlot, guests) => {
        try {
            const response = await fetch(
                `http://localhost:8080/reservations/${id}/reschedule`,
                {
                    method: "PUT",
                    credentials: "include",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify({
                        newStartTime: `${date}T${selectedTimeSlot}`,
                        newNumberOfGuests: guests
                    })
                });

            if (response.status === 401) navigate("/login");
            if (response.status === 403) setStatus('forbidden');
            if (!response.ok) {
                const apiError = await response.json();
                setError(apiError.message);

                if (response.status !== 409) {
                    setStatus(response.status === 404 ? "not found" : "internal error");
                }
                return;
            }

            const updatedReservation = await response.json();

            setReservation(updatedReservation);
            setIsEdit(false);

        } catch (error) {
            console.error(error);
        }
    };

    const handleReservationCancelConfirm = async () => {
        await cancelReservation(reservation.id);
        navigate("/");
    }

    const handleReschedule = () => {
        setIsEdit(true);
    }

    const handleCancelEdit = () => {
        setIsEdit(false);
    }

    const handleShowDialog = () => {
        setShowDialog(true);
    }

    if (status === "loading") return <p>Loading...</p>
    if (status === 'not found') return <NotFound message={error}/>;
    if (status === 'internal error') return <InternalError message={error}/>;
    if (status === 'forbidden') return <ForbiddenError/>;


    const start = new Date(reservation.reservationStart);
    const end = new Date(reservation.reservationEnd);

    const preferredStartTime = new Date(start);
    preferredStartTime.setMinutes(0, 0, 0);

    return (
        <section className="card-container center">
            <Dialog isOpen={showDialog} onClose={() => {
                setShowDialog(false)
            }} onConfirm={handleReservationCancelConfirm}
                    title="Confirmation">
                <p>Are you sure you want to cancel the reservation?</p>
            </Dialog>
            <h2 className="card-header">Reservation Details</h2>

            <div className="card-body start-aligned">
                {isEdit ? <>
                        {error && <div className="error-banner">{error}</div>}
                        <ReservationScheduler
                        initTime={formatTime(preferredStartTime)}
                        initDate={formatDate(start)}
                        initGuests={reservation.numberOfGuests}
                        onSchedule={handleEditConfirm}
                        title="Edit reservation"
                    />
                        <Field label="Old time:">
                            {start.toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"})}
                            {" – "}
                            {end.toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"})}
                        </Field>
                    </>:
                    <>
                        <Field label="Date:">{start.toLocaleDateString()}</Field>

                        <Field label="Time:">
                            {start.toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"})}
                            {" – "}
                            {end.toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"})}
                        </Field>

                <Field label="Guests:">{reservation.numberOfGuests}</Field>
                <Field label="Table:">{reservation.tableNumber}</Field>
                <hr/>

                <h3>Customer</h3>

                <Field
                    label="Name:">{reservation.customerDetails.firstName}{" "}{reservation.customerDetails.lastName}</Field>
                <Field label="Email:">{reservation.customerDetails.email}</Field>
                    </>
                }
            </div>
            <div className="card-footer">
                {isEdit ?
                    (<>
                        <button className="secondary-button" onClick={handleCancelEdit}>Cancel
                        </button>
                    </>) :
                    (<>
                        <button className="secondary-button" onClick={() => navigate(-1)}>Back
                        </button>
                        <button className="secondary-button" onClick={handleReschedule}>Edit
                        </button>
                        <button className="secondary-button cancel-button"
                                onClick={handleShowDialog}>Cancel
                        </button>
                    </>)}
            </div>
        </section>
    );
}

export default ReservationDetails;