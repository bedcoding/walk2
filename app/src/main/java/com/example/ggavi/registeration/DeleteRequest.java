package com.example.ggavi.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


// 카페24와 서버 연동 : 삭제할 때 내 카페24 DB와 연동
// AddRequest.java 코드를 그대로 복붙했다. (URL만 변경)
// 데이터 삭제는 URL에 있는 Delete php파일이 알아서 한다.

public class DeleteRequest extends StringRequest {

    final static private String URL = "http://ggavi2000.cafe24.com/ScheduleDelete.php";
    private Map<String, String> parameters;

    public DeleteRequest(String userID, String courseID, Response.Listener<String> listener) {
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
