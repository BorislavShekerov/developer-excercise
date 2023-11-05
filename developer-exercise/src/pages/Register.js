import {useState} from 'react'
import { register } from '../services/managerService';
import { useUserContext } from '../context/UserContext';
import { useNavigate } from 'react-router-dom';
export default function Register(){
    const { userLogin } = useUserContext();
    const navigate = useNavigate();
    const [registerFormData, setRegisterFormData] = useState({ firstname:"",lastname:"",
    email: "", password: "" })

    async function handleSubmit(e) {
        e.preventDefault()
        await onSubmit()
    }
    const onSubmit = async () => {
        register(registerFormData.firstname,registerFormData.lastname,registerFormData.email,registerFormData.password)
      .then((res)=>{
        
        userLogin(res);
        navigate("/manager");
      })
     };

    function handleChange(e) {
        const { name, value } = e.target
        setRegisterFormData(prev => ({
            ...prev,
            [name]: value
        }))
    }

    return (  <div className="login-container">
    <h1>Register</h1>
    <form onSubmit={handleSubmit} className="login-form">
        <input
            name="firstname"
            onChange={handleChange}
            type="text"
            placeholder="FirstName"
            value={registerFormData.firstname}
        />
        <input
            name="lastname"
            onChange={handleChange}
            type="text"
            placeholder="LastName"
            value={registerFormData.lastname}
        />
         <input
            name="email"
            onChange={handleChange}
            type="email"
            placeholder="Email"
            value={registerFormData.email}
        />
         <input
            name="password"
            onChange={handleChange}
            type="password"
            placeholder="Password"
            value={registerFormData.password}
        />
        
        <button>Register</button>
    </form>
</div>)
}