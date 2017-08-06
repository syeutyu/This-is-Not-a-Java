let mongoose = require('mongoose');

let schema =  mongoose.Schema({

    userId :  {type:String, default:"", unique : true}, 
    name :{type:String, default:""},
    passWord : {type:String, default:""},
    salt: { type: Number, required: true, unique : true},
    moduleCode : {type:String, default:null },
    R_num : {type:Number, default:null},
},{collection : 'Android'});

module.exports = schema;