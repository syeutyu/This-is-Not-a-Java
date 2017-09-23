const logic = require('./logic');
const lasbery = require('../../Mongo/Lasbery/Schema');
const android = require('../../Mongo/Android/AndroidSchema');

exports.splash = (req, res) => {
    let token = req.body.token;
    android.checkSave(token).then(() => {
        console.log('정보 있음');
        req.session.key = token;
        res.status(200).send({ "cookie ": req.session.key }).end();
    }).catch((err) => {
        console.log(err);
        res.status(400).end();
    })
};

exports.signup = (req, res) => {
    let token = req.body.token;
    android.findOne({ token }).then((findData) => {
        if (!findData) {
            return android.saveUser(req.body);
        } else {
            throw '이미 회원 가입한 유저 정보';
        }
    }).then((findData) => {
        req.session.key = req.body.token;
        res.status(201).end();

    }).catch((err) => {
        console.log('회원가입 Error');
        console.log(err);
        res.status(403).end();
    });
};

exports.search = (req, res) => { // 
    let userToken = req.session.key;
    logic.search(userToken)
        .then((data) => {
            res.status(200).json(data).end();
        }).catch((err) => {
            console.log('검색중 Error');
            console.log(err);
            res.status(400).end();
        });
}


exports.test = (req, res) => {
    let key = req.session.key;
    android.findOne(key)
        .then((userData) => {
            userData.updateData(true);
        })
        .then(lasbery.find({ bool })
            .then((data) => {
                if (data) res.status(200).json(data).end();
                else res.status(403).end();
            }));
};