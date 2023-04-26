package com.inn.AttendanceAPI.serviceImpl;


import com.inn.AttendanceAPI.firebase.FirebaseInitializer;
import com.inn.AttendanceAPI.model.Scan;
import com.inn.AttendanceAPI.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

@Service
public class ScanServiceImpl implements ScanService {

    @Autowired
    private FirebaseInitializer db;

    @Override
    public ResponseEntity<List<Scan>> getAllScans() throws ExecutionException, InterruptedException {
        List<Scan> scanList = new ArrayList<Scan>();
        CollectionReference scanRef = db.getFirebase().collection("Scan");
        ApiFuture<QuerySnapshot> querySnapshot = scanRef.get();
        for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
            Scan scan = doc.toObject(Scan.class);
            scanList.add(scan);
        }
        return new ResponseEntity<>(scanList, HttpStatus.OK);
    }

}

