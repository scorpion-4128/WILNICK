package com.nusantara.id.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nusantara.id.R;
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

public class PromoAdapter extends ArrayAdapter<JSONObject> {

    private int spinner_id;
private ImageView hn;
	private ImageView im;
	private Animation up;

    public PromoAdapter(Context context, int spinner_id, ArrayList<JSONObject> list) {
        super(context, R.layout.promo_item, list);
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
		View v = LayoutInflater.from(getContext()).inflate(R.layout.promo_item, parent, false);
		RelativeLayout promo_item_layout = v.findViewById(R.id.haha);
		TextView tv = v.findViewById(R.id.itemName);
	    hn = (ImageView) v.findViewById(R.id.hn);
        TextView extra = v.findViewById(R.id.textExtra);
        TextView pInfos = v.findViewById(R.id.payload_info);
		ImageView im = v.findViewById(R.id.pImages);
		promo_item_layout.setAnimation(anim);
		tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		tv.setSelected(true);
		pInfos.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		pInfos.setSelected(true);

        try {
			String name = getItem(position).getString("Name");
            tv.setText(name);


			if (spinner_id == R.id.payloadSpinner) {
				getPayloadIcon(position, im, extra, pInfos);
				pInfos.setText(getItem(position).getString("pInfo"));
				String qwerty = getItem(position).getString("pInfo").toLowerCase();
				if (qwerty.contains("")) {
                    extra.setText(".");
                    
       
                    
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		v.setAnimation(up);
        return v;
		

		
		
    }


    private void getPayloadIcon(int position, ImageView im, TextView extra, TextView pInfos) throws Exception {
        String name = getItem(position).getString("Name").toLowerCase();
		GradientDrawable var1 = new GradientDrawable();
hn.setBackgroundDrawable(var1);
		var1.setCornerRadius((float)30);
		var1.setOrientation(Orientation.RIGHT_LEFT);
		var1.setStroke(2, Color.parseColor("#ffffff"));
        if (name.contains("digicel")) {
            im.setImageResource(R.drawable.ic_digicel);
			var1.setColors(new int[]{Color.parseColor("#3F67D7"), Color.parseColor("#24B7FC")});	
        } else if (name.contains("natcom")) {
            im.setImageResource(R.drawable.ic_natcom);
			var1.setColors(new int[]{Color.parseColor("#86D71F"), Color.parseColor("#20FCA5")});
        } else if (name.contains("axis")) {
            im.setImageResource(R.drawable.ic_axis);
			var1.setColors(new int[]{Color.parseColor("#3F67D7"), Color.parseColor("#FC2701")});
        } else if (name.contains("indosat")) {
            im.setImageResource(R.drawable.ic_indosat);
			var1.setColors(new int[]{Color.parseColor("#FC2701"), Color.parseColor("#FC7506")});
        } else if (name.contains("smartfren")) {
            im.setImageResource(R.drawable.ic_smartfren);
			var1.setColors(new int[]{Color.parseColor("#FC7506"), Color.parseColor("#FC990E")});
        }else if(name.contains("three")) {
            im.setImageResource(R.drawable.ic_three);
			var1.setColors(new int[]{Color.parseColor("#FCF30C"), Color.parseColor("#E7FC04")});
        }else if(name.contains("")) {
            im.setImageResource(R.drawable.nusantara);
			var1.setColors(new int[]{Color.parseColor("#8DC7FC"), Color.parseColor("#3C94FC")});
        }
		
	
    }


}
