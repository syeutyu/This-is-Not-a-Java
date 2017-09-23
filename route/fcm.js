const FCM = require('fcm-node');
let serverKey = require('../serverKey.json');

exports.fcmSend = (data) => {
    let fcm = new FCM(serverKey.key);
    let message = {
        to: data.token,
        notification: {
            title: '화재가 발생했습니다',
            body: data.place
        }
    };

    fcm.send(message, (err, response) => {
        if (err) {
            console.log('error = ' + err);
        } else {
            console.log(response);
        }
    });
}