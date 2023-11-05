const baseUrl = 'https://localhost:7110/';



export const login=async (email,password)=>{
    const response=await fetch(`${baseUrl}login?Email=${email}&Password=${password}`,{
        method:'POST'
    });
    return await response.json();
}

export const register=async (firstname,lastname,email,password)=>{
    const response=await fetch(`${baseUrl}Register?FirstName=${firstname}&LastName=${lastname}&Password=${password}&Email=${password}`,{
        method:'POST',
       
    });
    return await response.json();
}

export const addItem=async(name,price,accessToken)=>{
    const response=await fetch(`${baseUrl}addItem?Name=${name}&Price=${price}`,{
        method:'POST',
        headers:{
            'Authorization': `bearer ${accessToken}`
        }
    });
    return await response.json();
}
export const itemToDeal=async(name,type,accessToken)=>{
    const response=await fetch(`${baseUrl}addItemToDeal?itemName=${name}&dealType=${type}`,{
        method:'POST',
        headers:{
            'Authorization': `bearer ${accessToken}`
        }
    });
    return await response.json();
}