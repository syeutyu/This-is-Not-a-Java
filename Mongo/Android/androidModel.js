let mongoose = require('mongoose');
let schema = require('./AndroidSchema');
let model = {};

schema.static('findById', function (userId, callback) {
    console.log('로그인 찾기' + userId);
    return this.find({ userId: userId }, callback);
});

schema.static('findByPw', function (passWord, callback) {
    console.log('비밀번호 찾기' + passWord);
    this.salt = this.makeSalt();
    return this.find({ passWord: passWord }, callback);
});

schema.method('makeSalt',function(){
    return Math.round((new Date().valueOf() * Math.random())) + '';
});

schema.static('findBySalt',function(salt,callback){
    return this.find({salt:salt},callback);
})

model = mongoose.model('Android', schema);

module.exports = model;