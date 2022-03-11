package Servicios;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Modelo.Planta1;
import Modelo.Planta2;
import Modelo.Planta3;

public class AccesoDatos {
	public AccesoDatos() {
		try {
			firestore();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private Firestore firestore;
	public Firestore firestore() throws Exception{

		FileInputStream serviceAccount =
		  new FileInputStream("./ClaveProyecto.json");
		
		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		  .build();
		
		FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
		firestore=FirestoreClient.getFirestore(firebaseApp);
		return firestore;
	}
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
	
		// As an admin, the app has access to read and write all data, regardless of Security Rules
		DatabaseReference ref1 = FirebaseDatabase.getInstance(firebaseApp).getReference("Planta1");
		DatabaseReference ref2 = FirebaseDatabase.getInstance(firebaseApp).getReference("Planta2");
		DatabaseReference ref3 = FirebaseDatabase.getInstance(firebaseApp).getReference("Planta3");

		
		ref1.addValueEventListener( new ValueEventListener() {
		  @Override
		  public void onDataChange(DataSnapshot dataSnapshot) {
		    Planta1 planta1 = dataSnapshot.getValue(Planta1.class);
		    if(planta1.getFinalizado()) {
		    	try {
					String idPractice=ConsultarId("Planta1");
					migrarValoresPlanta1(idPractice, planta1);
					DatabaseReference usersRef = ref1.child("finalizado");
					usersRef.setValueAsync(Boolean.FALSE);
				} catch (InterruptedException | ExecutionException e) {
					System.out.println(e.getMessage());
				}
		    }
		  }
		  @Override
		  public void onCancelled(DatabaseError error) {
			  
		  }
		});
		ref2.addValueEventListener( new ValueEventListener() {
			  @Override
			  public void onDataChange(DataSnapshot dataSnapshot) {
			    Planta2 planta2 = dataSnapshot.getValue(Planta2.class);
			    
			    if(planta2.getFinalizado()) {
			    	try {
						String idPractice=ConsultarId("Planta2");
						migrarValoresPlanta2(idPractice, planta2);
						DatabaseReference usersRef = ref2.child("finalizado");
						usersRef.setValueAsync(Boolean.FALSE);
					} catch (InterruptedException | ExecutionException e) {
						System.out.println(e.getMessage());
					}
			    }
			  }
			  @Override
			  public void onCancelled(DatabaseError error) {
				  
			  }
			});
		ref3.addValueEventListener( new ValueEventListener() {
			  @Override
			  public void onDataChange(DataSnapshot dataSnapshot) {
				  Planta3 planta3=new Planta3();
				  try {
					  planta3 = dataSnapshot.getValue(Planta3.class);
					    System.out.println("Finalizar: "+planta3.getFinalizar());
				} catch (Exception e) {
					System.out.println("Exception: "+e.getMessage());
				}
			    
			    if(planta3.getFinalizar()) {
			    	try {
						String idPractice=ConsultarId("Planta3");
						migrarValoresPlanta3(idPractice, planta3);
						DatabaseReference usersRef = ref3.child("finalizado");
						usersRef.setValueAsync(Boolean.FALSE);
					} catch (InterruptedException | ExecutionException e) {
						System.out.println(e.getMessage());
					}
			    }
			  }
			  @Override
			  public void onCancelled(DatabaseError error) {
				  
			  }
			});
	}
	private String ConsultarId(String prmPlanta) throws InterruptedException, ExecutionException  {
		String result="";
		DocumentReference docRef = firestore.collection("InExecution").document("BCcz7EchFcDdTQeD1edo");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document=future.get();
		if(document.exists()) {
			result=document.getData().get(prmPlanta).toString();
		}
		System.out.println("Id: "+result);
		return result;
	}
	private void migrarValoresPlanta1(String idPractice, Planta1 objPlanta1) throws InterruptedException, ExecutionException {
		Map<String, Map<String, Float>> data=new HashMap<String, Map<String,Float>>();
		data.put("Elongaciones", objPlanta1.elongaciones);
		data.put("Pesos", objPlanta1.pesos);
		DocumentReference docRef = firestore.collection("Practices").document(idPractice);
		ApiFuture<WriteResult> future=docRef.update("data",data);
		future.get();;
		
	}
	private void migrarValoresPlanta2(String idPractice, Planta2 objPlanta2) throws InterruptedException, ExecutionException {
		Map<String, Map<String, Float>> data=new HashMap<String, Map<String,Float>>();
		data.put("Errores", objPlanta2.getErrores());
		data.put("GravedadN", objPlanta2.getGravedadN());
		data.put("Tiempo", objPlanta2.getTiempo());
		DocumentReference docRef = firestore.collection("Practices").document(idPractice);
		ApiFuture<WriteResult> future=docRef.update("data",data);
		future.get();;
	}
	private void migrarValoresPlanta3(String idPractice, Planta3 objPlanta3) throws InterruptedException, ExecutionException {
		Map<String, Map<String, Float>> data=new HashMap<String, Map<String,Float>>();
		Map<String, Float> datos_x=new HashMap<>();
		int i=0;
		for (Float value : objPlanta3.getDatos_x()) {
			datos_x.put(Integer.toString(i), value);
			i++;
		}
		Map<String, Float> datos_y=new HashMap<>();
		i=0;
		for (Float value : objPlanta3.getDatos_y()) {
			datos_y.put(Integer.toString(i), value);
			i++;
		}
		data.put("datos_x", datos_x);
		data.put("datos_y", datos_y);
		DocumentReference docRef = firestore.collection("Practices").document(idPractice);
		ApiFuture<WriteResult> future=docRef.update("data",data);
		future.get();;
		Map<String, String> variables=new HashMap<String,String>();
		variables.put("posicion_X", objPlanta3.getPosicion_X());
		variables.put("posicion_Y", objPlanta3.getPosicion_Y());
		variables.put("tiempo", objPlanta3.getTiempo());
		variables.put("url_imagen", objPlanta3.getUrl_imagen());
		DocumentReference docRef1 = firestore.collection("Practices").document(idPractice);
		ApiFuture<WriteResult> future1=docRef1.update("variables",variables);
		future1.get();;
	}
}

