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
      const words = response.json()
      console.log(words)
    }
    fetchData()
  }, [])
  return (
    <div>
      {words.length > 0 &&
        words.map((word) => {
          return <Word text={word.word} />
        })
      }
    </div>
  )
}