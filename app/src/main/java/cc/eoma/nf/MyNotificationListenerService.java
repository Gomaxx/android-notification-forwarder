package cc.eoma.nf;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import cc.eoma.nf.sender.SocketSender;

public class MyNotificationListenerService extends NotificationListenerService {
    private MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = (MyApplication) getApplication();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);

        if (!application.getPackages().contains(notificationPkg)) {
            return;
        }
        send(application.getIp(), Integer.valueOf(application.getPort()), notificationTitle + "###G:T###" + notificationText);

    }

    private void send(String ip, Integer port, String message) {
        new Thread(() -> {
            SocketSender.send(ip, port, message);
        }).start();
    }
}
