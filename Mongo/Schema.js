var crypto = require('crypto');
let mongoose = require('mongoose');
var Schema ={};

Schema.createSchema = (mongoose) => {
    var UserSchema = mongoose.Schema({
        R_num: { type: String, unique: true  },
        time: { type: Date,index: {unique: false},'default': Date.now()},
        Message: {type: String,'default': ''},
        salt: {type: String,required: true},
        spot: {type: String,'default': ''},
        user_name: {type: String,'default': ''}
    });
}

module.exports = Schema;