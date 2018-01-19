package com.example.ggavi.registeration;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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

// Fragment 이놈은 기본적으로 특정한 화면 안에 있는
// 세부적인 화면을 만들 때 많이 사용하는 레이아웃이다.

public class StatisticsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    // (18) 추가
    private ListView courseListView;
    private StatisticsCourseListAdapter adapter;    // 어댑터 만들어주고
    private List<Course> courseList;                // 강의 클래스가 인스턴스로 들어게 해주고

    // (19) 위에 있던거 지우고 다시 만듬 (총 학점수 : 현재 해당 학생 신청)
    public static int totalCredit = 0;
    public static TextView credit;


    // (20) 순위를 보여주기 위한 변수
    private ArrayAdapter rankAdapter;
    private Spinner rankSpinner;

    // (21) 순위를 보여주기 위한 변수
    private ListView rankListView;
    private RankListAdapter rankListAdapter;
    private List<Course> rankList;


    // (18) 실질적으로 해당 액티비티를 불러왔을 때 실행되는 부분
    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        // 각각 생성자처럼 초기화
        courseListView = (ListView) getView().findViewById(R.id.courseListView);   // 리스트뷰 초기화
        courseList = new ArrayList<Course>();   // Course가 들어가는 어레이리스트
        adapter = new StatisticsCourseListAdapter(getContext().getApplicationContext(), courseList, this);
        courseListView.setAdapter(adapter);
        new BackgroundTask().execute();  // DB와 소통되는 부분

        // (19) 생성자 추가
        totalCredit = 0;
        credit = (TextView) getView().findViewById(R.id.totalCredit);

        // (20) 인기 순위 리스트 만들때 추가한 생성자
        rankSpinner = (Spinner) getView().findViewById(R.id.rankSpinner);
        rankAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.rank, R.layout.spinner_item);  // res -> spinner_item.xml 파일과 연결
        rankSpinner.setAdapter(rankAdapter);

        rankSpinner.setPopupBackgroundResource(R.color.colorPrimary);   // 해당 스피너를 선택했을 때 나오는 목록 배경색깔


        // (21)
        rankListView = (ListView) getView().findViewById(R.id.rankListView);
        rankList = new ArrayList<Course>();
        rankListAdapter = new RankListAdapter(getContext().getApplicationContext(), rankList, this);  // 생성자 초기화
        rankListView.setAdapter(rankListAdapter);

        // 계속 랭킹 실행시키면 터지길래 구글링해봄.
        rankAdapter.notifyDataSetChanged();


        rankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)     // int position, long id
            {
                // (22)만약 현재 선택한 스피너의 값이 "전체에서"일 경우 (전체에서 버튼을 누른 경우)
                if(rankSpinner.getSelectedItem().equals("전체에서"))
                {
                    rankList.clear();
                    new ByEntire().execute();
                }

                // 만약 현재 선택한 스피너의 값이 "전체에서"일 경우
                else if(rankSpinner.getSelectedItem().equals("남자 선호도"))
                {
                    rankList.clear();
                    new ByMale().execute();
                }

                // 만약 현재 선택한 스피너의 값이 "전체에서"일 경우
                else if(rankSpinner.getSelectedItem().equals("여자 선호도"))
                {
                    rankList.clear();
                    new ByFemale().execute();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    // (22)여자 중에서 순위 불러오기
    class ByFemale extends AsyncTask<Void, Void, String>
    {
        String target;  //우리가 접속할 홈페이지 주소가 들어감

        @Override
        protected void onPreExecute() {
            // 스케쥴 리스트를 검사할 수 있도록 userID를 넣어줌
            try
            {
                target = "http://ggavi2000.cafe24.com/ByFemale.php";  //해당 웹 서버에 접속
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // 해당 서버에 접속할 수 있도록 URL을 커넥팅 한다.
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                // 넘어오는 결과값을 그대로 저장
                InputStream inputStream = httpURLConnection.getInputStream();

                // 해당 inputStream에 있던 내용들을 버퍼에 담아서 읽을 수 있도록 해줌
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                // 이제 temp에 하나씩 읽어와서 그것을 문자열 형태로 저장
                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                // null 값이 아닐 때까지 계속 반복해서 읽어온다.
                while ((temp=bufferedReader.readLine()) != null)
                {
                    // temp에 한줄씩 추가하면서 넣어줌
                    stringBuilder.append(temp + "\n");
                }

                // 끝난 뒤 닫기
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();  //인터넷도 끊어줌
                return stringBuilder.toString().trim();
            }

            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override  // (18) 이부분은 바꿔준다 - 해당 결과를 처리할 수 있는 onPostExecute()
        public void onPostExecute(String result) {
            try {
                // 해당 결과(result) 응답 부분을 처리
                JSONObject jsonObject = new JSONObject(result);

                // response에 각각의 공지사항 리스트가 담기게 됨
                JSONArray jsonArray = jsonObject.getJSONArray("response");  //아까 변수 이름


                // 복붙하고 변경되는 부위
                int count = 0;
                int courseID;
                String courseGrade;
                String courseTitle;
                String courseProfessor;
                int courseCredit;
                int courseDivide;
                int coursePersonnel;  // 전체 강의 인원
                String courseTime;


                // 해당 홈페이지를 탐색한 뒤 나온 결과를 분석해서 파싱한 뒤에
                while (count < jsonArray.length())
                {
                    // 현재 배열의 원소값을 저장
                    JSONObject object = jsonArray.getJSONObject(count);

                    // 수정
                    courseID = object.getInt("courseID");
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseProfessor = object.getString("courseProfessor");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseTime = object.getString("courseTime");


                    // 강의 추가 부분 (우리에게 필요한 것만 생성자로)
                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide, coursePersonnel, courseTime, courseProfessor));  // Course.java에서 추가한 생성자 부분을 그대로 넣어준다
                    count++;
                }

                // 어댑터를 새롭게 갱신
                rankListAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // (22)남자 중에서 순위 불러오기
    class ByMale extends AsyncTask<Void, Void, String>
    {
        String target;  //우리가 접속할 홈페이지 주소가 들어감

        @Override
        protected void onPreExecute() {
            // 스케쥴 리스트를 검사할 수 있도록 userID를 넣어줌
            try
            {
                target = "http://ggavi2000.cafe24.com/ByMale.php";  //해당 웹 서버에 접속
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // 해당 서버에 접속할 수 있도록 URL을 커넥팅 한다.
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                // 넘어오는 결과값을 그대로 저장
                InputStream inputStream = httpURLConnection.getInputStream();

                // 해당 inputStream에 있던 내용들을 버퍼에 담아서 읽을 수 있도록 해줌
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                // 이제 temp에 하나씩 읽어와서 그것을 문자열 형태로 저장
                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                // null 값이 아닐 때까지 계속 반복해서 읽어온다.
                while ((temp=bufferedReader.readLine()) != null)
                {
                    // temp에 한줄씩 추가하면서 넣어줌
                    stringBuilder.append(temp + "\n");
                }

                // 끝난 뒤 닫기
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();  //인터넷도 끊어줌
                return stringBuilder.toString().trim();
            }

            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override  // (18) 이부분은 바꿔준다 - 해당 결과를 처리할 수 있는 onPostExecute()
        public void onPostExecute(String result) {
            try {
                // 해당 결과(result) 응답 부분을 처리
                JSONObject jsonObject = new JSONObject(result);

                // response에 각각의 공지사항 리스트가 담기게 됨
                JSONArray jsonArray = jsonObject.getJSONArray("response");  //아까 변수 이름


                // 복붙하고 변경되는 부위
                int count = 0;
                int courseID;
                String courseGrade;
                String courseTitle;
                String courseProfessor;
                int courseCredit;
                int courseDivide;
                int coursePersonnel;  // 전체 강의 인원
                String courseTime;


                // 해당 홈페이지를 탐색한 뒤 나온 결과를 분석해서 파싱한 뒤에
                while (count < jsonArray.length())
                {
                    // 현재 배열의 원소값을 저장
                    JSONObject object = jsonArray.getJSONObject(count);

                    // 수정
                    courseID = object.getInt("courseID");
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseProfessor = object.getString("courseProfessor");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseTime = object.getString("courseTime");


                    // 강의 추가 부분 (우리에게 필요한 것만 생성자로)
                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide, coursePersonnel, courseTime, courseProfessor));  // Course.java에서 추가한 생성자 부분을 그대로 넣어준다
                    count++;
                }

                // 어댑터를 새롭게 갱신
                rankListAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // (21)전체에서 순위 불러오기
    class ByEntire extends AsyncTask<Void, Void, String>
    {
        String target;  //우리가 접속할 홈페이지 주소가 들어감

        @Override
        protected void onPreExecute() {
            // 스케쥴 리스트를 검사할 수 있도록 userID를 넣어줌
            try
            {
                target = "http://ggavi2000.cafe24.com/ByEntire.php";  //해당 웹 서버에 접속
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // 해당 서버에 접속할 수 있도록 URL을 커넥팅 한다.
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                // 넘어오는 결과값을 그대로 저장
                InputStream inputStream = httpURLConnection.getInputStream();

                // 해당 inputStream에 있던 내용들을 버퍼에 담아서 읽을 수 있도록 해줌
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                // 이제 temp에 하나씩 읽어와서 그것을 문자열 형태로 저장
                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                // null 값이 아닐 때까지 계속 반복해서 읽어온다.
                while ((temp=bufferedReader.readLine()) != null)
                {
                    // temp에 한줄씩 추가하면서 넣어줌
                    stringBuilder.append(temp + "\n");
                }

                // 끝난 뒤 닫기
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();  //인터넷도 끊어줌
                return stringBuilder.toString().trim();
            }

            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override  // (18) 이부분은 바꿔준다 - 해당 결과를 처리할 수 있는 onPostExecute()
        public void onPostExecute(String result) {
            try {
                // 해당 결과(result) 응답 부분을 처리
                JSONObject jsonObject = new JSONObject(result);

                // response에 각각의 공지사항 리스트가 담기게 됨
                JSONArray jsonArray = jsonObject.getJSONArray("response");  //아까 변수 이름


                // 복붙하고 변경되는 부위
                int count = 0;
                int courseID;
                String courseGrade;
                String courseTitle;
                String courseProfessor;
                int courseCredit;
                int courseDivide;
                int coursePersonnel;  // 전체 강의 인원
                String courseTime;


                // 해당 홈페이지를 탐색한 뒤 나온 결과를 분석해서 파싱한 뒤에
                while (count < jsonArray.length())
                {
                    // 현재 배열의 원소값을 저장
                    JSONObject object = jsonArray.getJSONObject(count);

                    // 수정
                    courseID = object.getInt("courseID");
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseProfessor = object.getString("courseProfessor");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseTime = object.getString("courseTime");


                    // 강의 추가 부분 (우리에게 필요한 것만 생성자로)
                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide, coursePersonnel, courseTime, courseProfessor));  // Course.java에서 추가한 생성자 부분을 그대로 넣어준다
                    count++;
                }

                // 어댑터를 새롭게 갱신
                rankListAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // (18) ScheduleFragment.java에서 복붙
    //  데이터베이스에 접속할 수 있도록 만든 함수
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;  //우리가 접속할 홈페이지 주소가 들어감

        @Override
        protected void onPreExecute() {
            // 스케쥴 리스트를 검사할 수 있도록 userID를 넣어줌
            try
            {   // MainActivity에 있는 유저 아이디를 가져옴
                target = "http://ggavi2000.cafe24.com/StatisticsCourseList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");  //해당 웹 서버에 접속
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // 해당 서버에 접속할 수 있도록 URL을 커넥팅 한다.
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                // 넘어오는 결과값을 그대로 저장
                InputStream inputStream = httpURLConnection.getInputStream();

                // 해당 inputStream에 있던 내용들을 버퍼에 담아서 읽을 수 있도록 해줌
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                // 이제 temp에 하나씩 읽어와서 그것을 문자열 형태로 저장
                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                // null 값이 아닐 때까지 계속 반복해서 읽어온다.
                while ((temp=bufferedReader.readLine()) != null)
                {
                    // temp에 한줄씩 추가하면서 넣어줌
                    stringBuilder.append(temp + "\n");
                }

                // 끝난 뒤 닫기
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();  //인터넷도 끊어줌
                return stringBuilder.toString().trim();
            }

            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override  // 위에 나오는 ByEntire 함수의 원본
        public void onPostExecute(String result) {
            try {
                // 해당 결과(result) 응답 부분을 처리
                JSONObject jsonObject = new JSONObject(result);

                // response에 각각의 리스트가 담기게 됨
                JSONArray jsonArray = jsonObject.getJSONArray("response");  //아까 변수 이름

                int count = 0;
                int courseID;
                String courseGrade;
                String courseTitle;
                int courseDivide;     // 강의 분반
                int coursePersonnel;  // 전체 강의 인원
                int courseRival;      // 경쟁자 수 (그 강의를 신청한 총 인원)


                // 해당 홈페이지를 탐색한 뒤 나온 결과를 분석해서 파싱한 뒤에
                while (count < jsonArray.length())
                {
                    // 현재 배열의 원소값을 저장
                    JSONObject object = jsonArray.getJSONObject(count);

                    courseID = object.getInt("courseID");
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseRival = object.getInt("COUNT(SCHEDULE.courseID)");

                    // (19) 추가
                    int courseCredit = object.getInt("courseCredit");  // 해당 강의의 학점을 불러오도록 한다
                    totalCredit += courseCredit;

                    // 강의 추가 부분
                    courseList.add(new Course(courseID, courseTitle, courseDivide, courseGrade, coursePersonnel, courseRival, courseCredit));  // Course.java에서 추가한 생성자 부분을 그대로 넣어준다
                    count++;
                }

                // 어댑터를 새롭게 갱신
                adapter.notifyDataSetChanged();
                credit.setText(totalCredit + "학점");     // 전체 학점 출력

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}