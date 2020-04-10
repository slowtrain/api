const path = require('path');

module.exports = {
  configureWebpack: {
    resolve: {
        alias: {
            '@': path.join(__dirname, 'src/')
        }
    }
  },
  // publicPath: '/',
  
  publicPath:
    process.env.NODE_ENV === "production"
      ? "/justdo-vue-pro/preview/horizontal-default-light/"
      : "/",
  // where to output built files
  outputDir: 'dist',

  // whether to use eslint-loader for lint on save.
  // valid values: true | false | 'error'
  // when set to 'error', lint errors will cause compilation to fail.
  lintOnSave: true,

  // https://cli.vuejs.org/config/#runtimecompiler
  runtimeCompiler: false,

  // babel-loader skips `node_modules` deps by default.
  // explicitly transpile a dependency with this option.
  transpileDependencies: [
    /* string or regex */
  ],

  productionSourceMap: process.env.NODE_ENV !== 'production',

    // configure webpack-dev-server behavior
  devServer: {
      open: process.platform === 'darwin',
      host: 'localhost',
      port: 80,
      https: false,
      // hotOnly: false,
      proxy: {
        '/episode/api': {
          target: 'http://localhost:8080/episode/api',
          changeOrigin: false,
          pathRewrite: {
            '^/episode/api': ''
          }
        }
      }
      
  }
};
