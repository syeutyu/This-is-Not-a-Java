var express = require('express');
var database = require('./Mongo/database');
var mongoose = require('mongoose');
var bodyparser = require('body-parser');
var config = require('./config');
let route = require('./route/router');
var app = express();

database();
    
app.use(bodyparser.urlencoded({
    extended: false
}));

app.use(bodyparser.json());

app.use('/',route);

app.use(function(err, req, res, next) {
  console.error(err.stack);
  res.status(400).send('Something broke!');
});

app.listen(3000,function(){
    console.log('Port On');
});
  