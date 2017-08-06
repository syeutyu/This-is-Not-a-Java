let router = require('express').Router();
let logic = require('./user');

router.route('/auth/signin').post(logic.signin);
router.route('/auth/signup').post(logic.signup);
router.route('/auth/test').post(logic.test);
router.route('/auth/search').post(logic.search);

module.exports = router;