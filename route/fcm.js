let FCM = require('fcm-node');

exports.test = (req, res) => {
    let serverKey = req.query.serverKey;
    let clientId = req.query.clientId;
    let title = req.query.title;
    let body = req.query.body;

    sendFcm(serverKey, clientId, title, body, (bool) => {
        if (bool) {
            res.status(200).send({
                'Message': 'Send FCM'
            });
            res.end();
        } else {
            res.status(400).send({
                'Message': 'Failed FCM'
            });
            res.end();
        }
    });
}

function sendFcm(serverKey, clientId, title, body) {
    let fcm = new FCM(serverKey);
    let message = {
        to: clientId,
        notification: {
            title: title,
            body: body
        }
    };

    fcm.send(message, (err, response) => {
        if (err) {
            console.log('error = ' + err);
            return false;
        } else {
            console.log(response);
            return true;
        }
    });
}