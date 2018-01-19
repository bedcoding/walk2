package com.example.ggavi.registeration;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

// Fragment 이놈은 기본적으로 특정한 화면 안에 있는
// 세부적인 화면을 만들 때 많이 사용하는 레이아웃이다.

public class ScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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

    // (15) 0교시 ~ 14교시
    // (16) TextView를 깃허브에서 긁어온 자동 글자크기 조절 함수로 변경
    private AutoResizeTextView monday[] = new AutoResizeTextView[14];
    private AutoResizeTextView tuesday[] = new AutoResizeTextView[14];
    private AutoResizeTextView wednesday[] = new AutoResizeTextView[14];
    private AutoResizeTextView thursday[] = new AutoResizeTextView[14];
    private AutoResizeTextView friday[] = new AutoResizeTextView[14];

    // 하나의 스케쥴 객체를 만든다.
    private Schedule schedule = new Schedule();


    // (15) 해당 프래그먼트가 생성이 될 때 생성되는 것
    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        monday[0] = (AutoResizeTextView) getView().findViewById(R.id.monday0);
        monday[1] = (AutoResizeTextView) getView().findViewById(R.id.monday1);
        monday[2] = (AutoResizeTextView) getView().findViewById(R.id.monday2);
        monday[3] = (AutoResizeTextView) getView().findViewById(R.id.monday3);
        monday[4] = (AutoResizeTextView) getView().findViewById(R.id.monday4);
        monday[5] = (AutoResizeTextView) getView().findViewById(R.id.monday5);
        monday[6] = (AutoResizeTextView) getView().findViewById(R.id.monday6);
        monday[7] = (AutoResizeTextView) getView().findViewById(R.id.monday7);
        monday[8] = (AutoResizeTextView) getView().findViewById(R.id.monday8);
        monday[9] = (AutoResizeTextView) getView().findViewById(R.id.monday9);
        monday[10] = (AutoResizeTextView) getView().findViewById(R.id.monday10);
        monday[11] = (AutoResizeTextView) getView().findViewById(R.id.monday11);
        monday[12] = (AutoResizeTextView) getView().findViewById(R.id.monday12);
        monday[13] = (AutoResizeTextView) getView().findViewById(R.id.monday13);

        tuesday[0] = (AutoResizeTextView) getView().findViewById(R.id.tuesday0);
        tuesday[1] = (AutoResizeTextView) getView().findViewById(R.id.tuesday1);
        tuesday[2] = (AutoResizeTextView) getView().findViewById(R.id.tuesday2);
        tuesday[3] = (AutoResizeTextView) getView().findViewById(R.id.tuesday3);
        tuesday[4] = (AutoResizeTextView) getView().findViewById(R.id.tuesday4);
        tuesday[5] = (AutoResizeTextView) getView().findViewById(R.id.tuesday5);
        tuesday[6] = (AutoResizeTextView) getView().findViewById(R.id.tuesday6);
        tuesday[7] = (AutoResizeTextView) getView().findViewById(R.id.tuesday7);
        tuesday[8] = (AutoResizeTextView) getView().findViewById(R.id.tuesday8);
        tuesday[9] = (AutoResizeTextView) getView().findViewById(R.id.tuesday9);
        tuesday[10] = (AutoResizeTextView) getView().findViewById(R.id.tuesday10);
        tuesday[11] = (AutoResizeTextView) getView().findViewById(R.id.tuesday11);
        tuesday[12] = (AutoResizeTextView) getView().findViewById(R.id.tuesday12);
        tuesday[13] = (AutoResizeTextView) getView().findViewById(R.id.tuesday13);

        wednesday[0] = (AutoResizeTextView) getView().findViewById(R.id.wednesday0);
        wednesday[1] = (AutoResizeTextView) getView().findViewById(R.id.wednesday1);
        wednesday[2] = (AutoResizeTextView) getView().findViewById(R.id.wednesday2);
        wednesday[3] = (AutoResizeTextView) getView().findViewById(R.id.wednesday3);
        wednesday[4] = (AutoResizeTextView) getView().findViewById(R.id.wednesday4);
        wednesday[5] = (AutoResizeTextView) getView().findViewById(R.id.wednesday5);
        wednesday[6] = (AutoResizeTextView) getView().findViewById(R.id.wednesday6);
        wednesday[7] = (AutoResizeTextView) getView().findViewById(R.id.wednesday7);
        wednesday[8] = (AutoResizeTextView) getView().findViewById(R.id.wednesday8);
        wednesday[9] = (AutoResizeTextView) getView().findViewById(R.id.wednesday9);
        wednesday[10] = (AutoResizeTextView) getView().findViewById(R.id.wednesday10);
        wednesday[11] = (AutoResizeTextView) getView().findViewById(R.id.wednesday11);
        wednesday[12] = (AutoResizeTextView) getView().findViewById(R.id.wednesday12);
        wednesday[13] = (AutoResizeTextView) getView().findViewById(R.id.wednesday13);

        thursday[0] = (AutoResizeTextView) getView().findViewById(R.id.thursday0);
        thursday[1] = (AutoResizeTextView) getView().findViewById(R.id.thursday1);
        thursday[2] = (AutoResizeTextView) getView().findViewById(R.id.thursday2);
        thursday[3] = (AutoResizeTextView) getView().findViewById(R.id.thursday3);
        thursday[4] = (AutoResizeTextView) getView().findViewById(R.id.thursday4);
        thursday[5] = (AutoResizeTextView) getView().findViewById(R.id.thursday5);
        thursday[6] = (AutoResizeTextView) getView().findViewById(R.id.thursday6);
        thursday[7] = (AutoResizeTextView) getView().findViewById(R.id.thursday7);
        thursday[8] = (AutoResizeTextView) getView().findViewById(R.id.thursday8);
        thursday[9] = (AutoResizeTextView) getView().findViewById(R.id.thursday9);
        thursday[10] = (AutoResizeTextView) getView().findViewById(R.id.thursday10);
        thursday[11] = (AutoResizeTextView) getView().findViewById(R.id.thursday11);
        thursday[12] = (AutoResizeTextView) getView().findViewById(R.id.thursday12);
        thursday[13] = (AutoResizeTextView) getView().findViewById(R.id.thursday13);

        friday[0] = (AutoResizeTextView) getView().findViewById(R.id.friday0);
        friday[1] = (AutoResizeTextView) getView().findViewById(R.id.friday1);
        friday[2] = (AutoResizeTextView) getView().findViewById(R.id.friday2);
        friday[3] = (AutoResizeTextView) getView().findViewById(R.id.friday3);
        friday[4] = (AutoResizeTextView) getView().findViewById(R.id.friday4);
        friday[5] = (AutoResizeTextView) getView().findViewById(R.id.friday5);
        friday[6] = (AutoResizeTextView) getView().findViewById(R.id.friday6);
        friday[7] = (AutoResizeTextView) getView().findViewById(R.id.friday7);
        friday[8] = (AutoResizeTextView) getView().findViewById(R.id.friday8);
        friday[9] = (AutoResizeTextView) getView().findViewById(R.id.friday9);
        friday[10] = (AutoResizeTextView) getView().findViewById(R.id.friday10);
        friday[11] = (AutoResizeTextView) getView().findViewById(R.id.friday11);
        friday[12] = (AutoResizeTextView) getView().findViewById(R.id.friday12);
        friday[13] = (AutoResizeTextView) getView().findViewById(R.id.friday13);

        // (15) BackgroundTask 실행
        new BackgroundTask().execute();
    }

    // (15) CourseListAdapter.java에서 복붙
    //  데이터베이스에 접속할 수 있도록 만든 함수
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;  //우리가 접속할 홈페이지 주소가 들어감

        @Override
        protected void onPreExecute() {
            // 스케쥴 리스트를 검사할 수 있도록 userID를 넣어줌
            try
            {   // MainActivity에 있는 유저 아이디를 가져옴
                target = "http://ggavi2000.cafe24.com/ScheduleList.php?userID=" + URLEncoder.encode(MainActivity. userID, "UTF-8");  //해당 웹 서버에 접속
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

        @Override  // (15) 이부분은 바꿔준다 - 해당 결과를 처리할 수 있는 onPostExecute()
        public void onPostExecute(String result) {
            try {
                // 해당 결과(result) 응답 부분을 처리
                JSONObject jsonObject = new JSONObject(result);

                // response에 각각의 공지사항 리스트가 담기게 됨
                JSONArray jsonArray = jsonObject.getJSONArray("response");  //아까 변수 이름

                int count = 0;
                String courseProfessor;
                String courseTime;
                String courseTitle;
                int courseID;


                // 해당 홈페이지를 탐색한 뒤 나온 결과를 분석해서 파싱한 뒤에
                while (count < jsonArray.length())
                {
                    // 현재 배열의 원소값을 저장
                    JSONObject object = jsonArray.getJSONObject(count);

                    // 공지사항의 Content, Name, Date에 해당하는 값을 가져와라는 뜻
                    courseID = object.getInt("courseID");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseTitle = object.getString("courseTitle");  // courseTitle로 나온 값을 그대로 가져와서 변수에 넣겠다는 뜻

                    // 리스트에 추가해줌
                    // 현재 해당 사용자가 가지고 있는 모든 시간표 데이터에 있는 강의ID가
                    // 코스ID 리스트에 담기게 되고, 스케쥴 또한 마찬가지로 들어가게 된다.
                    schedule.addSchedule(courseTime, courseTitle, courseProfessor);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 스케쥴에서 세팅 (모든 강의가 추가된 이후 세팅이 마쳐져서 텍스트 뷰에 출력이 되는 것)
            schedule.setting(monday, tuesday, wednesday, thursday, friday, getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
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
