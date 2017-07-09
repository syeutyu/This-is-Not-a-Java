let mongoose = require('mongoose');
let schema = require('./Schema');
let crypto = require('crypto');
let model = {};

model = mongoose.model('java',schema);

module.exports = model;