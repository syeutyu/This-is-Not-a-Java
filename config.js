let config = {

    server_port: 3000,
    db_url: 'mongodb://localhost:27017',
    db_schemas: [{
            "file": './Lasbery/lasberyModel',
            "modelName": 'lasbery'
        }, {
            "file": "./Android/androidModel",
            "modelName": 'android'
        },
        {   
            "file" : "./Test/testModel",
            "modelName" : 'test'
        }
    ]
}

module.exports = config;