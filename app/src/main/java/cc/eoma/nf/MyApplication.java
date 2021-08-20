package cc.eoma.nf;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import android.widget.Toast;

import java.util.List;

public class MyApplication extends Application {

    private String ip;
    private String port;
    private List<String> packages;

    @Override
    public void onCreate() {
        super.onCreate();
        this.ensureMyNotificationListenerServiceRunning();
    }

    //确认 MyNotificationListenerService 是否开启
    private void ensureMyNotificationListenerServiceRunning() {
        ComponentName componentName = new ComponentName(this, MyNotificationListenerService.class);
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(Integer.MAX_VALUE);
        if (services == null || services.isEmpty()) {
            this.toggleNotificationListenerService();
            return;
        }

        for (ActivityManager.RunningServiceInfo service : services) {
            if (service.service.equals(componentName)) {
                if (service.pid == Process.myPid()) {
                    return;
                }
            }
        }
        toggleNotificationListenerService();
    }

    //重新开启 MyNotificationListenerService
    private void toggleNotificationListenerService() {
        ComponentName componentName = new ComponentName(this, MyNotificationListenerService.class);
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }




    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }
}
