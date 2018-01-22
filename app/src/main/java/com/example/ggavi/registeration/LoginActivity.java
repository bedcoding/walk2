package com.example.ggavi.registeration;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    // 알림창을 나타내는 다이얼로그
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // (2)로그인 창에서 회원가입 버튼을 눌렀을 때 화면이 넘어가는 부분
        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        // (5)아이디 텍스트를 매칭시켜준다 (아이디를 입력받는 부분)
        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);


        // (5)로그인 버튼을 눌렀을 때 발생하는 이벤트 처리
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseLister = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            // jsonResponse 이놈은 해당 결과는 받아온다.
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            // success 이놈이 나올 경우 (로그인에 성공한 경우)
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인에 성공했습니다")
                                        .setPositiveButton("확인", null)  //확인 버튼을 눌러서 나오게 함
                                        .create();
                                dialog.show();

                                // 화면 전환 (로그인창 -> 메인창)
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                // (12)userID에 대한 정보를 보내줌
                                intent.putExtra("userID", userID);

                                LoginActivity.this.startActivity(intent);
                                finish(); //현재 액티비티 닫기
                            }


                            // 로그인에 실패한 경우
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("계정을 다시 확인하세요")
                                        .setNegativeButton("다시 시도", null)  //확인 버튼을 눌러서 나오게 함
                                        .create();
                                dialog.show();
                            }
                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                // 실제로 로그인을 보낼 수 있는 로그인 리퀘스트
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseLister);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        // (26)제작자 정보 보기 버튼
        TextView information = (TextView) findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Pop.class));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        // (5)현재 dialog 이놈이 켜져 있을 때는 함부로 종료되지 않게 한다 (큰 의미는 없음)
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}