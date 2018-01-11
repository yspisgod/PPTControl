package com.ysp.asus.pptcontrolv_2;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    WebSocketClient webSocketClient;
    public float x1;
    public float x2;
    public int page=0;
    public TextView text;
    AlertDialog mAlertDialog=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            webSocketClient=new WebSocketClient(new URI("ws://主机名:主机端口号")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {

                }

                @Override
                public void onMessage(String message) {

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {

                }

                @Override
                public void onError(Exception ex) {

                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        webSocketClient.connect();

         text=(TextView) findViewById(R.id.text);
        text.setText(""+page);


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setPage();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            x1=event.getRawX();
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            x2=event.getRawX();
            Log.i("移动=================",(int)x1+"========="+(int)x2+"==========="+(x1-x2));
            if(x1 - x2 > 50){
                if(page>0) {
                    page -= 1;
                    text.setText("" + page);
                    webSocketClient.send("ppt_back");
//                Log.i("移动","往左边"+x1+"_"+x2);
                }
            }else if(x2 - x1 > 50){
                page+=1;
                text.setText(""+page);
                webSocketClient.send("ppt_move");
//                Log.i("移动","往右边"+x1+"_"+x2);
            }

        }

        return super.onTouchEvent(event);
    }

    public void setPage(){

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme));
        LayoutInflater flater = LayoutInflater.from(getApplicationContext());
        View view = flater.inflate(R.layout.style_dailog, null);
        final EditText editText=(EditText)view.findViewById(R.id.set_page);
        Button button=(Button)view.findViewById(R.id.set);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"输入不合法",Toast.LENGTH_LONG).show();
                }else{
                    page=Integer.parseInt(editText.getText().toString());
                    text.setText(""+page);
                    mAlertDialog.dismiss();
                }
            }
        });
        builder.setView(view);
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }
}
