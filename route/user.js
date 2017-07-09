const date = require('date-utils');
const Model = require('../Mongo/Schema');

exports.checkFire = (req,res) =>{
  let spot = req.body.spot;
  let Check = req.body.Check;
  
  let newDate = new Date();
  let time = newDate.toFormat('YYYY-MM-DD HH24:MI:SS');

  let users = new Model({
    "spot" : spot,
    "time" : time,
    "check" : Check
  });
  users.save((err)=>{
    if(err){
      console.log(err);
    }
  });
  console.log(users)
};
