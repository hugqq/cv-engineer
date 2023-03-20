module.exports = {
    publicPath: './',
    assetsDir: 'static',
    productionSourceMap: false,

    // webpack-dev-server 相关配置
    devServer: {
        host: '0.0.0.0',
        port: 80,
        proxy: {
            [process.env.VUE_APP_BASE_API]: {
                //后端服务地址和端口
                target: 'http://localhost:8080',
                //是否跨域
                changeOrigin: true,
                pathRewrite: {
                  ['^' + process.env.VUE_APP_BASE_API]: ''
                }
            }
        },
        disableHostCheck: true
    }
}