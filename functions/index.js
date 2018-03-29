const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/notificationRequests').onWrite((event) => {
    const recipient = event.params.recipientId;
    const message = event.params.message;
    const recipientDeviceToken = admin.database().ref(`/users/${recipient}/notificationToken`).once('value');
    console.log(recipientDeviceToken);

    return Promise.all(recipientDeviceToken).then((results) => {
      const tokenSnapshot = results;
      if (tokenSnapshot === null) {
        console.log("tokenSnapshot has not value");
      }

      const payload = {
        notification: {
          title: 'new message',
          body: message,
        }
      }
      const token = tokenSnapshot.val();
      return admin.messaging().sendToDevice(token, payload);
    }).then((response) => {
      const notificationsToRemove = [];
      response.results.forEach((result, index) => {
        const error = result.error;
        if (error) {
          console.error('failure sending notification', error);
        }
      });
      return Promise.all(notificationsToRemove);
    });
});
