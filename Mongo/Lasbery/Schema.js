let mongoose = require('mongoose');

let schema = mongoose.Schema({
    salt: { type: String, default: "", unique: true },
    time: { type: String, default: "" },
    Message: { type: String, default: "" },
    spot: { type: String, default: "" },
    user_name: { type: String, default: "" },
    check: Boolean
}, { collection: 'Lasbery' });

module.exports = schema;