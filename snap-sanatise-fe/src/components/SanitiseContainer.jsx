import { useState} from "react";
import React from 'react';
export default function SanitiseContainer() {
  const [input, setInput] = useState('')
  const [sanitisedString, setSanitisedString] = useState('')
  const [message, setMessage] = useState('')

  function handleTextInput(event) {
    setInput(event.target.value)
  }
  async function handleSanitiseString(event) {
    event.preventDefault()
    setMessage('')
    setSanitisedString('')
    const response = await fetch(
      'http://localhost:8080/sanitise', {
        method: 'POST',
        headers: {
          'Content-type': 'application/json',
        },
        body: JSON.stringify(input)
      }
    )
    if (!response.ok) {
      const errorResult = await response.json()
      setMessage(errorResult.message)
      throw new Error("Text could not be sanitised")
    }
    const result = await response.json()
    setSanitisedString(result.result.replaceAll('\"', ''))
  }

  function handleCopyResult() {
    const el = document.getElementById("sanitisedText")
    navigator.clipboard.writeText(el.innerText)
  }

  return (
    <div>
      <form className="flex flex-col" onSubmit={handleSanitiseString}>
        <textarea
          className="rounded-md border p-4 mb-4 lg:mb-6"
          placeholder="Add the text you would like to sanitise"
          rows="6"
          value={input}
          onChange={handleTextInput}
        ></textarea>
        {message &&<p className="text-sm text-red-700 mb-4 lg:mb-6">{message}</p>}
        <button
          className="text-white bg-gray py-2 px-4 w-[150px] rounded-xl mb-4 lg:mb-6 cursor-pointer"
          disabled={!input}>
          Sanitise Text
        </button>
      </form>
      { sanitisedString &&
        <>
          <div className="flex justify-between border-t-2 border-blue pt-4 lg:pt-6">
            <h2 className="text-3xl font-extrabold">Result</h2>
            <div className="flex justify-center cursor-pointer pb-4 hover:animate-pulse"
                 onClick={handleCopyResult}>
              <p className="pt-2 mr-2 font-bold">Copy Text</p>
              <img
                src={'/copy.png'}
                className="block h-10 w-10"/>
            </div>
          </div>
          <p className="leading-8 pb-20" id="sanitisedText">
            {sanitisedString.split('\\n').map((text, index) => (
              <React.Fragment key={index}>
                {text}
                <br />
              </React.Fragment>
              ))}
          </p>
        </>
      }
    </div>
  )
}