let router = require('express').Router();
let logic = require('./user');

router.route('/Ras/Send').post(logic.checkFire);
router.route('/auth/signin').post(logic.signin);
router.route('/auth/signup').post(logic.signup);

module.exports = router;