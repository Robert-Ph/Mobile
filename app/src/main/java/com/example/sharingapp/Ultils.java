package com.example.sharingapp;

import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Ultils extends AppCompatActivity {
//    public <T,U> void intent_toastNotion(Button btn, String notionText){
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LayoutInflater inflater = getLayoutInflater() ;
//                View layout = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) findViewById(R.id.custom_toast_container));
//
//                TextView text = layout.findViewById(R.id.text_notion);
//                text.setText(notionText);
//
//                Toast toast = new Toast(getApplicationContext());
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toast.setDuration(Toast.LENGTH_SHORT);
//                toast.setView(layout);
//                toast.show();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(tInstance, uClass);
//                        startActivity(intent);
//                    }
//                }, 2000 + 1000);
//            }
//        });
//    }
}
