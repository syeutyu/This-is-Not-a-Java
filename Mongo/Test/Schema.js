let mongoose = require('mongoose');

let schema = mongoose.Schema({
    R_num : {type : String, unique : true},
    check : Boolean
}, { collection: 'Record'});

module.exports = schema;