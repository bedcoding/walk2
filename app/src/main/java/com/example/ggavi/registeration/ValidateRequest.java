// 카페24와 서버 연동 : 현재 회원가입이 가능한지 확인하는 부분 (회원 아이디 체크)

package com.example.ggavi.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


// 카페24와 서버 연동 : 회원가입
public class ValidateRequest extends StringRequest {

    final static private String URL = "http://ggavi2000.cafe24.com/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}