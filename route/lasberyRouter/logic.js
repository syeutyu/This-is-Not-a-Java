const time = require('../time');
const fcm = require('../fcm');
const lasbery = require('../../Mongo/Lasbery/Schema');
const android = require('../../Mongo/Android/AndroidSchema');

exports.checkFire = (req, res) => {
    console.log('화재발생');
    let model = req.query.model;
    let check = req.query.check;
    let times = time.getTimeStamp();

    if (checkTest(model, check)) {
        lasbery.saveFire(model, check, times);
        android.find({ "code": model }).then((data) => {
            (0 < data.length) ? fcmSend(data): throwError();
            res.end();
        }).catch((err) => {
            console.log('화재 발생 Error');
            console.log(err);
            res.status(500);
            res.end();
        });
    } else {
        console.log('테스트를 위해서는 android의 테스트를 활성화해주세요');
        res.end();
    }
};

function checkTest(code, bool) {
    if (bool == 'false') return true;
    else android.findOne({ "code": code, "switch": bool })
        .then((data) => {
            if (data) return false;
            else return true;
        });
}

function fcmSend(data) {
    console.log(data.length + ' 명의 사람들에게 메세지 전송');
    for (let i in data) {
        fcm.fcmSend(data[i]);
    }
}

function throwError() {
    throw '해당하는 유저 정보 찾지 못함';
}

exports.setting = (req, res) => {
    let model = req.query.model;
    console.log('모듈 등록 : ' + model);
    let data = new lasbery({
        model
    });
    data.save((err) => {
        if (err) {
            console.log(err);
            res.status(403);
            res.end();
        } else {
            res.status(200);
            res.end();
        }
    });
};