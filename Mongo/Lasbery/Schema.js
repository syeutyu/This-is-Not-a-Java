let mongoose = require('mongoose');

let schema =  mongoose.Schema({
    R_num: {type:String, default : "", unique : true},
    time:{type:String, default : ""},
    Message: {type:String, default : ""},
    salt: {type:Number, default : ""},
    spot: {type:String, default : ""},
    user_name:{type:String, default : ""},
    check: Boolean
},{collection : 'Lasbery'});

module.exports = schema;