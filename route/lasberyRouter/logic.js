const time = require('../time');
const date = require('date-utils');

exports.checkFire = (req, res) => {

    console.log('화재발생');
    let R_num = req.query.R_num;
    let spot = req.query.spot;
    let database = req.app.get('database');
    let arr = spot.split(",");
    let times = time.getTimeStamp();

    let users = new database.lasbery({
        "R_num": R_num,
        "spot": arr[0],
        "time": times,
        "check": arr[1]

    });

    users.save((err) => {
        if (err) {
            console.log(err);
        }
    });
    res.status(200).send('Data Send');
    res.end();

};