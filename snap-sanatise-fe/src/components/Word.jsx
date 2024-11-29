import AddWord from "./AddWord.jsx";
import {useRef} from "react";

export default function Word({word, update, onDelete}) {
  const dialog = useRef()
  function handleOpenModal() {
    dialog.current.open()
  }

  return (
    <>
      <div
        className="relative bg-blue rounded-md py-1 px-2 lg:py-2 lg:px-4 text-white hover:scale-110 cursor-pointer"
        onClick={() => handleOpenModal()}>
        {word.word}
      </div>
      <AddWord
        editWord={word}
        ref={dialog}
        onUpdate={update}
        onDelete={onDelete}
      />
    </>
  )
}