import { createRoot } from 'react-dom/client'
import { BrowserRouter} from 'react-router-dom'
import './index.css'
import Layout from './Layout'

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <Layout/>
  </BrowserRouter>
)
