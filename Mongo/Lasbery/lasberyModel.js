let schema = require('./Schema');
let mongoose = require('mongoose');

let model = {};
schema.static('findR_num',function(R_num,callback){
    return this.find({R_num : R_num},callback);
});

schema.static('findAll',function(callback){
    return this.find({},callback);
});

schema.static('findByBool',function(bool,key,callback){
    this.find({check:bool , salt : key},callback);
})

model = mongoose.model('Lasbery', schema);

module.exports = model;