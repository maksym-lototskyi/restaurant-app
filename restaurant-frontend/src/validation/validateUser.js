export function validateUser(form) {
    const errors = {};

    if (!form.firstName || !form.firstName.trim()) {
        errors.firstName = "First name cannot be empty";
    } else if (form.firstName.length > 50) {
        errors.firstName = "First name must be at most 50 characters";
    }

    if (!form.lastName || !form.lastName.trim()) {
        errors.lastName = "Last name cannot be empty";
    } else if (form.lastName.length > 50) {
        errors.lastName = "Last name must be at most 50 characters";
    }

    if (!form.email || !form.email.trim()) {
        errors.email = "Email cannot be empty";
    } else if (!isValidEmail(form.email)) {
        errors.email = "Invalid email format";
    }

    if (form.password && form.password.trim()) {
        validatePassword(form.password, errors);
    }

    return {
        isValid: Object.keys(errors).length === 0,
        errors
    };
}

export function validateLogin(email, password) {
    const errors = {};
    if(!isValidEmail(email)){
        errors.email = "Invalid email format";
    }
    validatePassword(password, errors)
    return {
        isValid: Object.keys(errors).length === 0,
        errors
    };
}

function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function validatePassword(password, errors) {
    if (password.length < 8 || password.length > 64) {
        errors.password = "Password must be between 8 and 64 characters";
        return;
    }

    if (!/\d/.test(password)) {
        errors.password = "Password must contain at least one digit";
        return;
    }

    if (!/[a-z]/.test(password)) {
        errors.password = "Password must contain at least one lowercase letter";
        return;
    }

    if (!/[A-Z]/.test(password)) {
        errors.password = "Password must contain at least one uppercase letter";
        return;
    }

    if (!/[!@#$%^&*()/.-]/.test(password)) {
        errors.password = "Password must contain at least one special character";
    }
}


