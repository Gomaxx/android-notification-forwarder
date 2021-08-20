package cc.eoma.nf;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText ip = findViewById(R.id.ip);
        EditText port = findViewById(R.id.port);
        EditText pkg = findViewById(R.id.packages);

        MyApplication myApplication = (MyApplication) getApplication();


        if(!isEnabled()){
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }


        Button connection = findViewById(R.id.connection);
        connection.setOnClickListener(view -> {
            String text = ip.getText().toString().trim();
            String ttt = port.getText().toString();
            String packages = pkg.getText().toString();
            if ("".equals(text) || "".equals(ttt) || "".equals(pkg)) {
                Toast.makeText(MainActivity.this, "请输入IP:PORT", Toast.LENGTH_SHORT).show();
                return;
            }
            myApplication.setPackages(Arrays.asList(packages.split(",")));
            myApplication.setIp(text);
            myApplication.setPort(ttt);
        });
    }

    private Boolean isEnabled() {
        return NotificationManagerCompat.getEnabledListenerPackages(this).contains(getPackageName());
    }
}