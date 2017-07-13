const date = require('date-utils');
const LasModel = require('../Mongo/Schema');
const AndroidModel = require('../Mongo/AndroidSchema');
const time = require('./time');

exports.checkFire = (req, res) => {
  let spot = req.body.spot;
  let arr = spot.split(",");
  console.log(arr)
  let times = time.getTimeStamp();
  console.log(time)

  let users = new LasModel({

    "spot": arr[0],
    "time": times,
    "check": arr[1]
  
  });

  users.save((err) => {
    if (err) {
      console.log(err);
    }
  });

};

exports.signin = (req, res) => {
  console.log('로그인 실행');
  let id = req.body.id;
  let pw = req.body.password;

  console.log(id + ',' + pw);
  res.writeHead(200);
}

exports.signup = (req, res) => {

  console.log('회원가입 실행')
  
  let id = req.body.id;
  let pw = req.body.password;
  let code = req.body.code;

  let user = new AndroidModel({
    "userId": id,
    "passWord": pw,
    "moduleCode": code
  });

  user.save((err)=>{
    console.log('err : '+err);
  });
}