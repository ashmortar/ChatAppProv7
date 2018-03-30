const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/notificationRequests/{pushid}').onWrite((event) => {
    const recipient = event.data.child('recipientId').val();
    const message = event.data.child('message').val();
    console.error(message);
    const getDeviceTokenPromise = admin.database().ref(`users/${recipient}/notificationToken`).once('value');

    return Promise.all([getDeviceTokenPromise]).then((results) => {
      const tokenSnapshot = results[0];
      console.error(tokenSnapshot.val());
      const payload = {
        notification: {
          title: 'new message',
          body: message
        }
      }
      const token = tokenSnapshot.val();
      console.error(token);
      return admin.messaging().sendToDevice(token, payload);
    })
});
