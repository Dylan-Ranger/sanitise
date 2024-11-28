import {useEffect, useRef, useState} from "react";
import Word from "./Word.jsx";
import AddWord from "./AddWord.jsx";

export default function Words() {
  const dialog = useRef()
  const [words, setWords] = useState([])
  // TODO: loading
  // TODO: emtpy state
  useEffect( () => {
    const fetchData = async () => {
      // TODO: externalise back end url to env.
      const response = await fetch('http://localhost:8080/word/list')
      if (!response.ok) {
        // TODO: handle error
      }
      const words = await response.json()
      setWords(words.data)
    }
    fetchData()
  }, [])
  function handleOpenWordModal() {
    dialog.current.open()
  }
  function handleWordsUpdate(word) {
    setWords(prevState => {
      return prevState.some(w => w.id === word.id)
        ? prevState.map(w => w.id === word.id ? word : w)
        : [...prevState, word];
    });
  }

  function handleDeleteWord(word) {
    setWords(prevState => {
      return prevState.filter(w => word.id !== w.id)
    })
  }

  return (
    <>
      <div className="flex flex-wrap gap-2 mb-6">
        {words && words.length > 0 &&
          words.map((word) => {
            return <Word
              word={word}
              key={word.word}
              update={handleWordsUpdate}
              onDelete={handleDeleteWord}
            />
          })
        }
      </div>
      <button
        className="text-white bg-gray py-2 px-4 w-[150px] rounded-xl mb-6"
        onClick={handleOpenWordModal}>
        Add New Word
      </button>
      <AddWord
        ref={dialog}
        onUpdate={handleWordsUpdate}
      />
    </>
  )
}
