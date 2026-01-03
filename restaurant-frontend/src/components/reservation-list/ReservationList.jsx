import {useEffect, useState} from "react";
import PaginationControls from "./PaginationControls.jsx";
import ReservationListItem from "./ReservationListItem.jsx";
import './ReservationList.css';

const PAGE_SIZE = 5;

function ReservationList({customerId, onSelect}) {
    const [reservations, setReservations] = useState([]);
    const [page, setPage] = useState(0);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch(`http://localhost:8080/reservations/user/${customerId}`)
            .then(res => res.json())
            .then(data => {
                setReservations(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setLoading(false);
            });
    }, [customerId]);

    if (loading) return <p>Loading reservations...</p>;
    if (reservations.length === 0) return <p>No reservations found</p>;

    const totalPages = Math.ceil(reservations.length / PAGE_SIZE);
    const startIndex = page * PAGE_SIZE;
    const currentPageData = reservations.slice(
        startIndex,
        startIndex + PAGE_SIZE
    );

    return (
        <section className="grid-item1 card-container">
            <h3 className="card-header center-aligned">Your reservations</h3>
            <div className="body">
                <ul className="reservation-list">
                    {currentPageData.map(res => (
                        <ReservationListItem key={res.id} reservation={res} onClick={() => onSelect?.(res.id)}/>
                    ))}
                </ul>
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
