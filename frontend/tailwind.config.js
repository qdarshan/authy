/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'primary': '#1E201E',
        'secondary': '#3C3D37',
        'tertiary': '#697565',
        'quaternary': '#ECDFCC',
        'quinary': '#37474F',
      },
      fontFamily: {
        'sans': ['Poppins', 'sans-serif'],
      },
    },
  },
  plugins: [],

}
