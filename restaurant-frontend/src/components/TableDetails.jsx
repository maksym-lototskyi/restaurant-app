import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Field from "./utils/Field.jsx";
import Dialog from "./utils/Dialog.jsx";
import NotFound from "./error/NotFound.jsx";
import InternalError from "./error/InternalError.jsx";
import ForbiddenError from "./error/Forbidden.jsx";
import SecondaryButton from "./utils/SecondaryButton.jsx";
import InputField from "./utils/InputField.jsx";

export default function TableDetails() {
    const [table, setTable] = useState(null);
    const [status, setStatus] = useState("loading");
    const [errors, setErrors] = useState({});
    const [error, setError] = useState(null);
    const [isEdit, setIsEdit] = useState(false);
    const [showDialog, setShowDialog] = useState(false);
    const [form, setForm] = useState(null);

    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        async function fetchTable() {
            try {
                const res = await fetch(`http://localhost:8080/tables/${id}`, { credentials: "include" });
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
                setTable(data);
                setForm(data);
                setStatus("done");
            } catch (err) {
                console.error(err);
                setError(err.message);
                setStatus("internal error");
            }
        }

        fetchTable();
    }, [id, navigate]);


    const handleEditConfirm = async () => {
        try {
            const res = await fetch(`http://localhost:8080/tables/${id}`, {
                method: "PUT",
                credentials: "include",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ tableNumber: form.tableNumber, floorNumber: form.floorNumber })
            });

            if (res.status === 401) return navigate("/login");
            if (res.status === 403) return setStatus("forbidden")
            if (!res.ok) {
                const apiError = await res.json();

                setError(apiError.message);
                if(res.status === 400){
                    return setErrors(apiError.fieldErrors || {});
                }
                return setStatus(res.status === 404 ? "not found" : "internal error");
            }

            const updatedTable = await res.json();
            setTable(updatedTable);
            setIsEdit(false);
        } catch (err) {
            console.error(err);
        }
    };


    const handleDeleteConfirm = async () => {
        try {
            const res = await fetch(`http://localhost:8080/tables/${id}`, {
                method: "DELETE",
                credentials: "include",
            });

            if (res.status === 401) return navigate("/login");
            if (!res.ok) {
                const apiError = await res.json();
                setError(apiError.message);
                return setStatus(res.status === 404 ? "not found" : "internal error");
            }

            navigate("/");
        } catch (err) {
            console.error(err);
        }
    };

    const handleCancelEdit = () => {
        setIsEdit(false);
        setForm({...table});
    }
    const handleShowDialog = () => setShowDialog(true);

    if (status === "loading") return <p>Loading...</p>;
    if (status === "not found") return <NotFound message={error} />;
    if (status === "internal error") return <InternalError message={error} />;
    if (status === "forbidden") return <ForbiddenError />;

    return (
        <section className="card-container center">
            <Dialog
                isOpen={showDialog}
                onClose={() => setShowDialog(false)}
                onConfirm={handleDeleteConfirm}
                title="Confirmation"
            >
                <p>Are you sure you want to delete this table?</p>
            </Dialog>

            <h2 className="card-header">Table Details</h2>
            <div className="card-body start-aligned">
                {isEdit ? (
                    <>
                        {error && <div className="error-banner">{error}</div>}
                        <InputField
                            type="text"
                            value={form.tableNumber}
                            handleChange={(e) => setForm({ ...form, tableNumber: e.target.value })}
                            name="tableNumber"
                            label="Table Number:"
                            error={errors?.tableNumber}
                        />

                        <InputField
                            type="number"
                            value={form.floorNumber}
                            handleChange={(e) => setForm({ ...form, floorNumber: Number(e.target.value) })}
                            name="floorNumber"
                            label="Floor Number:"
                            error={errors?.floorNumber}
                        />
                    </>
                ) : (
                    <>
                        <Field label="Table Number">{table.tableNumber}</Field>
                        <Field label="Floor Number">{table.floorNumber}</Field>
                        <Field label="Seats">{table.numberOfSeats}</Field>
                    </>
                )}
            </div>

            <div className="card-footer">
                {isEdit ? (
                    <>
                        <SecondaryButton onClick={handleEditConfirm}>Confirm</SecondaryButton>
                        <SecondaryButton onClick={handleCancelEdit}>Cancel</SecondaryButton>
                    </>
                ) : (
                    <>
                        <SecondaryButton onClick={() => navigate(-1)}>Back</SecondaryButton>
                        <SecondaryButton onClick={() => setIsEdit(true)}>Edit</SecondaryButton>
                        <SecondaryButton cancel onClick={handleShowDialog}>Delete</SecondaryButton>
                    </>
                )}
            </div>
        </section>
    );
}
