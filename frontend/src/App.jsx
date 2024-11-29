import axios from "axios";
import { useState, useEffect } from "react";

function App() {
  const URL = "http://localhost:8080/";
  const vite_message = "Vite 환경 변수를 불러와서 화면에 출력하시오.";
  ("Spring Boot 환경 변수를 요청해서 화면에 출력하시오.");
  const [spring_message, setSpringMessage] = useState();

  // Spring Boot 환경 변수 요청 함수를 완성하시오.
  async function fetchMessage() {}

  useEffect(() => {
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
