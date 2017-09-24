const logic = require('./logic');
const android = require('../../Mongo/Android/AndroidSchema');
const lasbery = require('../../Mongo/Lasbery/Schema');

exports.search = (data) => {
    return new Promise((resolve, reject) => {
        if (typeof data != 'undefined') {
            android.find({ "token": data }).then((data) => {
                if (data) {
                    resolve(data);
                } else {
                    throw 'Not Found Data';
                }
            });
        } else {
            android.find({}).then((data) => {
                if (data) {
                    resolve(data);
                } else {
                    throw 'Not Found Data';
                }
            })
        }
    });
};