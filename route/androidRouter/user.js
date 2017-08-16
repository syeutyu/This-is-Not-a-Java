exports.signin = (req, res) => {
    let id = req.body.id;
    let pw = req.body.password;
    let database = req.app.get('database');

    console.log(id + ' , ' + pw);
    database.android.findById(id, (err, find) => {

        if (Object.keys(find).length != 0) {

            database.android.findByPw(pw, (err, findpw) => {

                if (Object.keys(findpw).length != 0) {

                    req.session.key = findpw[0]._doc.salt;
                    console.log(req.session.key);
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

function makeSalt() {
    return Math.round((new Date().valueOf() * Math.random())) + ''
}

exports.signup = (req, res) => {

    console.log('signUp');
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
                err: 'Error : ' + err
            });
            res.end();
        }

        if (find == false) {

            var salt = makeSalt()

            let user = new database.android({
                "userId": id,
                "passWord": pw,
                "salt": salt,
                "moduleCode": code,
                "name": name,
                "R_num": rno

            });

            user.save(function(err) {

                if (err) {
                    console.log(err.stack);

                    res.status(400).send({
                        err: 'Android DB save Failed'
                    });
                    res.end();

                } else
                    console.log('Android Save complete');
                res.status(200);
                res.end();

            });
        } else {
            res.status(402).send({
                err: '이미 회원가입한 정보 '
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

            database.lasbery.findR_num(roomNum, (err, find) => {

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
            database.lasbery.findAll((err, find) => {

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

        }
    } else {
        res.status(400).send({
            err: 'Not Found Session Key'
        })
        res.end();
    }
}


exports.test = (req, res) => {

    if (req.session.key) {
        let database = req.app.get('database');
        let key = req.session.key;
        database.lasbery.update({}, {
            "$set": {
                "check": key
            }
        }, () => {
            database.lasbery.findByBool(true, key, (err, find) => {

                if (err) {
                    console.log(err);
                    res.status(400).send({
                        err: err
                    });

                    res.end();
                }
                if (find) {
                    console.log('Test Method');
                    res.status(200).send(JSON.stringify(find));
                    res.end();
                }
            })
        })



    } else {
        res.status(400).send({
            err: 'Not Found Session Key'
        })
        res.end();
    }

};