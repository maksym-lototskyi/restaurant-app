import ListItemElement from "./ListItemElement.jsx";

function UserListItem({ user, onClick }) {
    return (
        <li className="list-item" onClick={onClick}>
            <ListItemElement value={user.firstName}/>
            <ListItemElement value={user.lastName}/>
            <ListItemElement value={user.email}/>
        </li>
    );
}

export default UserListItem;
