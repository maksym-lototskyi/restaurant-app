import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Field from "../utils/Field.jsx";
import Dialog from "../utils/Dialog.jsx";
import NotFound from "../error/NotFound.jsx";
import InternalError from "../error/InternalError.jsx";
import ForbiddenError from "../error/Forbidden.jsx";
import SecondaryButton from "../utils/SecondaryButton.jsx";

export default function UserDetails() {
    const [user, setUser] = useState(null);
    const [roles, setRoles] = useState([]);
    const [status, setStatus] = useState("loading");
    const [error, setError] = useState(null);
    const [showDialog, setShowDialog] = useState(false);
    const [roleError, setRoleError] = useState(null);

    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        async function fetchData() {
            try {
                const [userRes, rolesRes] = await Promise.all([
                    fetch(`http://localhost:8080/users/${id}`, { credentials: "include" }),
                    fetch(`http://localhost:8080/roles`, { credentials: "include" })
                ]);

                if (userRes.status === 401 || rolesRes.status === 401) {
                    return navigate("/login");
                }

                if (userRes.status === 403) {
                    setStatus("forbidden");
                    return;
                }

                if (!userRes.ok) {
                    const apiError = await userRes.json();
                    setError(apiError.message);
                    return setStatus(userRes.status === 404 ? "not found" : "internal error");
                }

                if (!rolesRes.ok) {
                    throw new Error("Failed to load roles");
                }

                const userData = await userRes.json();
                const rolesData = await rolesRes.json();

                setUser(userData);
                setRoles(rolesData);
                setStatus("done");

            } catch (err) {
                console.error(err);
                setError(err.message);
                setStatus("internal error");
            }
        }

        fetchData();
    }, [id, navigate]);

    const handleRoleChange = async (newRole) => {
        setRoleError(null);

        try {
            const res = await fetch(
                `http://localhost:8080/users/${id}/permissions?role=${newRole}`,
                {
                    method: "PUT",
                    credentials: "include"
                }
            );

            if (res.status === 401) return navigate("/login");
            if (res.status === 403) return setStatus("forbidden");
            if (!res.ok) {
                const apiError = await res.json();
                setRoleError(apiError.message);
                return;
            }

            setUser({ ...user, role: newRole });

        } catch (err) {
            console.error(err);
            setRoleError("Unexpected error while changing role");
        }
    };

    const handleDeleteConfirm = async () => {
        try {
            const res = await fetch(`http://localhost:8080/users/${id}`, {
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
                <p>Are you sure you want to delete this user?</p>
            </Dialog>

            <h2 className="card-header">User Details</h2>

            <div className="card-body start-aligned">
                <Field label="First Name">{user.firstName}</Field>
                <Field label="Last Name">{user.lastName}</Field>
                <Field label="Email">{user.email}</Field>

                <Field label="Role">
                    <select
                        value={user.role}
                        onChange={(e) => handleRoleChange(e.target.value)}
                    >
                        {roles.map(role => (
                            <option key={role} value={role}>
                                {role}
                            </option>
                        ))}
                    </select>
                </Field>

                {roleError && <div className="error-banner">{roleError}</div>}
            </div>

            <div className="card-footer">
                <SecondaryButton onClick={() => navigate(-1)}>
                    Back
                </SecondaryButton>
                <SecondaryButton cancel onClick={() => setShowDialog(true)}>
                    Delete
                </SecondaryButton>
            </div>
        </section>
    );
}
