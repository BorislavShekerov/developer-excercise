const baseUrl = 'https://localhost:7110/';

export const getAllItems = async () => {
    const response = await fetch(`${baseUrl}getAllItems`, {
        method: 'GET',     
    }
    );

    return await response.json();
}

export const scanItem=async (name,price)=>{
    const response=await fetch(`${baseUrl}scanItems?Name=${name}&Price=${price}`,{
        method:'POST'
    });
}