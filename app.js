const express = require('express');
const database = require('./Mongo/database');
const mongoose = require('mongoose');
const bodyparser = require('body-parser');
const config = require('./config');
const session = require('express-session');
const cookieParser = require('cookie-parser')
const androidRoute = require('./route/androidRouter/router');
const lasberyRoute = require('./route/lasberyRouter/router');
const app = express();


app.use(bodyparser.urlencoded({
    extended: false
}));

app.use(session({
    key: 'Java',
    secret: 'secret',
    resave: false
}));

app.use(cookieParser());
app.use(bodyparser.json());

app.use('/', androidRoute);
app.use('/', lasberyRoute);


app.listen(3000, function() {
    database.init(config);
    console.log('Port On' + config.server_port);
});