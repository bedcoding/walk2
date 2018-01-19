package com.example.ggavi.registeration;

//import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

// NoticeListAdapter.java를 가져와서 CourseListAdapter를 만들고,
// (18) 그걸 또 가져와서 수정하여 이 클래스를 만들었다.

public class StatisticsCourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;      // Course가 들어가는 리스트를 만들어줌
    private Fragment parent;              // 12강 추가
    private String userID = MainActivity.userID;  // MainActivity에 있는 public 형태의 userID를 가져와서 해당 사용자의 아이디를 저장




    public StatisticsCourseListAdapter(Context context, List<Course> courseList, Fragment parent)  //자신을 불러낸 부모 Fragment를 담을 수 있도록 한다.
    {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) //i는 position
    {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) //i는 position
    {
        return i;
    }

    @Override  //i는 position, viewGroupParent는 처음에 parent로 자동완성이 되어서 밑에 있는 parent와 중복되서 에러 생겼음 (그래서 고침)
    public View getView(final int i, View convertView, ViewGroup viewGroupParent)
    {
        // 하나의 View로 만들어 줄 수 있도록 한다.
        View v = View.inflate(context, R.layout.statistics, null);  // statistics.xml


        // statistics.xml이라는 레이아웃에 있는 모든 원소가 하나의 변수로써 자리잡게 되었다.
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseRate = (TextView) v.findViewById(R.id.courseRate);




        // courseList에서 특정한 원소를 가져올때
        // 그 원소가 "제한 없음"이라는 값을 가지거나
        // 혹은 Grade 값이 현재 비어있는 경우 "모든 학년"이라고 보여진다.
        if(courseList.get(i).getCourseGrade().equals("제한 없음") || courseList.get(i).getCourseGrade().equals(""))
        {
            courseGrade.setText("모든 사람");
        }

        else
        {
            courseGrade.setText(courseList.get(i).getCourseGrade() + "학년");
        }

        courseTitle.setText(courseList.get(i).getCourseTitle());
        courseDivide.setText(courseList.get(i).getCourseDivide() + "분반");


        // 만약 현재 courseList에서 가져온 현재 강의정보에 제한인원이 0이라면
        if(courseList.get(i).getCoursePersonnel() == 0)
        {
            coursePersonnel.setText("인원제한 없음");
            courseRate.setText("");  //인원 제한이 없으면 경쟁률도 0
        }

        else
        {
            // 인원 제한이 있는 경우 (현재 강의를 등록한 라이벌들 / 강의를 들을 수 있는 총 인원)
            coursePersonnel.setText("신청인원: " + courseList.get(i).getCourseRival() + "/" + courseList.get(i).getCoursePersonnel());

            // 경쟁률 계산
            int rate = ((int) (((double) courseList.get(i).getCourseRival() * 100 / courseList.get(i).getCoursePersonnel()) + 0.5));  // (+ 0.5는 반올림)
            courseRate.setText("경쟁률: " + rate + "%");

            // 경쟁률이 아주 낮다면 초록색(res폴더 -> values폴더 -> colors.xml에 있는 colorSafe 색깔)
            if(rate < 20)
            {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorSafe));
            }

            // 경쟁률이 낮다면
            else if(rate < 50)
            {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorPrimary));
            }

            // 경쟁률이 1:1 이하
            else if(rate < 100)
            {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorDanger));
            }

            // 경쟁률이 높다면
            else if(rate < 150)
            {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorWarning));
            }

            // 경쟁률이 겁나 높다면
            else
            {
                courseRate.setTextColor(parent.getResources().getColor(R.color.colorRed));
            }
        }

        v.setTag(courseList.get(i).getCourseID());


        // (19) 삭제버튼 이벤트 추가
        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CourseListAdapter.java에서 가져와서 복붙 (19)
                // 정상적으로 ID 값을 입력했을 경우 중복체크 시작
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // 해당 웹사이트에 접속한 뒤 특정한 response(응답)을 다시 받을 수 있도록 한다
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            // 만약 삭제할 수 있다면
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());  //자신을 불러낸 CourseFragment에서 알림창을 띄워줌
                                AlertDialog dialog = builder.setMessage("삭제되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();  //create 이걸로 다이얼로그에 다시 넣어줌
                                dialog.show();

                                // 삭제한 만큼 학점도 빼준다.
                                StatisticsFragment.totalCredit -= courseList.get(i).getCourseCredit();
                                StatisticsFragment.credit.setText(StatisticsFragment.totalCredit + "학점");
                                courseList.remove(i);    // 리스트에서 삭제
                                notifyDataSetChanged();  // 바뀐걸 적용
                            }

                            // 삭제 실패
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                AlertDialog dialog = builder.setMessage("삭제를 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create();  //create 이걸로 다이얼로그에 다시 넣어줌
                                dialog.show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 실질적으로 삭제할 수 있도록 생성자를 통해 객체를 만든다. (유저 ID, responseListener)
                // 그리고 어떤 회원이 어떤 강의를 삭제한다는 데이터는 DB에 넣어야 한다.
                // DeleteRequest.java를 만들어줘야 한다.
                DeleteRequest deleteRequest = new DeleteRequest(userID, courseList.get(i).getCourseID() + "", responseListener);  // + ""를 붙이면 문자열 형태로 바꿈
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(deleteRequest);
            }
        });


        // 버튼 이벤트까지 마친 뒤 정상적으로 return 한다.
        return v;
    }
}

