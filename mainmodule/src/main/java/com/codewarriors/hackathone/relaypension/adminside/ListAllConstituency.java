package com.codewarriors.hackathone.relaypension.adminside;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.ConstituencyHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListAllConstituency extends AppCompatActivity {

    DatabaseReference rootreference= FirebaseDatabase.getInstance().getReference();
    ListView listallconstit;

    ArrayList<ListConstituencyVAR> listtodisplay;


    ListconstituencyAdapterExba listconstituencyAdapterExba;
    ListConstituencyVAR listConstituencyVAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_constituency);
        listallconstit=findViewById(R.id.listviewallconstituency);


        listtodisplay=new ArrayList<>();











        rootreference.child("consituency").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    ConstituencyHelperClass constituencyHelperClass=new ConstituencyHelperClass();

                    for(DataSnapshot dataSnapshotchild:dataSnapshot.getChildren())
                    {
                        String constituencyname=dataSnapshotchild.getKey();
                        Long nooformin_ready=dataSnapshotchild.child("ready").getChildrenCount();
                        Long nooformin_queue=dataSnapshotchild.child("queue").getChildrenCount();
                        ListConstituencyVAR temp=new ListConstituencyVAR(constituencyname,nooformin_ready,nooformin_queue,constituencyHelperClass.getlimit(constituencyname));
                        listtodisplay.add(temp);

                        Log.d("msg",constituencyname+"/"+nooformin_ready+"/"+nooformin_queue);

                    }
                  //  listconstituencyAdapterExba=new ListconstituencyAdapterExba((ListAllConstituency)g,listtodisplay);
                    listallconstit.setAdapter(listconstituencyAdapterExba);
                    listconstituencyAdapterExba.notifyDataSetChanged();

                   // setall();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void setall() {
        for (ListConstituencyVAR temp:listtodisplay) {
            listConstituencyVAR = new ListConstituencyVAR(temp.getConstituencyname(),temp.nooformin_ready,temp.getNooformin_queue(),temp.getMaxlimit());
        }
    }
}
