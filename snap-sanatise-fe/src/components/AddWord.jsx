import {createPortal} from "react-dom";
import {forwardRef, useImperativeHandle, useRef, useState} from "react";

const AddWord = forwardRef(function AddWord(
  {onUpdate, onDelete, editWord= null}, ref
) {
  const isEdit = editWord !== null
  const dialog = useRef()
  const [word, setWord] = useState(isEdit ? editWord.word : '');
  const [errorMessage, setErrorMessage] = useState('')

  useImperativeHandle(ref, () => {
    return {
      open() {
        dialog.current.showModal()
      },
      close() {
        setWord('')
        dialog.current.close()
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
      const url = isEdit ? `http://localhost:8080/word/${editWord.id}` : 'http://localhost:8080/word'
      const body = isEdit ? {
        word : word,
        id: editWord.id
      } : {
        word
      }

      const response = await fetch(url, {
        method: isEdit ? 'PUT' : 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
      });

      if (!response.ok) {
        const error = await response.json()
        setErrorMessage(error.message)
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const result = await response.json()
      onUpdate(result.data)
      setWord(null)
      dialog.current.close()
    } catch (error) {
      console.error('Error:', error);
    }
  }

  async function handleDeleteWord(event) {
    event.preventDefault()
    const response = await fetch(`http://localhost:8080/word/${editWord.id}` , {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      }
    });
    if (!response.ok) {
      setErrorMessage("Could not delete the word!")
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    onDelete(editWord)
    dialog.current.close()
  }

  return createPortal(
    <dialog
      className="bg-blue rounded-lg p-5 relative overflow-visible wordDialog"
      ref={dialog}>
      <div
        onClick={() => dialog.current.close()}
        className="rounded-full bg-gray w-8 h-8 text-center leading-8 cursor-pointer
                   absolute top-0 right-0 -mt-2 -mr-2 font-bold text-white">
        X
      </div>
      <h2 className="text-2xl font-bold mb-6">{isEdit ? "Edit word" : "Add New Word"}</h2>
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
        <div className="flex justify-between content-center mt-4">
          <button
            className="text-white bg-gray py-2 px-4 w-[150px] rounded-xl"
            onClick={handleCreateWord}
            disabled={!word}>
            {isEdit ? "Update" : "Create"}
          </button>
          {isEdit &&
            <img
              src={"/trash.svg"}
              className="cursor-pointer"
              height="25"
              width="25"
              onClick={handleDeleteWord}
              alt="trashcan"/>}
        </div>
      </form>
    </dialog>,
    document.getElementById("modal")
  )
})
export default AddWord;