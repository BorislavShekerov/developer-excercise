import Home from'./pages/Home' 
import { UserProvider } from './context/UserContext';
import { BrowserRouter,Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';



function App() {
  return (
   <>
   <UserProvider>
    <BrowserRouter>
    <Routes>
    <Route path="/" element={<Layout />}>
      <Route index element={<Home/>}/>
      <Route path="/:name,:price" element={<Home/>}/>
      </Route>
    </Routes>
    </BrowserRouter>
   </UserProvider>
   
   
   </>
  );
}

export default App;
