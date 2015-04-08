package com.example.tm__mt.ecoquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

public class ResultAdapter extends BaseAdapter {

    private static final int TYPE_ANSWER_OK = 0;
    private static final int TYPE_ANSWER_NOK = 1;
    private static final int TYPE_MAX_COUNT = TYPE_ANSWER_NOK + 1;

    private TreeSet<Integer> answersOKset = new TreeSet<>();
    private ArrayList<ResultRow> data = new ArrayList<>();
    private LayoutInflater inflater;

    public ResultAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addOKItem(final ResultRow item) {
        data.add(item);

        notifyDataSetChanged();
        answersOKset.add(data.size() - 1);
    }

    public void addNOKItem(final ResultRow item) {
        data.add(item);

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return answersOKset.contains(position) ? TYPE_ANSWER_OK : TYPE_ANSWER_NOK;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResultRowHolder holder;

        if (convertView == null) {
            holder = new ResultRowHolder();
            int type = getItemViewType(position);
            switch (type) {
                case TYPE_ANSWER_OK:
                    convertView = inflater.inflate(R.layout.result_row_item_ok, parent, false);
                    break;
                case TYPE_ANSWER_NOK:
                    convertView = inflater.inflate(R.layout.result_row_item_nok, parent, false);
                    break;
            }
            if (convertView != null) {
                holder.tvQuestionNumber = (TextView)convertView.findViewById(R.id.tvQuestionNumber);
                holder.tvGivenAnswer = (TextView)convertView.findViewById(R.id.tvGivenAnswer);
                holder.tvAnswerTime = (TextView)convertView.findViewById(R.id.tvAnswerTime);
                convertView.setTag(holder);
            }
        } else {
            holder = (ResultRowHolder) convertView.getTag();
        }

        ResultRow rr = data.get(position);
        holder.tvQuestionNumber.setText("" + rr.questionNumber);
        holder.tvGivenAnswer.setText("" + rr.givenAnswer);
        holder.tvAnswerTime.setText(rr.answerTime);

        return convertView;
    }

    static class ResultRowHolder {
        TextView tvQuestionNumber;
        TextView tvGivenAnswer;
        TextView tvAnswerTime;
    }
}
