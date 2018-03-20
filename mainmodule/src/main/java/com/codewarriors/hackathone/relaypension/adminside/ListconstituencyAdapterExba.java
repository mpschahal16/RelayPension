package com.codewarriors.hackathone.relaypension.adminside;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codewarriors.hackathone.relaypension.R;

import java.util.ArrayList;

/**
 * Created by hp on 21-03-2018.
 */

public class ListconstituencyAdapterExba extends BaseAdapter {

    private Activity activity;
    private ArrayList<ListConstituencyVAR> listConstituencyVARSlist;

    public ListconstituencyAdapterExba(Activity activity, ArrayList<ListConstituencyVAR> listConstituencyVARSlist) {
        this.activity = activity;
        this.listConstituencyVARSlist = listConstituencyVARSlist;
    }

    @Override
    public int getCount() {
        return listConstituencyVARSlist.size();
    }

    @Override
    public Object getItem(int i) {
        return listConstituencyVARSlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder
    {
        TextView constituencytv,inreadytv,inqueuelv,maxlimitlv;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        if(view==null)
        {
            LayoutInflater inflater=activity.getLayoutInflater();
            v=inflater.inflate(R.layout.customconstituency_listitem,null);
            final ViewHolder viewHolder=new ViewHolder();
            viewHolder.constituencytv=v.findViewById(R.id.consnamelistitemtv);
            viewHolder.inreadytv=v.findViewById(R.id.inreadycountcustomtv);
            viewHolder.inqueuelv=v.findViewById(R.id.inqueuetv);
            viewHolder.maxlimitlv=v.findViewById(R.id.maxlimittv);

            v.setTag(viewHolder);

        }
        else
        {
            v=view;
        }
        ViewHolder holder=(ViewHolder) v.getTag();
        final ListConstituencyVAR listConstituencyVAR=listConstituencyVARSlist.get(i);
        holder.constituencytv.setText(listConstituencyVAR.getConstituencyname());
        holder.inreadytv.setText(listConstituencyVAR.getNooformin_ready().toString());
        holder.maxlimitlv.setText(listConstituencyVAR.getMaxlimit()+"");
        holder.inqueuelv.setText(listConstituencyVAR.getNooformin_queue().toString());
        return v;
    }
}
