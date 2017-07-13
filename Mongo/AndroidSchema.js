let mongoose = require('mongoose');
mongoose.Promise = global.Promise;

let schema = new mongoose.Schema({

    userId : {type : String , unique : true, required : true},
    passWord : {type : String , required :true},
    salt : {type : String},
    moduleCode : {type : Number , unique : true}

});

module.exports = mongoose.model('JavaProject_Android', schema);;