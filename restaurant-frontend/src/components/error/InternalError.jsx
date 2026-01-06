import ApiError from "./ApiError.jsx";

export default function InternalError({message}){
    return <ApiError message={message} title="Internal Error"/>
}