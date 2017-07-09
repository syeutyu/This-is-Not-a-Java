var express = require('express');
var database = require('./Mongo/database');
var mongoose = require('mongoose');
var bodyparser = require('body-parser');
var user = require('./route/user');
var config = require('./config');
var app = express();

database();
    
app.use(bodyparser.urlencoded({
    extended: false
}));

app.use(bodyparser.json());

app.listen(3000,function(){
    console.log('Port On');
});

app.post('/Ras/Send', user.checkFire);

