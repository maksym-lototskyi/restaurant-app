import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ReservationListItem from "./ReservationListItem";
import PaginationControls from "./PaginationControls";
import {useRefresh} from "../user-page/RefreshContext.jsx";
import './ReservationList.css';

function ReservationList({ title, fetchReservations, pageSize = 8 }) {
    const [reservations, setReservations] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(true);
    const { version } = useRefresh();
    const navigate = useNavigate();

    useEffect(() => {
        fetchReservations({ page, size: pageSize })
            .then(data => {
                setReservations(data.content);
                setTotalPages(data.totalPages);
            })
            .catch(console.error)
            .finally(() => setLoading(false));
    }, [page, version, fetchReservations, pageSize]);


    if (loading) return <p>Loading reservations...</p>;

    return (
        <section className="grid-item1 card-container">
            <h3 className="card-header center-aligned">{title}</h3>
            <div className="body">
                {reservations.length === 0 ? (
                    <p>No reservations found</p>
                ) : (
                    <ul className="reservation-list">
                        {reservations.map(res => (
                            <ReservationListItem
                                key={res.id}
                                reservation={res}
                                onClick={() => navigate(`/reservations/${res.id}`)}
                            />
                        ))}
                    </ul>
                )}
            </div>
            <div className="card-footer">
                <PaginationControls
                    page={page}
                    totalPages={totalPages}
                    onPrev={() => setPage(p => Math.max(p - 1, 0))}
                    onNext={() => setPage(p => Math.min(p + 1, totalPages - 1))}
                />
            </div>
        </section>
    );
}

export default ReservationList;
