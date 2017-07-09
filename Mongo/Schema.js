let mongoose = require('mongoose');

let schema = new mongoose.Schema({
    R_num: { type: String},
    time: { type: Date,index: {unique: false},'default': Date.now()},
    Message: {type: String,'default': ''},
    salt: {type: String},
    spot: {type: String,'default': ''},
    user_name: {type: String,'default': ''}
});

module.exports = mongoose.model('JavaProject1', schema);;