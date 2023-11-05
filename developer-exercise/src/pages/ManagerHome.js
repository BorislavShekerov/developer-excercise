import React from 'react'
import { useUserContext } from '../context/UserContext';
export default function ManagerHome(){
   
    const {user} = useUserContext();
    console.log(user);
    return (<>
    <h1>Manager Home</h1>
    </>)
}