let schema = require('./Schema');
let mongoose = require('mongoose');
let model = {};
schema.static('findByRecord',function(R_num,callback){
    return this.find({R_num : R_num},callback);
});

model = mongoose.model('Record', schema);

module.exports = model;