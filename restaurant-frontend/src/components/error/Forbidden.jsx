import ApiError from "./ApiError.jsx";

export default function ForbiddenError(){
    return <ApiError title="Forbidden" message="You do not have access to this resource"/>
}