import {createPortal} from "react-dom";
import {forwardRef, useImperativeHandle, useRef, useState} from "react";

const AddWord = forwardRef(function AddWord(
  {onUpdate}, ref
) {
  const dialog = useRef()
  const [word, setWord] = useState('');
  const [errorMessage, setErrorMessage] = useState()

  useImperativeHandle(ref, () => {
    return {
      open() {
        dialog.current.showModal()
      }
    }
  })

  function handleChange(event) {
    setWord(event.target.value)
  }
  async function handleCreateWord(event) {
    event.preventDefault()
    setErrorMessage(null)

    try {
      const response = await fetch('http://localhost:8080/word', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          word
        }),
      });

      if (!response.ok) {
        const error = await response.json()
        setErrorMessage(error.message)
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const result = await response.json()
      onUpdate(result.word)
      setWord(null)
      handleCloseModal()
    } catch (error) {
      console.error('Error:', error);
    }
  }

  function handleCloseModal() {
    dialog.current.close()
  }

  return createPortal(
    <dialog
      className="bg-blue rounded-lg p-5 relative overflow-visible wordDialog"
      ref={dialog}>
      <div
        onClick={handleCloseModal}
        className="rounded-full bg-gray w-8 h-8 text-center leading-8 absolute top-0 right-0 -mt-2 -mr-2 font-bold text-white">
        X
      </div>
      <h2 className="text-2xl font-bold mb-6">Add a new word</h2>
      <form
        className="flex flex-col"
        method="dialog">
        <input
          type="text"
          onChange={handleChange}
          value={word || ''}
          className={"p-2 border rounded-md mb-2"}
          placeholder="Enter a word" />
        {errorMessage &&
          <p className="text-sm text-red-700">{errorMessage}</p>
        }
        <button
          className="text-white bg-gray py-2 px-4 w-[150px] rounded-xl mt-4"
          onClick={handleCreateWord}
          disabled={!word}>
          Submit
        </button>
      </form>
    </dialog>,
    document.getElementById("modal")
  )
})
export default AddWord;