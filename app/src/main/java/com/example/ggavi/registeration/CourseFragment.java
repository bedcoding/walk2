package com.example.ggavi.registeration;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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

public class CourseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CourseFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
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


    // (9)values 폴더의 arrays.xml과 연동하여 화면에 뿌려주는 부분 시작점
    private ArrayAdapter yearAdapter;
    private Spinner yearSpinner;
    private ArrayAdapter termAdapter;
    private Spinner termSpinner;
    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter majorAdapter;
    private Spinner majorSpinner;

    private String courseUniversity = "";


    // 파싱을 위해 추가로 선언한 변수 (11강)
    private ListView courseListView;
    private CourseListAdapter adapter;
    private List<Course> courseList;


    @Override    // 액티비티가 만들어질 때 어떠한 처리가 이뤄지는 부분
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        // (9)선택: 예를들면 현재 선택한 것이 A인지 B인지 선택하는 식
        final RadioGroup courseUniversityGroup = (RadioGroup) getView().findViewById(R.id.courseUniversityGroup);
        yearSpinner = (Spinner) getView().findViewById(R.id.yearSpinner);
        termSpinner = (Spinner) getView().findViewById(R.id.termSpinner);
        areaSpinner = (Spinner) getView().findViewById(R.id.areaSpinner);
        majorSpinner = (Spinner) getView().findViewById(R.id.majorSpinner);


        // courseUniversityGroup을 선택했을 때 이뤄지는 이벤트 처리
        courseUniversityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i)  //i : checkedId
            {
                // 현재 선택된 라디오 버튼이 담김
                RadioButton courseButton = (RadioButton) getView().findViewById(i);
                courseUniversity = courseButton.getText().toString();

                // yearAdapter는 아까 만든 year 연도 부분의 배열과 맞춰줌
                yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.year, android.R.layout.simple_spinner_dropdown_item);
                yearSpinner.setAdapter(yearAdapter);  //yearAdapter를 자신의 어댑터로 설정

                termAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.term, android.R.layout.simple_spinner_dropdown_item);
                termSpinner.setAdapter(termAdapter);  //termAdapter를 자신의 어댑터로 설정

                // 무엇을 선택했냐에 따라 다르게 나오도록 한다
                // 예: 대학을 선택하면 학부 과정이, 대학원을 선택하면 대학원 과정이 나오는 식
                if(courseUniversity.equals("걷는길")) {
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.universityArea, android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapter);  //areaAdapter를 자신의 어댑터로 설정

                    //추가 (학부를 선택했을 때 바로 교양및기타가 보임)
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.universityRefinementMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                else if(courseUniversity.equals("자전거길")) {
                    areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.graduateArea, android.R.layout.simple_spinner_dropdown_item);
                    areaSpinner.setAdapter(areaAdapter);  //areaAdapter를 자신의 어댑터로 설정

                    //추가 (대학원을 선택했을 때 바로 대학원 강의가 보임)
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.graduateMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
            }
        });


        // 특정한 원소가 선택되었을 때 발생할 수 있는 그런 이벤트 처리 (상황에 따라 다른 배열이 나옴)
        // 예: 위에서 학부인지 대학원인지, 교양인지 전공인지에 따라 현재 보여줄 수 있는 과가 달라진다.
        // 이 부분은 values 폴더의 arrays.xml 파일과 연동된다.
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override // 이벤트를 다루는 부분
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // 만약 이게 '교양 및 기타'와 같다면, 특정한 어댑터로 초기화 해준다 (예: universityRefinementMajor로 초기화)
                // 그리고 majorSpinner가 setAdapter를 포함할 수 있도록 해준다.

                if(areaSpinner.getSelectedItem().equals("교양및기타"))
                {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.universityRefinementMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(areaSpinner.getSelectedItem().equals("전공"))
                {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.universityMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }

                if(areaSpinner.getSelectedItem().equals("일반대학원"))
                {
                    majorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.graduateMajor, android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(majorAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // 초기화 (해당 리스트 뷰와 일치시킴)
        courseListView = (ListView) getView().findViewById(R.id.courseListView);
        courseList = new ArrayList<Course>();
        adapter = new CourseListAdapter(getContext().getApplicationContext(), courseList, this);  //12강 this 추가
        courseListView.setAdapter(adapter);


        // 버튼 이벤트 추가 (검색 버튼)
        Button searchButton = (Button) getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
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


    // 카페24 Course DB연결 (MainActivity에서 공지사항 DB연결 부분 복붙)
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;  //우리가 접속할 홈페이지 주소가 들어감

        @Override
        protected void onPreExecute() {

            try
            {
                // 주소에 courseUniversity라는 매개변수를 보냄 (courseUniversity라는 변수를 UTF-8으로 치환
                // 그리고 courseYear에서는 현재 리스트에서 2018년인지 2019년인지 알기 위해 yearSpinner에 있는 정보를 0번째부터 4자리까지(2018) UTF-8으로 치환해서 보냄
                // 나머지도 그런 식으로 보내준다. (현재 영역을 UTF-8로 치환해서 보내줌) (현재 학과 정보를 받아와서 majorSpinner로 받아와서 UTF-8으로 보내줌)

                target = "http://ggavi2000.cafe24.com/CourseList.php?courseUniversity=" + URLEncoder.encode(courseUniversity, "UTF-8") +
                        "&courseYear=" + URLEncoder.encode(yearSpinner.getSelectedItem().toString().substring(0, 4), "UTF-8") +
                        "&courseTerm=" + URLEncoder.encode(termSpinner.getSelectedItem().toString(), "UTF-8") +
                        "&courseArea=" + URLEncoder.encode(areaSpinner.getSelectedItem().toString(), "UTF-8") +
                        "&courseMajor=" + URLEncoder.encode(majorSpinner.getSelectedItem().toString(), "UTF-8");
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



        @Override
        // 이 부분은 MainActivity에서 복붙하지 않고 수정했다.
        // 특정한 강의 학과를 넣었을 때, 모든 강의 리스트가 나올 수 있는지 확인
        public void onPostExecute(String result) {
            try {
                // 수정: 실제 파싱을 통해 실제 강의 목록을 보여주도록 변경
                // 먼저, 시작화면에 courseList(해당 강의 목록을) 전부 없애준다.
                courseList.clear();

                // 그리고 JSON 파싱을 한다.
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

                // 이후 Course.java 안에 있는 변수를 그대로 넣어준다.
                int courseID;               // 고유 번호
                String courseUniversity;    // 학부 혹은 대학원
                int courseYear;             // 해당 년도
                String courseTerm;          // 해당 학기
                String courseArea;          // 강의 영역
                String courseMajor;         // 해당 학과
                String courseGrade;         // 해당 학년
                String courseTitle;         // 강의 제목
                int courseCredit;           // 강의 학점
                int courseDivide;           // 강의 분반
                int coursePersonnel;        // 강의 제한 인원
                String courseProfessor;     // 강의 교수
                String coursetime;          // 강의 시간대
                String courseRoom;          // 강의실


                // 이제 모든 배열의 원소를 전부 돌면서 어떠한 동작을 처리하게 한다.
                while(count < jsonArray.length())
                {
                    // 현재 배열의 원소값을 가져올 수 있도록 한다.
                    JSONObject object = jsonArray.getJSONObject(count);

                    // 각각의 변수에 해당 JSON Object를 통해 가져온 문자열의 파싱된 내용을 넣어준다.
                    // 매 강의 정보마다 이렇게 각각의 원소에 각각의 강의 정보가 들어간다.
                    courseID = object.getInt("courseID");
                    courseUniversity = object.getString("courseUniversity");
                    courseYear = object.getInt("courseYear");
                    courseTerm = object.getString("courseTerm");
                    courseArea = object.getString("courseArea");
                    courseMajor = object.getString("courseMajor");
                    courseGrade = object.getString("courseGrade");
                    courseTitle = object.getString("courseTitle");
                    courseCredit = object.getInt("courseCredit");
                    courseDivide = object.getInt("courseDivide");
                    coursePersonnel = object.getInt("coursePersonnel");
                    courseProfessor = object.getString("courseProfessor");
                    coursetime = object.getString("courseTime");
                    courseRoom = object.getString("courseRoom");

                    // 매 순간순간마다 해당 강의 정보를 읽어서 course라는 클래스, 즉 강의에 대한 객체로서 생성을 한 뒤 객체를 처리
                    Course course = new Course(courseID, courseUniversity, courseYear, courseTerm, courseArea, courseMajor, courseGrade, courseTitle, courseCredit, courseDivide, coursePersonnel, courseProfessor, coursetime, courseRoom);

                    // 위에서 코스라는 객체 안에 해당 정보가 들어간 상태로 만들어진 뒤
                    courseList.add(course);
                    count++;
                }

                // 만약 count가 0이라면 (즉 어떠한 강의도 조회되지 않았다면)
                if(count == 0)
                {
                    AlertDialog dialog;

                    // 현재의 CourseFragment라는 액티비티에 메시지가 나오게 된다.
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourseFragment.this.getActivity());
                    dialog = builder.setMessage("조회된 강의가 없습니다. \n날짜를 확인하세요.")
                            .setPositiveButton("확인", null)
                            .create();  //create 이걸로 다이얼로그에 다시 넣어줌
                    dialog.show();
                    // 여기까지 하면 출력 완료
                }


                // 값이 변경되었음을 알린다.
                adapter.notifyDataSetChanged();
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
