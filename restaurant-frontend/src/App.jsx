import './App.css'
import UserPage from "./components/UserPage.jsx";
import NavBar from "./components/NavBar.jsx";

function App() {
    return (
        <>
            <header>
                <NavBar/>
            </header>
            <main>
                <UserPage/>
            </main>
        </>
    )
}

export default App
