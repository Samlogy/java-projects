import useKeycloak from "@/useKeycloak";
import axios from "axios";
import { useEffect, useState } from "react";

export default function OrderPage() {
  const [orders, setOrders] = useState([]);

  const { keycloak, authenticated } = useKeycloak();

  const loadOrders = async () => {
    try {
      const response = await axios.get("http://localhost:4003/api/orders");
      setOrders(response.data);
    } catch (error) {
      console.error("Erreur => Orders: ", error);
    }
  };

  useEffect(() => {
    loadOrders();
  }, []);

  console.log('keycloak => ', { keycloak, authenticated })

  return (
    <div>
      <h2> Orders List </h2>

      {authenticated ? (
        <div>
          <p>Hello, {keycloak?.idTokenParsed.preferred_username}!</p>

          <div>
            {orders && orders.length ? (
              orders.map((order:any) => (
                <div key={order.id}>
                  <p>
                    {" "}
                    {order.product} - {order.quantity} - {order.price} -{" "}
                    {order.status} - {order.customerName}{" "}
                  </p>
                </div>
              ))
            ) : (
              <p>No Order available.</p>
            )}
          </div>
        </div>
      ) : (
        <div>
          <p>Please log in to access your personalized content.</p>
        </div>
      )}
    </div>
  );
}
