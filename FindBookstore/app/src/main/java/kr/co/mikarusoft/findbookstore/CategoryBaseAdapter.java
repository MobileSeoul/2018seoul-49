package kr.co.mikarusoft.findbookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryBaseAdapter extends BaseAdapter {

    ArrayList<CategoryItem> categoryItems;
    Context context;

    public CategoryBaseAdapter(ArrayList<CategoryItem> categoryItems, Context context) {
        this.categoryItems = categoryItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categoryItems.size();
    }

    @Override
    public CategoryItem getItem(int position) {
        return categoryItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categoryItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_layout, parent,false);
        }

        //txt 갖고 오기
        TextView textView = (TextView) convertView.findViewById(R.id.txt);
        textView.setText(categoryItems.get(position).getCate());

        return convertView;
    }

    public void setCategoryItems(ArrayList<CategoryItem> list){
        this.categoryItems = list;
    }
}
