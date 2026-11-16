import path from 'path'
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// esse cara aqui vai ser usado para colocarmos o caminho relativo @ nas imports
// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react()],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src')
        }
    }
})