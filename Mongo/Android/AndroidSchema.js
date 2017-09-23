let mongoose = require('mongoose');

let schema = mongoose.Schema({
    token: { type: String, default: "", unique: true },
    name: { type: String, default: "" },
    place: { type: String, default: "" },
    code: { type: String, default: null },
    switch: { type: Boolean, default: false }
}, { collection: 'Android' });

/*
    token : 안드로이드 Fcm클라이언트 키 값으로 Primary Key 역할
    name : 사용자 이름
    place : 모듈 두는 위치
    code : 모듈 번호
    switch : Test를 위한 bool값
*/
schema.statics.checkSave = function(token) {
    return new Promise((resolve, reject) => {
        this.find({ token }, (err, find) => {
            (0 < find.length) ? resolve(): reject('Token is Not Found');
        });
    });
};

schema.statics.saveUser = function(data) {
    let user = new this({
        "token": data.token,
        "name": data.name,
        "place": data.place,
        "code": data.code,
        "switch": data.switch
    });

    return user.save();
};

schema.methods.updateData = function(bool) {
    this.switch = bool;
    this.save();
}

model = mongoose.model('Android', schema);

module.exports = model;