package com.example.ggavi.registeration;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter {

    private Context context;
    private List<Notice> noticeList; //Notice가 들어가는 리스트를 만들어줌

    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) //i는 position
    {
        return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) //i는 position
    {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) //i는 position
    {
        // 하나의 View로 만들어 줄 수 있도록 한다. (R.layout.notice로 배달)
        View v = View.inflate(context, R.layout.notice, null);
        TextView noticeText = (TextView) v.findViewById(R.id.noticeText);
        TextView nameText = (TextView) v.findViewById(R.id.nameText);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);

        // noticeText를 현재 리스트에 있는 값으로 넣어줄 수 있도록 한다.
        noticeText.setText(noticeList.get(i).getNotice());
        nameText.setText(noticeList.get(i).getName());
        dateText.setText(noticeList.get(i).getDate());

        v.setTag(noticeList.get(i).getNotice());
        return v;
    }


}
