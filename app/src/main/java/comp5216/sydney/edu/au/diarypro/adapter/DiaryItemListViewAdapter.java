package comp5216.sydney.edu.au.diarypro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp5216.sydney.edu.au.diarypro.R;
import comp5216.sydney.edu.au.diarypro.entity.DiaryItem;

public class DiaryItemListViewAdapter extends BaseAdapter {


    private List<DiaryItem> diaryItemList;
    private Context context;

    public DiaryItemListViewAdapter(List<DiaryItem> diaryItemList, Context context) {
        this.diaryItemList = diaryItemList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return diaryItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return diaryItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_diary_item,parent,false);
            viewHolder.name = convertView.findViewById(R.id.textview_diary_item_name);
            viewHolder.date = convertView.findViewById(R.id.textview_dairy_date);
            viewHolder.imageView = convertView.findViewById(R.id.diaryItemImage);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DiaryItem diaryItem = diaryItemList.get(position);
        viewHolder.diaryItem = diaryItem;
        viewHolder.name.setText(diaryItem.getName());
        viewHolder.date.setText(diaryItem.getDate());
        //TODO change the path
        viewHolder.imageView.setImageResource(diaryItem.getImage());
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView date;
        ImageView imageView;
        DiaryItem diaryItem;

        public ViewHolder() {

        }

    }
}
