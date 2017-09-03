exports.signId = (id, database) => {
    return new Promise((resolve, reject) => {

        database.android.findById(id, (err, find) => {
            if (err) {
                console.log(err);
                reject(err);
            }
            if (Object.keys(find).length != 0) {
                resolve(find);
            } else {
                reject('Not Found Id');
            }
        });
    });
};

exports.signPw = (pw, database) => {
    console.log('signpw');
    return new Promise((resolve, reject) => {
        database.android.findByPw(pw, (err, finds) => {
            if (err) {
                reject(err);
            }
            if (Object.keys(finds).length != 0) {
                console.log('et');
                resolve(finds);
            } else {
                throw 'Not Found Password';
            }
        });
    });
};