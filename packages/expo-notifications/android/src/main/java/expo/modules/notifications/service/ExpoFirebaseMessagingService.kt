package expo.modules.notifications.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import expo.modules.notifications.service.interfaces.FirebaseMessagingDelegate
import com.braze.push.BrazeFirebaseMessagingService

open class ExpoFirebaseMessagingService : FirebaseMessagingService() {
  protected open val firebaseMessagingDelegate: FirebaseMessagingDelegate by lazy {
    expo.modules.notifications.service.delegates.FirebaseMessagingDelegate(this)
  }
  override fun onMessageReceived(remoteMessage: RemoteMessage) {
    if (BrazeFirebaseMessagingService.handleBrazeRemoteMessage(this, remoteMessage)) {
        // Questo Remote Message ha avuto origine da Braze ed è stata visualizzata una notifica push.
        // Non è necessaria alcuna ulteriore azione.
    } else {
        firebaseMessagingDelegate.onMessageReceived(remoteMessage)
        // Questo Remote Message non ha avuto origine da Braze.
        // Nessuna azione è stata intrapresa e è possibile passare in modo sicuro questo Remote Message ad altri gestori.
    }
  }
  //override fun onMessageReceived(remoteMessage: RemoteMessage) = firebaseMessagingDelegate.onMessageReceived(remoteMessage)
  override fun onNewToken(token: String) = firebaseMessagingDelegate.onNewToken(token)
  override fun onDeletedMessages() = firebaseMessagingDelegate.onDeletedMessages()
}
