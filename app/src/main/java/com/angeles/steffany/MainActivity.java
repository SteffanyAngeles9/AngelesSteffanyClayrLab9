package com.angeles.steffany;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference details;

    EditText eFullname, eAge, eGender;
    TextView Fullname, Age, Gender;

    int index, in;
    ArrayList<String> keyList,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        details = db.getReference("details");
        keyList = new ArrayList<>();
        name = new ArrayList<>();

        eFullname = findViewById(R.id.eFName);
        eAge = findViewById(R.id.eAge);
        eGender = findViewById(R.id.eGender);

        Fullname = findViewById(R.id.fullname);
        Age = findViewById(R.id.age);
        Gender = findViewById(R.id.gender);
    }

    @Override
    protected void onStart() {
        super.onStart();
        details.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ss: dataSnapshot.getChildren()) {
                    keyList.add(ss.getKey());
                }

//                for(int i = 0; i < keyList.size(); i++) {
//                    Person p = dataSnapshot.child(keyList.get(i)).getValue(Person.class);
//                    name.add(p.getFullName());
//                    //keyList.add(ss.getKey());
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void save(View v){
        if(v.getId() == R.id.saveButton){
            String fullName1,  gender1;
            Boolean isOk = true;
            int age1;

            try{
                fullName1 = eFullname.getText().toString();
                age1 = Integer.parseInt(eAge.getText().toString().trim());
                gender1 = eGender.getText().toString();

//                for(int i =0; i < name.size(); i++){
//                    if(name.get(i).equals(fullName1)){
//                        isOk = false;
//                    }
//                }

                if(isOk == true){
                    String key = details.push().getKey();
                    Person pers = new Person(fullName1, age1, gender1);
                    details.child(key).setValue(pers);
                    keyList.add(key);
                    Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Full Name already exist", Toast.LENGTH_LONG).show();
                }

            }catch(Exception e){
                Toast.makeText(this, "Cannot put null values", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void search(View v){
        details.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                index = (int) dataSnapshot.getChildrenCount() - 1;
                Fullname.setText(dataSnapshot.child(keyList.get(index)).child("fullName").getValue().toString());
                Age.setText(dataSnapshot.child(keyList.get(index)).child("age").getValue().toString());
                Gender.setText(dataSnapshot.child(keyList.get(index)).child("gender").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
