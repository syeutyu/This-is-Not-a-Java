const time = require('../time');
const date = require('date-utils');

exports.checkFire = (req, res) => {

    console.log('화재발생');
    let spot = req.query.spot;
    console.log(spot);
    let database = req.app.get('database');
    let times = time.getTimeStamp();

    let users = new database.lasbery({
        "spot": spot,
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