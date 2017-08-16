var express = require('express');
var database = require('./Mongo/database');
var mongoose = require('mongoose');
var bodyparser = require('body-parser');
var config = require('./config');
var session = require('express-session');
var androidRoute = require('./route/androidRouter/router');
var lasberyRoute = require('./route/lasberyRouter/router');
var app = express();


app.use(bodyparser.urlencoded({
    extended: false
}));

app.use(session({
    key: 'Java',
    secret: 'secret',
    resave: false,
    saveUninitialized: true
}));

app.use(bodyparser.json());

app.use('/', androidRoute);
app.use('/', lasberyRoute);


app.listen(3000, function() {
    database.init(app, config);
    console.log('Port On');
});