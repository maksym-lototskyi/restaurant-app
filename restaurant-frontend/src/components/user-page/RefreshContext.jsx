import {createContext, useContext, useState} from "react";

const RefreshContext = createContext();

export function RefreshProvider({ children }) {
    const [version, setVersion] = useState(0);

    const triggerRefresh = () => {
        setVersion(prev => {
            return prev + 1
        });
    };

    return (
        <RefreshContext.Provider value={{ version, triggerRefresh }}>
            {children}
        </RefreshContext.Provider>
    );
}

export function useRefresh(){
    return useContext(RefreshContext);
}
