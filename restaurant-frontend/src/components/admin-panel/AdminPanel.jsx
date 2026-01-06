import ReservationListItem from "../list/ReservationListItem.jsx";
import List from "../list/List.jsx";
import '../list/List.css';
import './AdminPanel.css'
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import ViewSelector from "./ViewSelector.jsx";
import TableListItem from "../list/TableListItem.jsx";
import UserListItem from "../list/UserListItem.jsx";

export default function AdminPanel() {
    const navigate = useNavigate();
    const [currView, setCurrView] = useState("reservations");
    const views = {
        reservations : <List
            fetchItems={({ page, size }) =>
                fetch(`http://localhost:8080/reservations?page=${page}&size=${size}`, {
                    credentials: "include"
                }).then(r => {
                    return r.json()
                })
            }
            renderItem={item => (
                <ReservationListItem
                    key={item.id}
                    reservation={item}
                    onClick={() => navigate(`/reservations/${item.id}`)}
                />
            )}
        />,
        tables : <List
            fetchItems={({ page, size }) =>
                fetch(`http://localhost:8080/tables?page=${page}&size=${size}`, {
                    credentials: "include"
                }).then(r => r.json())
            }
            renderItem={item => (
                <TableListItem
                    key={item.id}
                    table={item}
                    onClick={() => navigate(`/tables/${item.id}`)}
                />
            )}
        />,
        users : <List
            fetchItems={({ page, size }) =>
                fetch(`http://localhost:8080/users?page=${page}&size=${size}`, {
                    credentials: "include"
                }).then(r => r.json())
            }
            renderItem={item => (
                <UserListItem
                    key={item.id}
                    user={item}
                    onClick={() => navigate(`/users/${item.id}`)}
                />
            )}
        />
    }


    return <>
        <div className="admin-panel">
            <ViewSelector view={currView} setView={setCurrView}/>
            <section className="admin-panel-body">
                {views[[currView]]}
            </section>
        </div>
    </>
}