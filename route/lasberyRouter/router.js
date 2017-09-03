let router = require('express').Router();
let logic = require('./logic');

router.route('/Ras/Send').get(logic.checkFire);

module.exports = router;