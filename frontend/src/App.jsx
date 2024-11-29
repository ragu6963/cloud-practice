import axios from "axios";
import { useState, useEffect } from "react";

function App() {
  const vite_message = import.meta.env.VITE_MESSAGE;
  const [spring_message, setSpringMessage] = useState("");
  const url = "http://localhost:8080/";
  useEffect(() => {
    async function fetchMessage() {
      const response = await axios.get(url);
      setSpringMessage(response.data);
    }
    fetchMessage();
  }, []);

  return (
    <>
      <h1>{vite_message}</h1>
      <h1>{spring_message}</h1>
    </>
  );
}

export default App;
