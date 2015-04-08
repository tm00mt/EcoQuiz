package com.example.tm__mt.ecoquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

public class RankingAdapter extends BaseAdapter {

    private static final int TYPE_ITEM_OLD = 0;
    private static final int TYPE_ITEM_NEW = 1;
    private static final int TYPE_MAX_COUNT = TYPE_ITEM_NEW + 1;

    private Integer itemNew = -1;
    private ArrayList<RankingRow> data = new ArrayList<>();
    private LayoutInflater inflater;

    public RankingAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addNewItem(final RankingRow item) {
        data.add(item);

        notifyDataSetChanged();
        itemNew = item.attempt;
    }

    public void addOldItem(final RankingRow item) {
        data.add(item);

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).attempt == itemNew ? TYPE_ITEM_NEW : TYPE_ITEM_OLD;
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
        RankingRowHolder holder;

        if (convertView == null) {
            holder = new RankingRowHolder();
            int type = getItemViewType(position);
            switch (type) {
                case TYPE_ITEM_OLD:
                    convertView = inflater.inflate(R.layout.ranking_row_item_old, parent, false);
                    break;
                case TYPE_ITEM_NEW:
                    convertView = inflater.inflate(R.layout.ranking_row_item_new, parent, false);
                    break;
            }
            if (convertView != null) {
                holder.tvPosition = (TextView)convertView.findViewById(R.id.tvPosition);
                holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
                holder.tvScore = (TextView)convertView.findViewById(R.id.tvScore);
                holder.tvTime = (TextView)convertView.findViewById(R.id.tvTime);
                convertView.setTag(holder);
            }

        } else {
            holder = (RankingRowHolder) convertView.getTag();
        }

        RankingRow rr = data.get(position);
        holder.tvPosition.setText("" + (position + 1));
        holder.tvName.setText(rr.name);
        holder.tvScore.setText("" + rr.score);
        holder.tvTime.setText(rr.time);

        return convertView;
    }

    static class RankingRowHolder {
        TextView tvPosition;
        TextView tvName;
        TextView tvScore;
        TextView tvTime;
    }
}
