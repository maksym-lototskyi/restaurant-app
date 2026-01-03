function PaginationControls({ page, totalPages, onPrev, onNext }) {
    return (
        <div className="pagination">
            <button className="secondary-button" onClick={onPrev} disabled={page === 0}>
                Previous
            </button>

            <span>Page {page + 1} of {totalPages}</span>

            <button className="secondary-button" onClick={onNext} disabled={page === totalPages - 1}>
                Next
            </button>
        </div>
    );
}

export default PaginationControls;