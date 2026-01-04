export function findClosestDate() {
    const date = new Date();
    if(date.getMinutes() !== 0){
        date.setMinutes(0);
    }

    if (date.getHours() >= 20) {
        date.setDate(date.getDate() + 1);
        date.setHours(8, 0, 0, 0);
    }
    return date;
}

export function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");

    return `${year}-${month}-${day}`;
}

export function formatTime(date) {
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");

    return `${hours}:${minutes}`;
}

export function extractDateFromString(dateString){
    const dateElems = dateString.split("-");
    const year = Number(dateElems[0]);
    const month = Number(dateElems[1]);
    const day = Number(dateElems[2]);

    const date = new Date();
    date.setFullYear(year, month, day);
    date.setHours(8, 0, 0, 0);

    if(date.getDate() === findClosestDate().getDate()){
        return findClosestDate();
    }
    return date;
}

export function extractHoursFromTimeString(time) {
    return Number(time.split(":")[0])
}