const time = require('../time');
const date = require('date-utils');

exports.checkFire = (req, res) => {
    let spot = req.body.spot;
    let database = req.app.get('database');
    let arr = spot.split(",");
    let times = time.getTimeStamp();

    let users = new database.lasbery({

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