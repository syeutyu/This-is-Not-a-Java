const date = require('date-utils');

exports.checkFire = (req,res) =>{
  let Rnum = req.body.name;
  let database = req.app.get('database');
  let newDate = new Date();
  let time = newDate.toFormat('YYYY-MM-DD HH24:MI:SS');
  console.log(Rnum);
};
