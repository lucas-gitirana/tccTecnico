package lucas.gitirana.fumus.controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dao {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
}
