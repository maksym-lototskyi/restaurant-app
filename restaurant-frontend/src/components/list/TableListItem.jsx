import ListItemElement from "./ListItemElement.jsx";

function TableListItem({table, onClick}) {
    return (
        <li className="list-item" onClick={onClick}>
            <ListItemElement value={<strong>Table {table.tableNumber}</strong>}/>
            <ListItemElement value={`Floor ${table.floorNumber}`}/>
            <ListItemElement value={`${table.numberOfSeats} seats`}/>
        </li>
    );
}

export default TableListItem;
