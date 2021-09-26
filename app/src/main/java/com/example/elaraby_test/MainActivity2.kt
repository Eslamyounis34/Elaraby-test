package com.example.elaraby_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    var composable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        tokenbutton.setOnClickListener {
            val notificationData = notificationedittext.text.toString()
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result.toString()

                val notification = Notification(notificationData, "Message From Elaraby")
                val notificationContent = NotificationContent(notification, token.toString())
                composable = RetroInstance.api.sendCustomNotification(notificationContent)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<NotificationResponse>() {
                        override fun onSuccess(t: NotificationResponse) {
                            Log.e("Testsuccess", t.toString())

                        }


                        override fun onError(e: Throwable) {
                            Log.e("TestError", e.message.toString())

                        }
                    })

            })

        }
    }
}