import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ReservationListItem from "./ReservationListItem";
import PaginationControls from "./PaginationControls";
import {useRefresh} from "../user-page/RefreshContext.jsx";
import {useAuth} from "../../AuthContext.jsx";
import './ReservationList.css';

const PAGE_SIZE = 8;

function ReservationList() {
    const [reservations, setReservations] = useState([]);
    const [page, setPage] = useState(0);
    const [loading, setLoading] = useState(true);
    const { version } = useRefresh();
    const navigate = useNavigate();
    const { user } = useAuth();

    useEffect(() => {
        fetch(`http://localhost:8080/reservations/customer`, {
            credentials: "include"
        })
            .then(res => {
                if (!res.ok) throw new Error("Failed to fetch reservations");
                return res.json();
            })
            .then(data => {
                setReservations(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setLoading(false);
            });
    }, [user, version]);

    if (loading) return <p>Loading reservations...</p>;

    const totalPages = Math.ceil(reservations.length / PAGE_SIZE);
    const startIndex = page * PAGE_SIZE;
    const currentPageData = reservations.slice(startIndex, startIndex + PAGE_SIZE);

    return (
        <section className="grid-item1 card-container">
            <h3 className="card-header center-aligned">Your reservations</h3>
            <div className="body">
                {reservations.length === 0 ? (
                    <p>No reservations found</p>
                ) : (
                    <ul className="reservation-list">
                        {currentPageData.map(res => (
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
