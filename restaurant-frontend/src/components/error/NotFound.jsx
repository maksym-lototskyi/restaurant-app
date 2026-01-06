import ApiError from "./ApiError.jsx";

export default function NotFound({message}) {
    return <ApiError message={message} title="Not found" />;
}