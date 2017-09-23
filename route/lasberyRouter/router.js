let router = require('express').Router();
let logic = require('./logic');

router.route('/ras/send').get(logic.checkFire);
router.route('/ras/set').get(logic.setting);

module.exports = router;