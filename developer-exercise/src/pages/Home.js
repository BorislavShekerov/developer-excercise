import {useState,useEffect} from 'react'
import{Link}from 'react-router-dom'
import { getAllItems,scanItem } from '../services/customerService';

export default function Home (){

    const [items,setItems]=useState(null);
    const [scanned,setScanned] =useState([]);
  
    useEffect(()=>{
      getAllItems()
      .then(res=>{
        console.log(res)
        setItems(res)
      })
    },[]);
  
    const onScan=(e)=>{
      
      let splited=e.target.id.split('|')
      setScanned([...scanned,splited[0]])
      console.log(splited[1]);
      scanItem(splited[0],splited[1]);
      
      
    }

    return( <>
        <h1 className='table'>Items Log</h1>
        {scanned.length===0? <h2 className='table'>Scanned Items: empty</h2>: <h2 className='table'>Scanned Items: {scanned+','}</h2>}
        
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