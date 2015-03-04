package p.android_chapter_4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {
    Button button1;
    String[] provinces = new String[]{
            "aa", "bb", "cc"
    };
    boolean[] mutilChoice = {false, false, false};

   public Dialog mAlertDiaglog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);

        Event();
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

                                try {   //运行被关闭
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


}
