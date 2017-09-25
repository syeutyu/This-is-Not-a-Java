let router = require('express').Router();
let logic = require('./aController');
let middle = require('../../middle/sessionCheck');
let fcm = require('../fcm');

router.route('/').get((req, res) => {
    console.log('test');
});
router.route('/auth/signin').post(logic.splash);
router.route('/auth/signup').post(logic.signup);
router.route('/auth/test').get(middle, logic.test).post(middle, logic.test);
router.route('/auth/search').post(middle, logic.search);
module.exports = router;