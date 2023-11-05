import React from "react"
import { Link, NavLink } from "react-router-dom"



export default function Header(){
    const activeStyles = {
        fontWeight: "bold",
        textDecoration: "underline",
        color: "#161616"
    }
    

   
return (<header>
    <Link className="site-logo" to="/"># GrosaryTill App</Link>
    <nav>
        <NavLink 
            to="/login"
            style={({isActive}) => isActive ? activeStyles : null}
        >
           ManagerLogin
        </NavLink>
        <NavLink 
            to="/register"
            style={({isActive}) => isActive ? activeStyles : null}
        >
            ManagerRegister
        </NavLink>
   
    </nav>
</header>)
}