package com.nusantara.id.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nusantara.id.R;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONObject;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import com.nusantara.id.NusantaraApp;
import android.widget.LinearLayout;
import android.text.TextUtils;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.*;
import android.graphics.Color;
import android.widget.RelativeLayout;
public class SpinnerAdapter extends ArrayAdapter<JSONObject> {

    private int spinner_id;
	private Animation up;

    public SpinnerAdapter(Context context, int spinner_id, ArrayList<JSONObject> list) {
        super(context, R.layout.spinner_item, list);
        this.spinner_id = spinner_id;
    }

    @Override
    public JSONObject getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return view(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return view(position, convertView, parent);
    }

    private View view(int position, View convertView, ViewGroup parent) {

		
		Animation anim = AnimationUtils.loadAnimation(NusantaraApp.getApp(), R.anim.grow);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
		RelativeLayout promo_item_layout = v.findViewById(R.id.haha);
		TextView tv = v.findViewById(R.id.itemName);
        TextView info = v.findViewById(R.id.info);
        ImageView im = v.findViewById(R.id.itemImage);
		ImageView hn = v.findViewById(R.id.hn);
		promo_item_layout.setAnimation(anim);
	tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		tv.setSelected(true);
		info.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		info.setSelected(true);
		GradientDrawable var1 = new GradientDrawable();
		hn.setBackgroundDrawable(var1);
		var1.setColor(NusantaraApp.getApp().getResources().getColor(R.color.colorPrimary));
		var1.setCornerRadius((float)30);
		var1.setOrientation(Orientation.RIGHT_LEFT);
		var1.setStroke(2, Color.parseColor("#ffffff"));
		        try {

            String name = getItem(position).getString("Name");
            tv. setText(name);


            if (spinner_id == R.id.serverSpinner) {
                getServerIcon(position, im, info);
				info.setText(getItem(position).getString("sInfo"));      

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	
        return v;
	
    }

    private void getServerIcon(int position, ImageView im, TextView info) throws Exception {
        InputStream inputStream = getContext().getAssets().open("flags/" + getItem(position).getString("FLAG"));
        im.setImageDrawable(Drawable.createFromStream(inputStream, getItem(position).getString("FLAG")));
        if (inputStream != null) {
            inputStream.close();
        }
    }
}
