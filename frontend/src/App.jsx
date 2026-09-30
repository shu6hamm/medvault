import { useEffect, useState } from "react";

function App() {
  const [health, setHealth] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/health")
      .then((res) => res.json())
      .then((data) => setHealth(data))
      .catch(() => setHealth({ status: "DOWN" }));
  }, []);

  return (
    <div style={{ padding: 40, fontFamily: "sans-serif" }}>
      <h1>MedVault</h1>
      <p>Backend status: {health ? health.status : "Checking..."}</p>
    </div>
  );
}

export default App;