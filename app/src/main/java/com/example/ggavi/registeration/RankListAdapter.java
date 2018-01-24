package com.example.ggavi.registeration;

//import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

// (20) 하나의 코스 순위 정보를 불러올 수 있도록 하는 기능
// ~ Adapter.java 파일을 가져와서 수정하여 이 클래스를 만들었다.

public class RankListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;      // Course가 들어가는 리스트를 만들어줌
    private Fragment parent;              // 12강 추가




    public RankListAdapter(Context context, List<Course> courseList, Fragment parent)  //자신을 불러낸 부모 Fragment를 담을 수 있도록 한다.
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

    @Override   // 각각 만들었던 디자인에 있는 내용들을 매칭시켜준다.
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        // 가장 먼저 레이아웃에 rank를 매칭시켜준다.
        View v = View.inflate(context, R.layout.rank, null);  // rank.xml

        // rank.xml이라는 레이아웃에 있는 모든 원소가 하나의 변수로써 자리잡게 되었다.
        TextView rankTextView = (TextView) v.findViewById(R.id.rankTextView);
        //TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        //TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        //TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        //TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        //TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        //TextView courseTime = (TextView) v.findViewById(R.id.courseTime);

        rankTextView.setText((i+1) + "위");   // 몇 등인지 보여줌

        // 1위가 아닐 경우 모두 같은 배경색
        if(i != 0)
        {
            rankTextView.setBackgroundColor(parent.getResources().getColor(R.color.colorPrimaryDark));
        }

        courseTitle.setText(courseList.get(i).getCourseTitle());

/*
        // courseList에서 특정한 원소를 가져올때
        // 그 원소가 "제한 없음"이라는 값을 가지거나
        // 혹은 Grade 값이 현재 비어있는 경우 "모든 학년"이라고 보여진다.
        if(courseList.get(i).getCourseGrade().equals("제한 없음") || courseList.get(i).getCourseGrade().equals(""))
        {
            courseGrade.setText("모든 학년");
        }

        else
        {
            courseGrade.setText(courseList.get(i).getCourseGrade() + "학년");
        }

        courseCredit.setText(courseList.get(i).getCourseCredit() + "학점");
        courseDivide.setText(courseList.get(i).getCourseDivide() + "분반");


        // 만약 현재 courseList에서 가져온 현재 강의정보에 제한인원이 0이라면
        if(courseList.get(i).getCoursePersonnel() == 0)
        {
            coursePersonnel.setText("인원제한 없음");
        }

        else
        {
            // 인원 제한이 있는 경우 (현재 강의를 등록한 라이벌들 / 강의를 들을 수 있는 총 인원)
            coursePersonnel.setText("제한 인원: " + courseList.get(i).getCoursePersonnel() + " 명");
        }



        // 교수 이름이 비어있는 경우
        if(courseList.get(i).getCourseProfessor().equals(""))
        {
            courseProfessor.setText("개인 연구");
        }

        // 교수 이름이 이미 있는 경우
        else
        {
            courseProfessor.setText(courseList.get(i).getCourseProfessor() + "교수님");
        }

        // 이렇게 하면 정상적으로 모든 값이 출력되서 디자인에 들어감
        courseTime.setText(courseList.get(i).getCourseTime() + "");

        */

        v.setTag(courseList.get(i).getCourseID());
        return v;
    }
}

