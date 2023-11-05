import {useState} from 'react'
import { useUserContext } from '../context/UserContext';
import { addItem, itemToDeal } from '../services/managerService';
export default function ManagerHome(){

    const [addItemFormData, setAddItemFormData] = useState({ name: "", price: "" })
    const [itemtoDealFormData, setitemtoDealFormData] = useState({ name: "", type: "" })
    const {user} = useUserContext();
    console.log(user);
    async function handleSubmit(e) {
        e.preventDefault()
      await onSubmit()
    
     
    }
    async function handleSubmit2(e) {
        e.preventDefault()
      await onSubmit2()
    
     
    }
    const onSubmit=async()=>{
        
        addItem(addItemFormData.name,addItemFormData.price,user.token).then((res)=>{
        
          console.log(res);
         
        })
      }
      const onSubmit2=async()=>{
        
        itemToDeal(itemtoDealFormData.name,itemtoDealFormData.type,user.token).then((res)=>{
        
          console.log(res);
         
        })
      }
    function handleChange(e) {
        const { name, value } = e.target
        setAddItemFormData(prev => ({
            ...prev,
            [name]: value
        }))
    }
    function handleChange2(e) {
        const { name, value } = e.target
        setitemtoDealFormData(prev => ({
            ...prev,
            [name]: value
        }))
    }
    return (<div className="login-container">
    <h1>Add Item</h1>
    <form onSubmit={handleSubmit} className="login-form">
        <input
            name="name"
            onChange={handleChange}
            type="text"
            placeholder="name"
            value={addItemFormData.name}
        />
        <input
            name="price"
            onChange={handleChange}
            type="text"
            placeholder="price"
            value={addItemFormData.price}
        />
        <button>Add</button>
    </form>
    <h1>Add ItemToDeal</h1>
    <form onSubmit={handleSubmit2} className="login-form">
        <input
            name="name"
            onChange={handleChange2}
            type="text"
            placeholder="name"
            value={itemtoDealFormData.name}
        />
        <input
            name="type"
            onChange={handleChange2}
            type="text"
            placeholder="type"
            value={itemtoDealFormData.type}
        />
        <button>Add</button>
    </form>
</div>)
}