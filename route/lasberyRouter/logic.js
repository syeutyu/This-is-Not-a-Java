const time = require('../time');
const date = require('date-utils');

exports.checkFire = (req, res) => {

    console.log('화재발생');
    let spot = req.query.spot;
    let check = spot.split(',');
    console.log(check);
    let database = req.app.get('database');
    let times = time.getTimeStamp();

    let users = new database.lasbery({
        "spot": check[0],
        "check": check[1],
        "time": times,
    });

    users.save((err) => {
        if (err) {
            console.log(err);
        }
    });
    res.status(200).send('Data Send');
    res.end();

};