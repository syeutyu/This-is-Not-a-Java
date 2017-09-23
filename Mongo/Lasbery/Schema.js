let mongoose = require('mongoose');

let schema = mongoose.Schema({
    model: { type: String, default: "" },
    time: { type: String, default: "" },
    check: { type: Boolean, default: false }
}, { collection: 'Lasbery' });

/*
    model : 기기번호
    time : 화재발생 시간
    check : 테스트 판별 
*/

schema.statics.saveFire = function(model, check, time) {
    console.log('화재 기록 저장');
    let data = new this({
        "model": model,
        "check": check,
        "time": time
    });
    data.save();

};
model = mongoose.model('Lasbery', schema);

module.exports = model;