import { createContext, useState, useContext } from "react";

const UserContext = createContext();

export const UserProvider = ({
    children,
}) => {
    const [user, setUser] = useState({});

    const userLogin = (userData) => {
        setUser(userData);
    };

    const userLogout = () => {
        setUser({});
    };

    return (
        <UserContext.Provider value={{
            user,
            userLogin,
            userLogout,
            isAuthenticated: !!user.accessToken
        }}>
            {children}
        </UserContext.Provider>  
    );
};

export const useUserContext = () => {
    const context = useContext(UserContext);

    return context;
};