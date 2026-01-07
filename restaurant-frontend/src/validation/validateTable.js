export function validateTableCreate(form) {
    const errors = {};

    validateFloorNumberAndTableNumber(errors, form);

    if (form.numberOfSeats === "" || form.numberOfSeats === null) {
        errors.numberOfSeats = "Number of seats is required";
    } else if (isNaN(form.numberOfSeats)) {
        errors.numberOfSeats = "Number of seats must be a number";
    } else if (Number(form.numberOfSeats) <= 0) {
        errors.numberOfSeats = "Number of seats must be greater than 0";
    }
    else if (Number(form.numberOfSeats) > 20) {
        errors.numberOfSeats = "Number of seats must not be greater than 20";
    }

    return errors;
}

export function validateTableUpdate(form) {
    const errors = {};
    validateFloorNumberAndTableNumber(errors, form);

    return errors;
}

function validateFloorNumberAndTableNumber(errors, form) {
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
    else if (Number(form.floorNumber) > 100) {
        errors.floorNumber = "Floor number must be not greater than 100";
    }
}
