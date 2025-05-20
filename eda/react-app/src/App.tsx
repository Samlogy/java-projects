import {
  Link,
  Route,
  BrowserRouter as Router,
  Routes
} from "react-router-dom";
import './App.css';
import OrderPage from "./components/OrderPage";
import TodoPage from "./components/TodoPage";
import { KeycloakProvider } from './KeycloakContext';


function NavBar() {
  return(
    <nav>
      <ul>
        <>
          <Link to="/orders">Orders</Link>
        </>
        <>
          <Link to="/">Todos</Link>
        </>
      </ul>
    </nav>
  )
}

function Routing() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<TodoPage /> } />
        <Route path="/orders" element={<OrderPage />} />
      </Routes>
    </Router>
  );
}

function App() {
  return (
    <KeycloakProvider>
      <NavBar />
      <Routing />
    </KeycloakProvider>
  );
}


export default App;