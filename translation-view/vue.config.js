const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  devServer: {
    open: true,
    host: 'localhost',
    port: 9080,
    https: false,
    proxy: {
      ['/translation-engine']: {
        target: 'http://localhost:8090',
        ws: true,
        changOrigin: true,
        pathRewrite: {
          ['^/translation-engine']: '/translationEngine'
        }
      }
    },

    client: {
      overlay: false
    }
  }
})
