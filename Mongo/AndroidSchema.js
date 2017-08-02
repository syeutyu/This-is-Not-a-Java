let mongoose = require('mongoose');
mongoose.Promise = global.Promise;

let schema = new mongoose.Schema({

    userId :  {type:String, default:""}, 
    name :{type:String, default:""},
    passWord : {type:String, default:""},
    salt : {type:Number, default:null},
    moduleCode : {type:String, default:null},
    R_num : {type:Number, default:null},
});

schema.static('findById',function(userId,callback){
    console.log('로그인 찾기'+userId);   
    return this.find({userId : userId},callback);
});

schema.static('findByPw',function(passWord,callback){
    console.log('비밀번호 찾기'+passWord);   
    return this.find({passWord : passWord},callback);
});

module.exports = mongoose.model('JavaProject_Android', schema);;