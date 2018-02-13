package com.example.ggavi.registeration;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

public class Schedule {

    // 월요일부터 금요일까지의 모든 교시 데이터 (0교시~13교시)
    private String monday[] = new String[1];
    //private String tuesday[] = new String[14];
    //private String wednesday[] = new String[14];
    //private String thursday[] = new String[14];
    //private String friday[] = new String[14];


    // 생성자
    public Schedule() {

        // 모든 날짜에 강의 정보가 공백이 들어가게 한다.
        for(int i=0; i<1; i++)
        {
            monday[i] = "";
            //tuesday[i] = "";
            //wednesday[i] = "";
            //thursday[i] = "";
            //friday[i] = "";
        }
    }


    // 스케쥴 정보를 담는 특정한 텍스트가 있을때,
    // 이 데이터를 파싱해서 강의 정보가 들어가는 배열에 넣어준다
    public void addSchedule(String scheduleText) {
        int temp;

        // 스케쥴 텍스트에서 '월'이라는 단어가 포함되어 있을 때,
        // 그 스케쥴 텍스트(월이라는 단어가 포함되어 있는 곳)의
        // 위치를 반환하고 temp 라는 값에 들어간다. (예) 월:[3][4][5], 화[4], [5]
        if ((temp = scheduleText.indexOf("월")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if (scheduleText.charAt(i) == ']') {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;

                    // 현재가 '월'요일이기 때문에, 현재 넣을 데이터에 '교시'에 해당하는 숫자 데이터를 그대로 넣을 수 있도록 함
                    monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "1";  // 들어갈 내용: "1"
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '월'요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }
    }

/*

        // 화요일
        if((temp = scheduleText.indexOf("화")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;

                    // 현재가 ' '요일이기 때문에, 현재 넣을 데이터에 '교시'에 해당하는 숫자 데이터를 그대로 넣을 수 있도록 함
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";  // 들어갈 내용: "수업"
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }


        // 수요일
        if((temp = scheduleText.indexOf("수")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;

                    // 현재가 ' '요일이기 때문에, 현재 넣을 데이터에 '교시'에 해당하는 숫자 데이터를 그대로 넣을 수 있도록 함
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";  // 들어갈 내용: "수업"
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }


        // 목요일
        if((temp = scheduleText.indexOf("목")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;

                    // 현재가 ' '요일이기 때문에, 현재 넣을 데이터에 '교시'에 해당하는 숫자 데이터를 그대로 넣을 수 있도록 함
                    thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";  // 들어갈 내용: "수업"
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }


        // 금요일
        if((temp = scheduleText.indexOf("금")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;

                    // 현재가 ' '요일이기 때문에, 현재 넣을 데이터에 '교시'에 해당하는 숫자 데이터를 그대로 넣을 수 있도록 함
                    friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";  // 들어갈 내용: "수업"
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }
    }

*/

    // 새롭게 추가하려는 수강신청의 날짜 데이터가,
    // 현재 자리잡고 있는 스케쥴 데이터와 중복되지 않는지 체크해주는 함수
    public boolean validate(String scheduleText) {

        // 아무것도 없는 경우
        if(scheduleText.equals(""))
        {
             return true;
        }

        int temp;




        if((temp = scheduleText.indexOf("월")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;


                    // 만약 현재 값이 공백이 아니라면 (어떠한 데이터가 이미 있음)
                    if(!monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        // 해당 시간표는 중복으로 들어갈 수 없다!
                        return false;
                    }
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }

        /*
        if((temp = scheduleText.indexOf("화")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;


                    // 만약 현재 값이 공백이 아니라면 (어떠한 데이터가 이미 있음)
                    if(!tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        // 해당 시간표는 중복으로 들어갈 수 없다!
                        return false;
                    }
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }

        if((temp = scheduleText.indexOf("수")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;


                    // 만약 현재 값이 공백이 아니라면 (어떠한 데이터가 이미 있음)
                    if(!wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        // 해당 시간표는 중복으로 들어갈 수 없다!
                        return false;
                    }
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }

        if((temp = scheduleText.indexOf("목")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;


                    // 만약 현재 값이 공백이 아니라면 (어떠한 데이터가 이미 있음)
                    if(!thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        // 해당 시간표는 중복으로 들어갈 수 없다!
                        return false;
                    }
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }

        if((temp = scheduleText.indexOf("금")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;


                    // 만약 현재 값이 공백이 아니라면 (어떠한 데이터가 이미 있음)
                    if(!friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        // 해당 시간표는 중복으로 들어갈 수 없다!
                        return false;
                    }
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }
*/
        // 여기까지 거쳤다면 시간표가 중복되지 않아서 들어갈 수 있는 것
        return true;
    }

    // 스케쥴 정보를 담는 특정한 텍스트가 있을때,
    // 이 데이터를 파싱해서 강의 정보가 들어가는 배열에 넣어준다
    // (15) 강의 제목, 강의를 연 교수 이름까지 받음
    public void addSchedule(String scheduleText, String courseTitle, String courseProfessor) {
        /*
        String professor;

        // 만약 courseProfessor가 빈공간이면 professor도 빈공간이 된다.
        if(courseProfessor.equals(""))
        {
            professor = "";
        }

        else
        {
            // 괄호 안에 이름이 들어간다.
            // professor = "(" + courseProfessor + ")";
            professor = "";  // 그냥 공백으로 변경
        }

        */

        int temp;

        // 스케쥴 텍스트에서 '월'이라는 단어가 포함되어 있을 때,
        // 그 스케쥴 텍스트(월이라는 단어가 포함되어 있는 곳)의
        // 위치를 반환하고 temp 라는 값에 들어간다. (예) 월:[3][4][5], 화[4], [5]
        if((temp = scheduleText.indexOf("월")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;

                    // 현재가 '월'요일이기 때문에, 현재 넣을 데이터에 '교시'에 해당하는 숫자 데이터를 그대로 넣을 수 있도록 함
                    // 마지막 =에 들어갈 내용: 해당 배열 안에 제목이 들어간다.
                    monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle;
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '월'요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }

/*
        // 화요일
        if((temp = scheduleText.indexOf("화")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;

                    // 현재가 ' '요일이기 때문에, 현재 넣을 데이터에 '교시'에 해당하는 숫자 데이터를 그대로 넣을 수 있도록 함
                    // 마지막 =에 들어갈 내용: 해당 배열 안에 강의 제목과 강의를 연 교수님 이름이 들어간다.
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }


        // 수요일
        if((temp = scheduleText.indexOf("수")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;

                    // 현재가 ' '요일이기 때문에, 현재 넣을 데이터에 '교시'에 해당하는 숫자 데이터를 그대로 넣을 수 있도록 함
                    // 마지막 =에 들어갈 내용: 해당 배열 안에 강의 제목과 강의를 연 교수님 이름이 들어간다.
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }


        // 목요일
        if((temp = scheduleText.indexOf("목")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;

                    // 현재가 ' '요일이기 때문에, 현재 넣을 데이터에 '교시'에 해당하는 숫자 데이터를 그대로 넣을 수 있도록 함
                    // 마지막 =에 들어갈 내용: 해당 배열 안에 강의 제목과 강의를 연 교수님 이름이 들어간다.
                    thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }


        // 금요일
        if((temp = scheduleText.indexOf("금")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            // i는 스케쥴 텍스트의 길이가 작을 때까지 반복하거나
            // && 현재 위치에 해당하는 것이 :(콜론)이 아닐 때 반복
            for(int i=temp; i<scheduleText.length() && scheduleText.charAt(i) !=':'; i++)
            {
                // 현재 위치의 단어가 [일 경우, startPoint에 현재 위치를 넣어준다.
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }

                // 만약 현재 위치의 단어가 ]일 경우
                if(scheduleText.charAt(i) == ']')
                {
                    // 여기까지가 숫자가 들어가는 공간
                    endPoint = i;

                    // 현재가 ' '요일이기 때문에, 현재 넣을 데이터에 '교시'에 해당하는 숫자 데이터를 그대로 넣을 수 있도록 함
                    // 마지막 =에 들어갈 내용: 해당 배열 안에 강의 제목과 강의를 연 교수님 이름이 들어간다.
                    friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }

                // 정리: 즉 괄호와 괄호 사이의 숫자를 파싱해서,
                // 그 위치에 해당하는 '  '요일에 해당하는 교시에 수업이라는 데이터를 넣어줌
            }
        }
        */

    }

    // (15) 텍스트 뷰에 해당 강의 목록들을 보여줄 수 있도록 세팅한 함수
    // 특정한 강의가 해당 시간에 이미 들어가 있다면 monday라는 TextView에 현재 강의를 출력할 수 있도록 해야 함
    // (16) TextView를 깃허브에서 긁어온 AutoResizeTextView로 모두 교체 (자동 글자크기 조절)
    public void setting(AutoResizeTextView[] monday, AutoResizeTextView[] tuesday, AutoResizeTextView[] wednesday, AutoResizeTextView[] thursday, AutoResizeTextView[] friday, Context context) {

        // 현재 TextView에 들어가는 내용 중에서 가장 긴 텍스트를 골라서 칸을 이쁘게 맞춰준다.
        int maxLength = 0;
        String maxString = "";
        for(int i=0; i<1; i++)     // 1
        {
            // 만약 현재 텍스트의 길이가 가장 긴 텍스트의 길이보다 긴 경우
            if(this.monday[i].length() > maxLength)
            {
                // maxLength에 최고로 긴 문장 갱신
                maxLength = this.monday[i].length();

                // 현재 가장 긴 문자를 넣어줌
                maxString = this.monday[i];
            }
/*
            if(this.tuesday[i].length() > maxLength)
            {
                // maxLength에 최고로 긴 문장 갱신
                maxLength = this.tuesday[i].length();

                // 현재 가장 긴 문자를 넣어줌
                maxString = this.tuesday[i];
            }

            if(this.wednesday[i].length() > maxLength)
            {
                // maxLength에 최고로 긴 문장 갱신
                maxLength = this.wednesday[i].length();

                // 현재 가장 긴 문자를 넣어줌
                maxString = this.wednesday[i];
            }

            if(this.thursday[i].length() > maxLength)
            {
                // maxLength에 최고로 긴 문장 갱신
                maxLength = this.thursday[i].length();

                // 현재 가장 긴 문자를 넣어줌
                maxString = this.thursday[i];
            }

            if(this.friday[i].length() > maxLength)
            {
                // maxLength에 최고로 긴 문장 갱신
                maxLength = this.friday[i].length();

                // 현재 가장 긴 문자를 넣어줌
                maxString = this.friday[i];
            }

            */
        }


        // 0부터 13교시까지
        for(int i = 0; i<1; i++)
        {
            // 만약 현재 배열에 들어있는 값이 비어있지 않다면
            if(!this.monday[i].equals(""))
            {
                // 이런 식으로 하면 해당 배열에 있는 내용이 저 monday라는 텍스트 뷰에 값이 들어가게 된다.
                monday[i].setText(this.monday[i]);

                // DB에서 값을 불러왔는지 if문으로 체크 (급히 화면 전환하면 터져길래 넣음)
                if (context != null)
                {
                    // 해당 강의가 존재할 때 글자색상이 바뀜
                    monday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                }
            }

            // (16) else문 추가
            else
            {
                // 아래 소스 지우고, 이제 위에서 새로 생성한 maxString을 넣어서 크기를 맞춘다.
                monday[i].setText(maxString);

                // (*편법*) 현재 강의가 비어 있다면 한글 10개를 집어넣어서 크기를 맞춰준다.
                // monday[i].setText("가나다라마바사아자차");
            }

/*

            // 만약 현재 배열에 들어있는 값이 비어있지 않다면
            if(!this.tuesday[i].equals(""))
            {
                // 이런 식으로 하면 해당 배열에 있는 내용이 저 ~day라는 텍스트 뷰에 값이 들어가게 된다.
                tuesday[i].setText(this.tuesday[i]);

                // DB에서 값을 불러왔는지 if문으로 체크 (급히 화면 전환하면 터져길래 넣음)
                if (context != null)
                {
                    // 해당 강의가 존재할 때 글자색상이 바뀜
                    tuesday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                }

            }

            else
            {
                // 아래 소스 지우고, 이제 위에서 새로 생성한 maxString을 넣어서 크기를 맞춘다.
                tuesday[i].setText(maxString);

                // (*편법*) 현재 강의가 비어 있다면 한글 10개를 집어넣어서 크기를 맞춰준다.
                // tuesday[i].setText("가나다라마바사아자차");
            }


            // 만약 현재 배열에 들어있는 값이 비어있지 않다면
            if(!this.wednesday[i].equals(""))
            {
                // 이런 식으로 하면 해당 배열에 있는 내용이 저 ~day라는 텍스트 뷰에 값이 들어가게 된다.
                wednesday[i].setText(this.wednesday[i]);

                // DB에서 값을 불러왔는지 if문으로 체크 (급히 화면 전환하면 터져길래 넣음)
                if (context != null)
                {
                    // 해당 강의가 존재할 때 글자색상이 바뀜
                    wednesday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                }
            }

            else
            {
                // 아래 소스 지우고, 이제 위에서 새로 생성한 maxString을 넣어서 크기를 맞춘다.
                wednesday[i].setText(maxString);

                // (*편법*) 현재 강의가 비어 있다면 한글 10개를 집어넣어서 크기를 맞춰준다.
                // wednesday[i].setText("가나다라마바사아자차");
            }


            // 만약 현재 배열에 들어있는 값이 비어있지 않다면
            if(!this.thursday[i].equals(""))
            {
                // 이런 식으로 하면 해당 배열에 있는 내용이 저 ~day라는 텍스트 뷰에 값이 들어가게 된다.
                thursday[i].setText(this.thursday[i]);

                // DB에서 값을 불러왔는지 if문으로 체크 (급히 화면 전환하면 터져길래 넣음)
                if (context != null)
                {
                    // 해당 강의가 존재할 때 글자색상이 바뀜
                    thursday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                }
            }

            else
            {
                // 아래 소스 지우고, 이제 위에서 새로 생성한 maxString을 넣어서 크기를 맞춘다.
                thursday[i].setText(maxString);

                // (*편법*) 현재 강의가 비어 있다면 한글 10개를 집어넣어서 크기를 맞춰준다.
                // thursday[i].setText("가나다라마바사아자차");
            }


            // 만약 현재 배열에 들어있는 값이 비어있지 않다면
            if(!this.friday[i].equals(""))
            {
                // 이런 식으로 하면 해당 배열에 있는 내용이 저 ~day라는 텍스트 뷰에 값이 들어가게 된다.
                friday[i].setText(this.friday[i]);

                // DB에서 값을 불러왔는지 if문으로 체크 (급히 화면 전환하면 터져길래 넣음)
                if (context != null)
                {
                    // 해당 강의가 존재할 때 글자색상이 바뀜
                    friday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                }
            }

            else
            {
                // 아래 소스 지우고, 이제 위에서 새로 생성한 maxString을 넣어서 크기를 맞춘다.
                friday[i].setText(maxString);

                // (*편법*) 현재 강의가 비어 있다면 한글 10개를 집어넣어서 크기를 맞춰준다.
                // friday[i].setText("가나다라마바사아자차");
            }


            // (16) 위에서 각각의 모든 시간표를 검사한 뒤, 현재 텍스트 크기를 조절한다.
            // 본래 역할: 텍스트의 길이가 얼마나 길든 상관없이 크기를 자동으로 텍스트 뷰에 맞춰줌
            // 왜냐하면 강좌에서는 다른 사람의 소스를 가져왔는데, 내가 가져온 코드와 함수명도 다르고 내용도 달랐다.
            // 두 소스코드를 비교해보고 뭔가 구조적으로 비슷해보이는 함수 하나를 갖고오긴 했는데 제대로 작동되지 않았다.
*/
/*
            monday[i].resizeText();        //얘는 이 깃허브 코드에는 없다!
            tuesday[i].adjustTextSize();   //얘는 내가 긁어온 깃허브 코드에 있는 함수지만 private라서 빨간불이 떴다.
            wednesday[i].adjustTextSize(); //억지로 public으로 바꿔봤지만 소용없음. 다른 비슷한 함수도 없었다.
            thursday[i].adjustTextSize();
            friday[i].adjustTextSize();
*//*


*/

        }
    }

}