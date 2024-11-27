export default function SanitiseContainer() {
  // TODO: Loader
  // TODO: Output
  return (
    <div>
      <form className="flex flex-col">
        <textarea
          className="rounded-md border p-4"
          placeholder="Add the text you would like to sanitise"
          rows="8"
        ></textarea>
        <button className="text-white bg-gray py-2 px-4 w-[150px] rounded-xl">
          Sanitise Text
        </button>
      </form>
    </div>
  )
}