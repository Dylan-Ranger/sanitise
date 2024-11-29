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
      console.log(errorResult)
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
          className="rounded-md border p-4 mb-6"
          placeholder="Add the text you would like to sanitise"
          rows="6"
          value={input}
          onChange={handleTextInput}
        ></textarea>
        <p className="text-sm text-red-700 mb-6 -mt-6">{message}</p>
        <button
          className="text-white bg-gray py-2 px-4 w-[150px] rounded-xl mb-6"
          disabled={!input}>
          Sanitise Text
        </button>
      </form>
      { sanitisedString &&
        <>
          <div className="flex justify-between">
            <h2 className="text-3xl font-extrabold mb-6">Result</h2>
            <div className="flex justify-center cursor-pointer"
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