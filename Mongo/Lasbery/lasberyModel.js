let schema = require('./Schema');
let mongoose = require('mongoose');

let model = {};
schema.static('findR_num',function(R_num,callback){
    return this.find({R_num : R_num},callback);
})

model = mongoose.model('Lasbery', schema);

module.exports = model;