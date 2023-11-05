const baseUrl = 'https://localhost:7110/';



export const login=async (email,password)=>{
    const response=await fetch(`${baseUrl}login?Email=${email}&Password=${password}`,{
        method:'POST'
    });
    return await response.json();
}