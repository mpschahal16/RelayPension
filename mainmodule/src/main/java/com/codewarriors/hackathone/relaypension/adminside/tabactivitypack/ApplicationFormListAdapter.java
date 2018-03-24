package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codewarriors.hackathone.relaypension.R;

import java.util.ArrayList;

    /**
     * Created by aishwarya on 21-03-2018.
     */

    public class ApplicationFormListAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<ApplicationFormListVAR> lvar;

        public ApplicationFormListAdapter(Context context, ArrayList<ApplicationFormListVAR> lvar) {

            this.context = context;
            this.lvar = lvar;
        }

        @Override
        public int getCount()
        {
            return lvar.size();
        }

        @Override
        public Object getItem(int i)
        {
            return lvar.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return 0;
        }

        static class ViewHolder
        {
            TextView tv,tv2,tv3,tv4;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v;
            if(view==null)
            {
                LayoutInflater inflater= LayoutInflater.from(context);
                v=inflater.inflate(R.layout.readyorqueuelistitem,null);
                final ViewHolder viewHolder=new ViewHolder();
                viewHolder.tv=v.findViewById(R.id.readynametv);
                viewHolder.tv2=v.findViewById(R.id.formnoreadytv);
                viewHolder.tv3=v.findViewById(R.id.constituencyreadytv);
                viewHolder.tv4=v.findViewById(R.id.agereadytv);
                v.setTag(viewHolder);
            }
            else
            {
                v=view;
            }
            ViewHolder holder=(ViewHolder) v.getTag();
            final ApplicationFormListVAR lv= lvar.get(i);
            holder.tv.setText(lv.getName());
            holder.tv2.setText(lv.getFno());
            holder.tv3.setText(lv.getConsti());
            holder.tv4.setText(lv.getAge());

            return v;
        }
    }

