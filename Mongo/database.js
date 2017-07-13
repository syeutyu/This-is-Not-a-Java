const mongoose = require('mongoose');
mongoose.Promise = global.Promise;

module.exports = () => {
  function connect() {
    mongoose.connect('localhost:27017', function(err) {
      if (err) {
        console.error('mongodb connection error', err);
      }
      console.log('mongodb connected');
    });
  }

  connect();
  mongoose.connection.on('disconnected', connect);
  require('./Schema.js');
  require('./AndroidSchema');
};