export function validateTable(form) {
    const errors = {};

    if (!form.tableNumber || form.tableNumber.trim() === "") {
        errors.tableNumber = "Table number is required";
    } else if (form.tableNumber.length > 10) {
        errors.tableNumber = "Table number must be at most 10 characters";
    }

    if (form.floorNumber === "" || form.floorNumber === null) {
        errors.floorNumber = "Floor number is required";
    } else if (isNaN(form.floorNumber)) {
        errors.floorNumber = "Floor number must be a number";
    } else if (Number(form.floorNumber) < 0) {
        errors.floorNumber = "Floor number must be 0 or greater";
    }

    if (form.numberOfSeats === "" || form.numberOfSeats === null) {
        errors.numberOfSeats = "Number of seats is required";
    } else if (isNaN(form.numberOfSeats)) {
        errors.numberOfSeats = "Number of seats must be a number";
    } else if (Number(form.numberOfSeats) <= 0) {
        errors.numberOfSeats = "Number of seats must be greater than 0";
    }

    return errors;
}
