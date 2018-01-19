package com.example.ggavi.registeration;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    // values 폴더에 추가한 arrays.xml 이놈을 담기 위해 선언
    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userID;
    private String userPassword;
    private String userGender;
    private String userMajor;
    private String userEmail;
    private AlertDialog dialog;  //알림창을 보여줌
    private boolean validate = false;  //사용할 수 있는 회원 아이디인지 체크




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // spinner 얘는 현재 디자인에 있는 majorSpinner 이놈을 그대로 가져올 수 있도록 하고
        // adapter 얘는 values 폴더에 추가한 arrays.xml 얘들을 얻어와서 넣어줌
        // 마지막으로 스피너에 위에서 얻어온 adapter 이거를 추가해주면 정상적으로 등록이 됨
        spinner = (Spinner) findViewById(R.id.majorSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // 디자인에서 만들었던 녀석들 매칭
        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);

        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        int genderGroupID = genderGroup.getCheckedRadioButtonId(); //현재 뭐가 선택되어 있는지 확인 (젠더 그룹에 선택된 게 남자인지 여자인지)
        userGender = ((RadioButton) findViewById(genderGroupID)).getText().toString(); //현재 선택된 버튼을 매칭 (남자 or 여자)

        // 라디오 버튼이 클릭됐을 때에 대한 이벤트 처리
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radiogroup, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i);
                userGender = genderButton.getText().toString();  //현재 선택된 라디오 버튼을 찾은 뒤 유저젠더의 값을 바꿔줌
            }
        });


        // 회원 중복 체크 버튼
        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();

                // 현재 만약에 validate 체크가 이뤄져있다면 바로 함수 종료
                if(validate)
                {
                    return;
                }

                // 현재 체크가 안되어 있지만 ID 값에 아무런 값이 없다면 오류 발생 (ID는 빈 공간일 수 없으므로)
                if(userID.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();  //create 이거로 다이얼로그에 다시 넣어줌
                    dialog.show();
                    return;
                }

                // 정상적으로 ID 값을 입력했을 경우 중복체크 시작
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // 해당 웹사이트에 접속한 뒤 특정한 response(응답)을 다시 받을 수 있도록 한다
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            // 만약 사용할 수 있는 아이디라면
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();  //create 이걸로 다이얼로그에 다시 넣어줌
                                dialog.show();

                                idText.setEnabled(false); // ID 값을 더 이상 바꿀 수 없도록 고정
                                validate = true; // 체크 완료
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }

                            // 중복체크 실패 (사용할 수 없는 아이디)
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();  //create 이걸로 다이얼로그에 다시 넣어줌
                                dialog.show();
                            }
                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                // 실질적으로 접속할 수 있도록 생성자를 통해 객체를 만든다. (유저 ID, responseListener)
                // ValidateRequest.java라는 파일을 만들어야 한다.
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });


        // 회원가입 버튼
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userMajor = spinner.getSelectedItem().toString();
                String userEmail = emailText.getText().toString();


                // 현재 중복체크가 되어 있지 않을 경우
                if(!validate)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복체크를 해주세요")
                            .setNegativeButton("확인", null)
                            .create();  //create 이걸로 다이얼로그에 다시 넣어줌
                    dialog.show();
                    return;
                }


                // 하나라도 빈 공간이 있을 경우
                if(userID.equals("") || userPassword.equals("") || userMajor.equals("") || userEmail.equals("") || userGender.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();  //create 이걸로 다이얼로그에 다시 넣어줌
                    dialog.show();
                    return;
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        // 해당 웹사이트에 접속한 뒤 특정한 response(응답)을 다시 받을 수 있도록 한다
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            // 만약 사용할 수 있는 아이디라면
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();  //create 이걸로 다이얼로그에 다시 넣어줌
                                dialog.show();
                                finish();           //회원가입 창을 닫는다.
                            }

                            // 중복체크 실패 (사용할 수 없는 아이디)
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();  //create 이걸로 다이얼로그에 다시 넣어줌
                                dialog.show();
                            }
                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                // 실질적으로 접속할 수 있도록 생성자를 통해 객체를 만든다.
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender, userMajor, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }

    // 회원 등록이 이뤄진 이후 회원등록 창이 꺼지게 되면 실행되는 부분
    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
