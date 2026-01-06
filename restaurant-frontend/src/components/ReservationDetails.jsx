import Field from "./Field.jsx";
import {useEffect, useState} from "react";
import Dialog from "./Dialog.jsx";
import {useNavigate, useParams} from "react-router-dom";
import {cancelReservation, editReservation} from "../api.js";
import TimeSlotsSelector from "./user-page/TimeSlotsSelector.jsx";

function ReservationDetails() {
    const [reservation, setReservation] = useState(null);
    const [loading, setLoading] = useState(true);
    const [form, setForm] = useState(null);
    const [isEdit, setIsEdit] = useState(false);
    const [showDialog, setShowDialog] = useState(false);
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`http://localhost:8080/reservations/${id}`, {credentials: "include"})
            .then(res => res.json())
            .then(data => {
                setReservation(data);
                setForm(data)
                setLoading(false);
            });
    }, [id]);

    const handleEditConfirm = async () => {
        await editReservation(reservation.id, form.reservationStart, setReservation, setForm, setIsEdit);
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

    const handleChange = (e) => {
        setForm({...form, [e.target.name] : e.target.value});
        e.target.value = '';
    }

    const handleShowDialog = () => {
        setShowDialog(true);
    }

    if (!reservation) return <p>Reservation not found</p>;

    const start = new Date(reservation.reservationStart);
    const end = new Date(reservation.reservationEnd);

    return (
        <section className="card-container center">
            <Dialog isOpen={showDialog} onClose={() => {setShowDialog(false)}} onConfirm={handleReservationCancelConfirm}
                    title="Confirmation">
                <p>Are you sure you want to cancel the reservation?</p>
            </Dialog>
            {loading ? (
                <p>Loading...</p>
            ) : (<>
                <h2 className="card-header">Reservation Details</h2>

                <div className="card-body start-aligned">
                    {isEdit ? <TimeSlotsSelector handleChange={handleChange} /> :
                        <>
                            <Field label="Date:">
                                {start.toLocaleDateString()}
                            </Field>

                            <Field label="Time:">
                                {start.toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"})}
                                {" – "}
                                {end.toLocaleTimeString([], {hour: "2-digit", minute: "2-digit"})}
                            </Field>
                        </>
                    }

                    <Field label="Guests:">
                        {reservation.numberOfGuests}
                    </Field>

                    <Field label="Table:">
                        {reservation.tableNumber}
                    </Field>

                    <hr/>

                    <h3>Customer</h3>

                    <Field label="Name:">
                        {reservation.customerDetails.firstName}{" "}
                        {reservation.customerDetails.lastName}
                    </Field>

                    <Field label="Email:">
                        {reservation.customerDetails.email}
                    </Field>
                </div>
                <div className="card-footer">
                    {isEdit ?
                        (<>
                            <button className="secondary-button confirm-button" onClick={handleEditConfirm}>Confirm</button>
                            <button className="secondary-button" onClick={handleCancelEdit}>Cancel</button>
                        </>) :
                        (<>
                            <button className="secondary-button" onClick={() => navigate(-1)}>Back</button>
                            <button className="secondary-button" onClick={handleReschedule}>Reschedule</button>
                            <button className="secondary-button cancel-button" onClick={handleShowDialog}>Cancel</button>
                        </>)}
                </div>
            </>)}
        </section>
    );
}

export default ReservationDetails;