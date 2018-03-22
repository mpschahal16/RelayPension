package com.codewarriors.hackathone.relaypension.adminside;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.adminside.tabactivitypack.ApplicationFormListAdapter;
import com.codewarriors.hackathone.relaypension.adminside.tabactivitypack.ApplicationFormListVAR;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_review extends AppCompatActivity {
    DatabaseReference rootreference= FirebaseDatabase.getInstance().getReference();
    ListView lv;
    ArrayList<ApplicationFormListVAR> listtodisplay;
    ApplicationFormListAdapter applicationFormListAdapter;
    ApplicationFormListVAR applicationFormListVAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_review);
        lv=findViewById(R.id.lvdetail);
        listtodisplay=new ArrayList<>();

        rootreference.child("consituency").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    listtodisplay.clear();

                    for(DataSnapshot dataSnapshotchild:dataSnapshot.getChildren())
                    {
                       /* String name=dataSnapshotchild.getKey();
                        String consti=dataSnapshotchild.getKey();
                        int age=Integer.parseInt(dataSnapshotchild.getKey());
                        int fno=Integer.parseInt(dataSnapshotchild.getKey());
                        ApplicationFormListVAR var=new ApplicationFormListVAR(name,age,consti,fno);
                        listtodisplay.add(var);*/
                    }
                    applicationFormListAdapter =new ApplicationFormListAdapter(getApplicationContext(),listtodisplay);
                    lv.setAdapter(applicationFormListAdapter);
                    applicationFormListAdapter.notifyDataSetChanged();

    }
}
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"ERROR"+databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

}


