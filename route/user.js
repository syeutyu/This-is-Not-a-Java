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
  AndroidModel.findById(id, (err, find) => {
    
    if (Object.keys(find).length!= 0) {

      AndroidModel.findByPw(pw, (err, findpw) => {
        
        if(Object.keys(findpw).length!= 0){
          res.writeHead(200);
          res.end();
      } else{

        res.status(401).send({message :'Not find Pw'});

         res.end();
      }
      });


    } else {
        res.status(401).send({message :'Not find Id'});

      res.end();
    }
  });
}

exports.signup = (req, res) => {

  console.log('회원가입 실행')

  let id = req.body.id;
  let pw = req.body.password;
  let code = req.body.code;
  let name = req.body.name;
  let rno = req.body.rno;

  let user = new AndroidModel({
    "userId": id,
    "passWord": pw,
    "moduleCode": code,
    "name": name,
    "R_num": rno
  });

  user.save(function (err) {

    if (err) {
      console.log(err.stack);
      res.send(400, {
        err: 'Android DB save Failed'
      });
      res.end();

    } else
      console.log('Android Save complete')
    res.status(200);
    res.end();

  });
}