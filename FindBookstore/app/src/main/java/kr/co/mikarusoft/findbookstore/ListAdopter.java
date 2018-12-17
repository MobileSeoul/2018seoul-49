package kr.co.mikarusoft.findbookstore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by KokoroNihonn3DGame on 2018-08-07.
 */

public class ListAdopter extends BaseAdapter {

    private ArrayList<List_Item> items = new ArrayList<>();



    public ListAdopter(ArrayList<List_Item> items) {
        this.items = items;
    }

    public ArrayList<List_Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<List_Item> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public List_Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final Context context = parent.getContext();

        if(convertView == null)
        {
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, parent,false);
        }

        if (position % 2 == 1)
        {
            convertView.setBackgroundColor(Color.parseColor("#726F63"));
        }
        else {
            convertView.setBackgroundColor(Color.parseColor("#4F4044"));
        }


        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView nameText = (TextView)convertView.findViewById(R.id.textView_name);
        TextView addressText = (TextView)convertView.findViewById(R.id.textView_address);
        TextView exText = (TextView)convertView.findViewById(R.id.bookstore_ex);
        TextView categoryText = (TextView)convertView.findViewById(R.id.textView_category);


        List_Item list_item = getItem(position);

        Bitmap bitmap = list_item.getBitmapimg();
        if(bitmap == null)
        {
            new ImageAsyncTask(imageView,list_item).execute(list_item.getImgURL());

        } else {
            imageView.setImageBitmap(bitmap);
        }

        /*
        Drawable pic = list_item.getPicture();
        if(pic !=null)
        {
            imageView.setImageDrawable(list_item.getPicture());
        }*/

        //서점 설명 가져오는 문자열 변수
        String name = list_item.getName();
        if(name != null)
        {
            nameText.setText(name);
        } else {
            nameText.setText((context.getString(R.string.name_bookstore))+(context.getString(R.string.no_info)));
        }

        String category = list_item.getCategory();
        if(category != null)
        {
            categoryText.setText(category);
        } else {
            nameText.setText((context.getString(R.string.cate_bookstore))+(context.getString(R.string.no_info)));
        }

        String address = list_item.getAddress();
        if (address != null) {
            addressText.setText(address);
        } else {
            nameText.setText((context.getString(R.string.address_bookstore))+(context.getString(R.string.no_info)));
        }

        String ex = list_item.getEx();
        if (ex != null){
            exText.setText(ex);
        } else {
            nameText.setText((context.getString(R.string.ex_bookstore))+(context.getString(R.string.no_info)));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked "+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ClickedData.class);

                intent.putExtra("id", items.get(position).getId());
                intent.putExtra("lng", items.get(position).getGpsLng());
                intent.putExtra("lat", items.get(position).getGpsLat());

                intent.putExtra("name", items.get(position).getName());
                intent.putExtra("address", items.get(position).getAddress());
                intent.putExtra("bookstore_ex", items.get(position).getEx());
                intent.putExtra("category", items.get(position).getCategory());
                intent.putExtra("IMG", items.get(position).getBitmapimg());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
