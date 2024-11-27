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
      setWords(words)
    }
    fetchData()
  }, [])
  function handleOpenWordModal() {
    dialog.current.open()
  }
  function handleWordsUpdate(word) {
    setWords(prevState => {
      return [...prevState, word]
    })
  }
  return (
    <>
      <div className="flex flex-wrap">
        {words && words.length > 0 &&
          words.map((word) => {
            return <Word word={word.word} key={word.word} />
          })
        }
      </div>
      <button
        className="text-white bg-gray py-2 px-4 w-[150px] rounded-xl"
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
