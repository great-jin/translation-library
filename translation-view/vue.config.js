const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  devServer: {
    open: true,
    host: process.env.VUE_APP_API_HOST,
    port: process.env.VUE_APP_API_PORT,
    https: false,
    proxy: {
      [process.env.VUE_APP_API_BASE_PREFIX]: {
        target: process.env.VUE_APP_API_BASE_URL,
        ws: true,
        changOrigin: true,
        pathRewrite: {
          [`^${process.env.VUE_APP_API_BASE_PREFIX}`]: process.env.VUE_APP_API_SERVLET_PREFIX
        }
      }
    },

    client: {
      overlay: false
    }
  }
})
