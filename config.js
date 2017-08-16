let config = {

    server_port: 3000,
    db_url: 'mongodb://dong:kyera12589@ds137261.mlab.com:37261/dayzen',
    db_schemas: [{
            "file": './Lasbery/lasberyModel',
            "modelName": 'lasbery'
        }, {
            "file": "./Android/androidModel",
            "modelName": 'android'
        },
        {
            "file": "./Test/testModel",
            "modelName": 'test'
        }
    ]
}

module.exports = config;