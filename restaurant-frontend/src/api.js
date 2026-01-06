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
                credentials : "include",
                method: "PUT"
            });
    }catch (error) {
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

