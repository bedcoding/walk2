package com.example.ggavi.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


// 카페24와 서버 연동 : 수강신청할 때 내 ID의 스케쥴DB에 넣기
public class AddRequest extends StringRequest {

    final static private String URL = "http://ggavi2000.cafe24.com/CourseAdd.php";
    private Map<String, String> parameters;

    public AddRequest(String userID, String courseID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);
        // 특정한 사람이 특정한 강의를 선택하면 DB에 저장
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
