import {useState} from 'react'
import { login } from '../services/managerService'
import { useUserContext } from '../context/UserContext';
import { useNavigate } from 'react-router-dom';
export default function Login(){


    const [loginFormData, setLoginFormData] = useState({ email: "", password: "" })
    const { userLogin } = useUserContext();
    const navigate = useNavigate();
    async function handleSubmit(e) {
        e.preventDefault()
      await onSubmit(loginFormData)
    
     
    }
    const onSubmit=async()=>{
      login(loginFormData.email,loginFormData.password)
      .then((res)=>{
       // console.log(res);
        userLogin(res);
        navigate("/manager");
      })
    }
    function handleChange(e) {
        const { name, value } = e.target
        setLoginFormData(prev => ({
            ...prev,
            [name]: value
        }))
    }
    return ( <div className="login-container">
    <h1>Sign in to your account</h1>
    <form onSubmit={handleSubmit} className="login-form">
        <input
            name="email"
            onChange={handleChange}
            type="email"
            placeholder="Email address"
            value={loginFormData.email}
        />
        <input
            name="password"
            onChange={handleChange}
            type="password"
            placeholder="Password"
            value={loginFormData.password}
        />
        <button>Log in</button>
    </form>
</div>)
}