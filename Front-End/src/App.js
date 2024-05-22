import logo from './logo.svg';
import './App.css';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Routes,
} from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Signup from './pages/Signup';
import AboutUs from './pages/AboutUs';
import { ContextProvider } from './context/Context';
import TutorAdvertisement from './pages/TutorAdvertisement';
import TutorDetail from './pages/TutorDetail';

function App() {
  return (
    <ContextProvider>
      <Router>
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/login' element={<Login />} />
          <Route path='/signup' element={<Signup />} />
          <Route path='/about-us' element={<AboutUs />} />
          <Route
            path='/tutor-advertisements'
            element={<TutorAdvertisement />}
          />
            <Route path='/tutor-advertisements/:id' element={<TutorDetail />} />
          <Route path='*' element={<h1>Not Found</h1>} />
        </Routes>
      </Router>
    </ContextProvider>
  );
}

export default App;
