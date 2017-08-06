let router = require('express').Router();
let logic = require('./logic');

router.route('/Ras/Send').post(logic.checkFire);

module.exports = router;