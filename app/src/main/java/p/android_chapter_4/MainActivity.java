package p.android_chapter_4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {
    Button button1;
    Button longPressShowMenu;
    String[] provinces = new String[]{
            "aa", "bb", "cc"
    };
    boolean[] mutilChoice = {false, false, false};

    public Dialog mAlertDiaglog;
    private static int i = 1;
    NotificationManager notifyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        makeMessage();
        Event();
        longPressShowMenu=(Button)findViewById(R.id.LongPressShowMenu);
        registerForContextMenu(longPressShowMenu);
    }

    private void makeMessage() {
//        View view=getLayoutInflater().inflate(R.layout.custom_view,null);
//        Toast toast=new Toast(this);
//        toast.setView(view);
//        toast.show();

//        Toast toast = Toast.makeText(this, "who", Toast.LENGTH_LONG);
//        toast.setText("me");
//        toast.show();

//        Toast toast = Toast.makeText(this, "never dismiss Toast", Toast.LENGTH_LONG);  //不能用，仍然消失
//        toast.setGravity(Gravity.TOP,0,0);
//        try {
//            Field field = toast.getClass().getDeclaredField("mTN");
//            field.setAccessible(true);
//            Object obj = field.get(toast);
//            Method method = obj.getClass().getDeclaredMethod("show", null);
//            method.invoke(obj, null);
//        } catch (Exception e) {
//            Log.v("ref", e.getMessage());
//        }

//        final Toast toast = Toast.makeText(this, "never dismiss Toast", Toast.LENGTH_LONG);//能用
//        toast.show();
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                while (true) {
//                    toast.show();
//                }
//            }
//        },1500);


    }

    public void makenotify(View view) {

//        NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Notification notification = new Notification(R.drawable.pic1, "New message", System.currentTimeMillis());
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,FullscreenActivity.class), 0);
//        notification.setLatestEventInfo(this, "Title", "content", pendingIntent);
//        notifyManager.notify(i++, notification);


//        NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, FullscreenActivity.class), 0);
//        BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.pic2);
//        Notification notification = new Notification.Builder(this)
//                .setContentText("NEW")
//                .setContentTitle("TITLE")
//                .setSmallIcon(R.drawable.pic1)
//                .setLargeIcon(bd.getBitmap())
//                .setContentIntent(pendingIntent).build();
//        notifyManager.notify(i++, notification);

         notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.pic1, "New message", System.currentTimeMillis());

//        Intent intent=new Intent("brodcast");   //开启广播
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

//        Intent intent1=new Intent(this,myserver.class); //开启服务
//        PendingIntent pendingIntent1=PendingIntent.getService(this,1,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, FullscreenActivity.class), 0);
        notification.setLatestEventInfo(this, "Title", "content", pendingIntent);
        notification.flags=Notification.FLAG_ONGOING_EVENT;//设置为点击按钮不能清除
        RemoteViews remoteview=new RemoteViews(getPackageName(),R.layout.custom_view);
        remoteview.setTextViewText(R.id.custome_textview,"changed by notify");
        notification.contentView=remoteview;
        notifyManager.notify(i++, notification);

    }

    public void clearNotify(View view) {
        notifyManager.cancelAll();
    }

    private void Event() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAlertDiaglog = new AlertDialog.Builder(MainActivity.this).setTitle("wangxm")
//                        .setMessage("Message")        //Message和item内容只能显示一个，Message优先级高
//                        .setItems(provinces, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(getApplicationContext(), "you select " + provinces[which], Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setSingleChoiceItems(provinces,-1,new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                flag[0] =which;
//                            }
//                        })
                        .setMultiChoiceItems(provinces, mutilChoice, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                mutilChoice[which] = isChecked;
                                Toast.makeText(getApplicationContext(), "Which is " + which + " and isChecked is" + isChecked, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("1", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "you click 1" + "and which is" + which, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("点击关闭窗口", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(getApplicationContext(), "you click 2" + "and which is" + which, Toast.LENGTH_SHORT).show();

                                try {   //点击被关闭
                                    Field field = mAlertDiaglog.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(mAlertDiaglog, true);
                                } catch (Exception e) {
                                    Log.v("Refection Error", e.getMessage());
                                }

                            }
                        })
                        .setPositiveButton("3", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(getApplicationContext(), "you click 3" + "and which is" + which, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), "you click 3 " + "and you selected " + provinces[flag[0]], Toast.LENGTH_SHORT).show();
                                int i = 0;
                                for (String province : provinces) {
                                    String logInfo = mutilChoice[i++] ? "mutilCheckedInfo checked" : "mutilCheckedInfo is unchecked";
                                    Log.i(province, logInfo);
                                }
                            }
                        }).create();

                Window window = mAlertDiaglog.getWindow();
                window.setGravity(Gravity.BOTTOM | Gravity.END);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.alpha = 0.1f;
                window.setAttributes(layoutParams);

                mAlertDiaglog.show();


                try {   //防止被关闭
                    Field field = mAlertDiaglog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    field.set(mAlertDiaglog, false);
                } catch (Exception e) {
                    Log.v("Refection Error", e.getMessage());
                }


            }
        });

    }

    public void progressBar(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Title");
        progressDialog.setMessage("try to growing...");
        progressDialog.setMax(100);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
//      progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//转圈
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(10);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "decrease", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.setProgress(10);
            }
        });
        progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
            }
        });
        progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "increase", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.setProgress(80);
            }
        });
        progressDialog.show();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(1,1,1,"ttt");
        menu.add(1,2,2,"xxx");
        menu.add(1,3,3,"jjj");
        menu.add(1,4,4,"mmm");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1,1,1,"aaa");
        menu.add(1,2,2,"bbb");
        menu.add(1,3,3,"ccc");
        menu.add(1,4,4,"ddd");
    }


}
