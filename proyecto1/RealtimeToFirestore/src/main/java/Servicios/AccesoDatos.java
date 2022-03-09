package Servicios;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Modelo.Planta1;

public class AccesoDatos {
	public void conectar() throws IOException {
		// Fetch the service account key JSON file contents
		FileInputStream serviceAccount = new FileInputStream("./ClaveProyectoRealTime.json");

		// Initialize the app with a service account, granting admin privileges
		FirebaseOptions options = FirebaseOptions.builder()
		    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		    // The database URL depends on the location of the database
		    .setDatabaseUrl("https://plantas-db84a-default-rtdb.firebaseio.com")
		    .build();
		FirebaseApp firebaseApp=FirebaseApp.initializeApp(options,"secondary");
		System.out.println("Paso 1");
		// As an admin, the app has access to read and write all data, regardless of Security Rules
		DatabaseReference ref1 = FirebaseDatabase.getInstance(firebaseApp).getReference("Planta1");
		DatabaseReference ref2 = FirebaseDatabase.getInstance(firebaseApp).getReference("Planta2");
		DatabaseReference ref3 = FirebaseDatabase.getInstance(firebaseApp).getReference("Planta3");
		System.out.println("Paso 2");
		
		ref1.addValueEventListener( new ValueEventListener() {
		  @Override
		  public void onDataChange(DataSnapshot dataSnapshot) {
		    Planta1 planta1 = dataSnapshot.getValue(Planta1.class);
		    
		    if(planta1.getFinalizado()) {
		    	System.out.println("Pracica finalizada");
		    }
		    
		  }

		  @Override
		  public void onCancelled(DatabaseError error) {
			  
		  }
		});
		
		
	}
}

