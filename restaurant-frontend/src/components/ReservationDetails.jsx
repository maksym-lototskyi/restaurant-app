import Field from "./Field.jsx";
import InputField from "./InputField.jsx";
import {useEffect, useState} from "react";
import Dialog from "./Dialog.jsx";

function ReservationDetails({id, onBack}) {
    const [reservation, setReservation] = useState(null);
    const [loading, setLoading] = useState(true);
    const [form, setForm] = useState(null);
    const [isEdit, setIsEdit] = useState(false);
    const [showDialog, setShowDialog] = useState(false);

    useEffect(() => {
        fetch(`http://localhost:8080/reservations/${id}`)
            .then(res => res.json())
            .then(data => {
                setReservation(data);
                setForm(data)
                setLoading(false);
            });
    }, [id]);

    const handleEditConfirm = async () => {
        try {
            const response = await fetch(
                `http://localhost:8080/reservations/${reservation.id}/reschedule?newStartTime=${form.reservationStart}`,
                {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    }
                }
            );

            if (!response.ok) {
                throw new Error("Failed to update reservation");
            }

            const updatedReservation = await response.json();

            setReservation(updatedReservation);
            setForm(updatedReservation);
            setIsEdit(false);

        } catch (error) {
            console.error(error);
            alert("Could not update reservation. Please try again.");
        }
    };

    const handleReservationCancelConfirm = async () => {
        try{
            const response = await fetch(`http://localhost:8080/reservations/${reservation.id}/cancel`,
                {
                    method: "PUT"
                });
            if(response.ok){
                onBack();
            }
        }catch (error) {
            console.error(error);
            alert("Could not cancel reservation. Please try again.");
        }

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
        console.log(form);
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
                    title="Confirmation" content="Are you sure you want to cancel the reservation?">
            </Dialog>
            {loading ? (
                <p>Loading...</p>
            ) : (<>
                <h2 className="card-header">Reservation Details</h2>

                <div className="card-body start-aligned">
                    {isEdit ? <InputField label="Date and Time" name="reservationStart" value={form.reservationStart} inputType="datetime-local" handleChange={handleChange} /> :
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
                            <button className="secondary-button" onClick={onBack}>Back</button>
                            <button className="secondary-button" onClick={handleReschedule}>Reschedule</button>
                            <button className="secondary-button cancel-button" onClick={handleShowDialog}>Cancel</button>
                        </>)}
                </div>
            </>)}
        </section>
    );
}

export default ReservationDetails;