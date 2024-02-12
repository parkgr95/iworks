/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx,ts,tsx}", 
    'node_modules/flowbite-react/lib/esm/**/*.js',
  ],
  theme: {
    extend: {
      colors: {
        'mainBlue': '#1F4068',
        'mainBlack': '#1B1C25',
        'mainGreen': '#206A5D',
        'mainGray': '#EBECF1',
      },
      height: {
        '128': '32rem',
      }
    }
  },
  plugins: [
    // ...
    require('flowbite/plugin'),
  ],
};