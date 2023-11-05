import React from "react"
import { Link, NavLink } from "react-router-dom"
import { useUserContext } from '../context/UserContext';


export default function Header(){
    const {userLogout} = useUserContext();
    const {user}=useUserContext();
    
    const activeStyles = {
        fontWeight: "bold",
        textDecoration: "underline",
        color: "#161616"
    }
    
const handleClick=()=>{
    userLogout();
}
   
return (<header>
    {user.role==='Manager'?<Link className="site-logo" to="/manager"># GrosaryTill App</Link>
    :<Link className="site-logo" to="/"># GrosaryTill App</Link>}
    
    <nav>{user.firstName!==undefined? <NavLink onClick={handleClick}
            to="/"
            style={({isActive}) => isActive ? activeStyles : null}
        >
           Logout
        </NavLink>:null}
         { user.firstName===undefined ? <NavLink 
            to="/login"
            style={({isActive}) => isActive ? activeStyles : null}
        >
           ManagerLogin
        </NavLink>:null}
       {user.firstName===undefined? <NavLink 
            to="/register"
            style={({isActive}) => isActive ? activeStyles : null}
        >
            ManagerRegister
        </NavLink>:null}
   
    </nav>
</header>)
}