var express = require('express');
var mongoose = require('mongoose');
var bodyparser = require('body-parser');
var user = require('./route/user');
var database = require('./Mongo/database');
var config = require('./config');
var app = express();

app.use(bodyparser.urlencoded({
    extended: false
}));

app.use(bodyparser.json());

app.listen(3000,function(){
    console.log('Port On');
    database.init(app,config);
});

app.post('/Ras/Send', user.checkFire);

