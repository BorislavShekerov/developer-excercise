import {useState,useEffect} from 'react'
import{Link}from 'react-router-dom'
import { getAllItems,scanItem,calculateTotal} from '../services/customerService';

export default function Home (){

    const [items,setItems]=useState(null);
    const [scanned,setScanned] =useState([]);
    const [total,setTotal]=useState(0);
  
    useEffect(()=>{
      getAllItems()
      .then(res=>{
        console.log(res)
        setItems(res)
      })
    },[]);
  
    const onScan=(e)=>{
      
      let splited=e.target.id.split('|');
      setScanned([...scanned,splited[0]]);
      scanItem(splited[0],splited[1]);
      
      
    }
    const onPay=(e)=>{
        calculateTotal().then( (res)=>{
            setTotal(res);
        console.log(res);
        setScanned([]);
    });
       
    }

    return( <>
        <h1 className='table'>{total===0?`Total Price:0`:`Total Price: ${total.total}`}</h1>
        
        {scanned.length===0? <h2 className='table'>Scanned Items: empty</h2>: <h2 className='table'>Scanned Items: {scanned+','}</h2>}
       <div className='table'>
       <Link  className='btn btn-danger'onClick={onPay} to={'/'}>Pay</Link>
        </div> 
        <table  className="styled-table">
            <thead>
                <tr>
                    <th>
                      Product Name
                    </th>
                 
                    <th> 
                        Price
                    </th>
                    <th>
                        DealId
                    </th>
                  
                    
                    
                    <th></th>
                </tr>
            </thead>
            <tbody>
            {items===null?<tr><td>Loading</td></tr>:items.map(itm=> <tr key={itm.name}>
              <td>
                  {itm.name}
              </td>
              <td>
                 {itm.price}
              </td>
              <td>
                 {itm.dealId}
              </td>
             
              <td>
              <Link id={`${itm.name}|${itm.price}`} className='btn btn-outline-primary' onClick={onScan}to={'/'}>Scan</Link>
              </td>
          </tr>)} 
         
      
            </tbody>
        </table>
        </>)
} 