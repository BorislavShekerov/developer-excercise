import Home from'./pages/Home' 
import { UserProvider } from './context/UserContext';
import { BrowserRouter,Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Login from './pages/Login';
import Register from './pages/Register';
import ManagerHome from './pages/ManagerHome';


function App() {
  return (
   <>
   <UserProvider>
    <BrowserRouter>
    <Routes>
    <Route path="/" element={<Layout />}>
      <Route index element={<Home/>}/>
      <Route path="login" element={<Login/>}/>
      <Route path="register" element={<Register/>}/>
      <Route path="manager" element={<ManagerHome/>}/>
      </Route>
    </Routes>
    </BrowserRouter>
   </UserProvider>
   
   
   </>
  );
}

export default App;
