const logic = require('./logic');
const lasbery = require('../../Mongo/Lasbery/Schema');
const android = require('../../Mongo/Android/AndroidSchema');

exports.splash = (req, res) => {
    console.log('splash 로그인 시작');
    let token = req.body.token;
    android.checkSave(token).then(() => {
        console.log('정보 있음');
        req.session.key = token;
        res.status(200).end();
    }).catch((err) => {
        console.log(err);
        res.status(400).end();
    })
};

exports.signup = (req, res) => {
    console.log('회원가입');
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

exports.search = (req, res) => { // 사용자 전적 검색을 하는 로직
    console.log('검색 시작');
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


exports.test = (req, res) => { // 테스트를 위한 안드로이드 로직
    console.log('test시작');
    let key = req.session.key;
    let bool = req.body.bool || req.query.bool;
    android.findOne({ "token": key })
        .then((userData) => {
            userData.updateData(bool);
        })
        .then(lasbery.find({ bool })
            .then((data) => {
                if (data) res.status(200).end();
                else res.status(403).end();
            }));
};