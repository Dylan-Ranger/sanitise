import {useEffect, useState} from "react";
import Word from "./Word.jsx";

export default function Words() {
  const [words, setWords] = useState([])
  useEffect( () => {
    const fetchData = async () => {
      const response = await fetch('http://localhost:8080/word/list')
      if (!response.ok) {
        // handle error
      }
      const words = await response.json()
      setWords(words)
    }
    fetchData()
  }, [])
  return (
    <div>
      {words && words.length > 0 &&
        words.map((word) => {
          console.log(word)
          return <Word word={word.word} key={word.word} />
        })
      }
    </div>
  )
}