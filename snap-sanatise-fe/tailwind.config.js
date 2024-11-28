/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        "black": "#222831",
        "gray": "#393E46",
        "blue": "#00ADB5",
        "white": "#EEEEEE"
      },
      screens: {
        '3xl': '1800px'
      }
    },
  },
  plugins: [],
}

