export async function logout(){
    try {
        await fetch("http://localhost:8080/logout", {
            method: "POST",
            credentials : "include"
        })
    }
    catch (err){
        console.log(err);
    }
}

export async function cancelReservation(id){
    try{
        await fetch(`http://localhost:8080/reservations/${id}/cancel`,
            {
                method: "PUT"
            });
    }catch (error) {
        console.error(error);
    }
}

export async function editReservation(id, reservationStart, setReservation, setForm, setIsEdit){
    try {
        const response = await fetch(
            `http://localhost:8080/reservations/${id}/reschedule?newStartTime=${reservationStart}`,
            {
                method: "PUT",
                headers: {"Content-Type": "application/json"}
            });

        if (!response.ok) {
            throw new Error("Failed to update reservation");
        }

        const updatedReservation = await response.json();

        setReservation(updatedReservation);
        setForm(updatedReservation);
        setIsEdit(false);

    } catch (error) {
        console.error(error);
    }
}

export async function getAllReservations(){
    try {
        const response = await fetch("http://localhost:8080/reservations", {
            credentials: "include"
        });

        if (!response.ok) {
            throw new Error("Failed to fetch reservations");
        }

        return await response.json();
    } catch (error) {
        console.error(error);
        return [];
    }
}

