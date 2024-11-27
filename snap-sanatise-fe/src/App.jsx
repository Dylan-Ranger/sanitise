import { useState } from 'react'
import Words from "./components/Words.jsx";
import SanitiseContainer from "./components/SanitiseContainer.jsx";

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className="h-svh py-8 lg:w-3/4">
      <h1 className="text-4xl font-extrabold tex-black">Word Sanitiser</h1>
      <Words />
      <SanitiseContainer />
    </div>
  )
}

export default App
