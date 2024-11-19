function App() {
  const API_KEY = import.meta.env.VITE_API_KEY;
  const API_URL = import.meta.env.VITE_API_URL;
  return (
    <>
      <h1>Hello World</h1>
      <p>{API_KEY}</p>
      <p>{API_URL}</p>
    </>
  );
}

export default App;
