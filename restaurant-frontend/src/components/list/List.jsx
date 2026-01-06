import {useState, useEffect} from "react";
import PaginationControls from "./PaginationControls";
import {useRefresh} from "../user-page/RefreshContext.jsx";
import './List.css';

function List({renderItem, fetchItems, pageSize = 8}) {
    const [items, setItems] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [loading, setLoading] = useState(true);
    const {version} = useRefresh();

    useEffect(() => {
        fetchItems({page, size: pageSize})
            .then(data => {
                setItems(data.content);
                setTotalPages(data.totalPages);
            })
            .catch(console.error)
            .finally(() => setLoading(false));
    }, [page, version, fetchItems, pageSize]);


    if (loading) return <p>Loading reservations...</p>;

    return (
        <>
            <div className="body">
                {items.length === 0 ? (
                    <p>No reservations found</p>
                ) : (
                    <ul className="list">
                        {items.map(res => renderItem(res))}
                    </ul>
                )}
            </div>
            <PaginationControls
                page={page}
                totalPages={totalPages}
                onPrev={() => setPage(p => Math.max(p - 1, 0))}
                onNext={() => setPage(p => Math.min(p + 1, totalPages - 1))}
            />
        </>
    );
}

export default List;
