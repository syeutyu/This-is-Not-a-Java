exports.signin = (req, res) => {
  console.log('로그인 실행');
  let id = req.body.id;
  let pw = req.body.password;
  let database = req.app.get('database');

  console.log(id + ',' + pw);
  database.android.findById(id, (err, find) => {

    if (Object.keys(find).length != 0) {

      database.android.findByPw(pw, (err, findpw) => {

        if (Object.keys(findpw).length != 0) {
          req.session.key = findpw[0]._doc.salt;
          res.writeHead(200);
          res.end();

        } else {

          res.status(401).send({
            message: 'Not find Pw'
          });

          res.end();
        }
      });


    } else {
      res.status(401).send({
        message: 'Not find Id'
      });

      res.end();

    }
  });
}

exports.signup = (req, res) => {

  console.log('회원가입 실행')
  let database = req.app.get('database');
  let id = req.body.id;
  let pw = req.body.password;
  let code = req.body.code;
  let name = req.body.name;
  let rno = req.body.rno;


  database.android.findById(id, (err, find) => {
    if (err) {
      console.log(err);
      res.status(402).send({
        err: '회원가입 도중 오류 발생 : ' + err
      });
      res.end();
    }
    if (find == null) {
      let user = new database.android({
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
    } else {
      res.status(402).send({
        err: '중복된 회원정보 입니다'
      });
      res.end();
    }
  })

}

exports.search = (req, res) => {

  if (req.session.key) {
    let roomNum = req.body.R_num;
    let database = req.app.get('database');

    if (roomNum) {

      databae.lasbery.findR_num(roomNum, (err, find) => {

        if (err) {
          console.log(err);
          res.status(400).send({
            err: err
          });
          res.end();
        }

        if (find) {
          res.status(200).send(JSON.stringify(find));
          res.end();
        }
      })

    } else {

      console.log('데이터 못 찾음');
      res.status(400).send({
        err: 'Not Found R_num Data'
      });
      res.end();
    }
  } else {
    res.status(400).send({
      err: '세션키값이 존재하지않습니다'
    })
    res.end();
  }
}

exports.test = (req, res) => {

  if (req.sesion.key) {
    let database = req.app.get('database');
    let key = req.session.key;
    database.android.findBySalt(key,(err,find)=>{
      if (err) {
        console.log(err);
        res.status(400).send({
          err: err
        });
        res.end();
      }
      if(find){
        let found = find[0]._doc.R_num;
        let user = new database.test({
          'R_num' : found,
          'check' : true
        });

        user.save();

        res.status(200);
        res.end();
      }
    })

  } else {
    res.status(400).send({
      err: '세션값이 존재하지않습니다'
    })
    res.end();
  }

}