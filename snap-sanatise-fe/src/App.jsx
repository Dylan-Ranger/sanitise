import { useState } from 'react'
import Words from "./components/Words.jsx";

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Words />
    </>
  )
}

export default App
