let mongoose = require('mongoose');

let schema = mongoose.Schema({
    userId: { type: String, default: "", unique: true },
    name: { type: String, default: "" },
    passWord: { type: String, default: "" },
    salt: { type: String, unique: true },
    moduleCode: { type: String, default: null },
}, { collection: 'Android' });

module.exports = schema;