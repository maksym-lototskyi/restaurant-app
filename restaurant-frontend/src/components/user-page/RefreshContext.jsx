import {createContext, useContext, useState} from "react";

const RefreshContext = createContext();

export function RefreshProvider({ children }) {
    const [version, setVersion] = useState(0);

    const triggerRefresh = () => {
        setVersion(v => v + 1);
    };

    return (
        <RefreshContext.Provider value={{ version, triggerRefresh }}>
            {children}
        </RefreshContext.Provider>
    );
}

// eslint-disable-next-line react-refresh/only-export-components
export function useRefresh(){
    return useContext(RefreshContext);
}
