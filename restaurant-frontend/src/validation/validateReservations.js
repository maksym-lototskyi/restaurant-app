export function validateReservationEdit(date, selectedTimeSlot, guests) {
    if (!date || !selectedTimeSlot || guests === undefined || guests === null) {
        return { valid: false, error: "All fields are required." };
    }

    const dateTimeString = `${date}T${selectedTimeSlot}`;
    const newStartTime = new Date(dateTimeString);

    if (isNaN(newStartTime.getTime())) {
        return { valid: false, error: "Invalid date or time format." };
    }

    const now = new Date();
    if (newStartTime <= now) {
        return { valid: false, error: "The reservation must be in the future." };
    }

    const hours = newStartTime.getHours();
    if (hours < 8 || hours > 20) {
        return { valid: false, error: "Time must be between 08:00 and 20:00." };
    }

    const minutes = newStartTime.getMinutes();
    if (minutes % 15 !== 0) {
        return { valid: false, error: "Minutes must be a multiple of 15." };
    }

    const guestNumber = Number(guests);
    if (!Number.isInteger(guestNumber) || guestNumber < 1 || guestNumber > 20) {
        return { valid: false, error: "Number of guests must be between 1 and 20." };
    }

    return { valid: true };
}
