import { createRoot } from 'react-dom/client'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import App from './App'
import Login from './components/Login'
import Nav from './components/nav/Nav'
import './index.css'
import { Toaster } from './components/ui/toaster'
import Dashboard from './components/Dashboard'

const Layout = () => {
  return (
    <>
      <Nav />
      <Toaster/>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </>
  )
}

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <Layout />
  </BrowserRouter>
)
