var express = require('express');
var mongoose = require('mongoose');
var bodyparser = require('body-parser');
var user = require('./route/user');
var app = express();

var db = mongoose.connection;
mongoose.connect("mongodb://localhost:27017/local");

app.use(bodyparser.urlencoded({
    extended: false
}));

app.use(bodyparser.json());

app.listen(3000,function(){
    console.log('Port On');
});

app.post('/', user.checkFire);

