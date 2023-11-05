import {useState} from 'react'

export default function Register(){

    const [registerFormData, setRegisterFormData] = useState({ firstname:"",lastname:"",
    email: "", password: "" })

    async function handleSubmit(e) {
       
    }
    const onSubmit = async (values) => {
        
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