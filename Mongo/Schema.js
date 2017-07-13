let mongoose = require('mongoose');
mongoose.Promise = global.Promise;

let schema = new mongoose.Schema({
    R_num: {type:String, default : ""},
    time:{type:String, default : ""},
    Message: {type:String, default : ""},
    salt: {type:Number, default : ""},
    spot: {type:String, default : ""},
    user_name:{type:String, default : ""},
    check: Boolean
});

module.exports = mongoose.model('JavaProject_Lasbery', schema);;