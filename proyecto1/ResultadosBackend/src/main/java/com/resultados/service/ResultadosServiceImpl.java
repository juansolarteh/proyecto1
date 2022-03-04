package com.resultados.service;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.WriteResult;
import com.google.firestore.v1beta1.Document;
import com.csvreader.CsvWriter;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.resultados.commons.GenericServiceImpl;
import com.resultados.dto.PracticesDTO;
import com.resultados.model.Practices;

import io.grpc.netty.shaded.io.netty.channel.unix.Buffer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ResultadosServiceImpl  extends GenericServiceImpl<Practices, PracticesDTO> implements PracticesAPI{
	
	@Autowired
	private Firestore firestore;
	

	@Override
	public CollectionReference getCollection() {
		return firestore.collection("Practices");
	}
	
	
	public boolean reportarAnomalia(String idResultado,String anomalia) throws Exception {
		for(PracticesDTO practice : getAll()) {
			if(practice!=null) {
				if(practice.getId().compareTo(idResultado)==0) {
					Map<String, String> anomalias=practice.getAnomalias();
					anomalias.put(String.valueOf(practice.getNextAnomalyId()), anomalia);
						//Actualización en la base de datos
						DocumentReference docRef=firestore.collection("Practices").document(practice.getId());
						ApiFuture<WriteResult> future=docRef.update("anomalias",anomalias);
						future.get();
						int aux=practice.getNextAnomalyId();
						aux=aux+1;
						ApiFuture<WriteResult> futur=docRef.update("nextAnomalyId",aux);
						futur.get();
						return true;
				}
			}
		}
		return false;
	}
	public boolean addStudents(String idResultado, String idStudent) throws Exception {
		for(PracticesDTO practice : getAll()) {
			if(practice!=null) {
				if(practice.getId().compareTo(idResultado)==0) {
					Map<String, String> students=practice.getStudents();
					if(!students.containsKey(idStudent)) {
						students.put(idStudent, getNombreEstudiante(idStudent));
						//Actualización en la base de datos
						DocumentReference docRef=firestore.collection("Practices").document(practice.getId());
						ApiFuture<WriteResult> future=docRef.update("students",students);
						future.get();;
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean addAttendees(String idResultado, String idStudent) throws Exception {
		for(PracticesDTO practice : getAll()) {
			if(practice!=null) {
				if(practice.getId().compareTo(idResultado)==0) {
					Map<String, String> attendees=practice.getAttendees();
					if(!attendees.containsKey(idStudent)) {
						attendees.put(idStudent, getNombreEstudiante(idStudent));
						//Actualización en la base de datos
						DocumentReference docRef=firestore.collection("Practices").document(practice.getId());
						ApiFuture<WriteResult> future=docRef.update("attendees",attendees);
						future.get();;
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean addData(String idResultado, String variable, String value) throws Exception {
		for(PracticesDTO practice : getAll()) {
			if(practice!=null) {
				if(practice.getId().compareTo(idResultado)==0) {
					Map<String, String> data=practice.getData();
						data.put(variable, value);
						//Actualización en la base de datos
						DocumentReference docRef=firestore.collection("Practices").document(practice.getId());
						ApiFuture<WriteResult> future=docRef.update("data",data);
						future.get();;
						return true;
				}
			}
		}
		return false;
	}
	private String getNombreEstudiante(String idEstudiante) throws Exception {
		String result="";
		DocumentReference docRef = firestore.collection("Students").document(idEstudiante);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document=future.get();
		if(document.exists()) {
			String nombre=document.getData().get("name").toString();
			String apellido=document.getData().get("surname").toString();
			result=nombre.concat(" ");
			result=result.concat(apellido);
		}
		return result;
	}
	public String crearCSV(String idResultado) throws Exception {
		String nombreArchivo="./datos"+idResultado+".csv";
		try	{
			boolean existe=new File(nombreArchivo).exists();
			if(existe) {
				File archivoDatos=new  File(nombreArchivo);
				archivoDatos.delete();
			}
			//Crerar el archivo
			CsvWriter salidaCSV=new CsvWriter(new FileWriter(nombreArchivo, true), ';');
			
			//
			salidaCSV.write("Estudiantes: ");
			
			for(PracticesDTO practice : getAll()) {
				if(practice!=null) {
					if(practice.getId().compareTo(idResultado)==0) {
						for(String student: practice.getStudents().values()) {
							salidaCSV.write(student);
						}
						salidaCSV.endRecord();
						salidaCSV.write("Asistentes: ");
						
						for(String student: practice.getAttendees().values()) {
							salidaCSV.write(student);
						}
						salidaCSV.endRecord();
						break;
					}
				}
			}
			salidaCSV.close();
			subirArchivo("proyecto-1-94c3c", "proyecto-1-94c3c.appspot.com", idResultado+".csv", nombreArchivo);
			String destFilePath="./prueba"+idResultado+".csv";
			URL url=generateV4GetObjectSignedUrl("proyecto-1-94c3c", "proyecto-1-94c3c.appspot.com", idResultado+".csv");
			return url.toString();
			
		}catch (IOException e) {
			e.printStackTrace();
			return "Ocurrio un error";
		}
		
	}
	private void subirArchivo(String projectId, String bucketName, String objectName, String filePath) throws IOException {
		    // The ID of your GCP project
		    // String projectId = "your-project-id";

		    // The ID of your GCS bucket
		    // String bucketName = "your-unique-bucket-name";

		    // The ID of your GCS object
		    // String objectName = "your-object-name";

		    // The path to your file to upload
		    // String filePath = "path/to/your/file"
			StorageOptions storageOptions = StorageOptions.newBuilder()
			       .setProjectId("proyecto-1-94c3c")
			        .setCredentials(GoogleCredentials.fromStream(new 
			         FileInputStream("./ClaveProyecto.json"))).build();
			    Storage storage = storageOptions.getService();

		    //Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		    BlobId blobId = BlobId.of(bucketName, objectName);
		    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		    storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
		    storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

		    System.out.println(
		        "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
		  }
	


	  private  void descargarArchivo(String projectId, String bucketName, String objectName, String destFilePath)throws IOException {
	    // The ID of your GCP project
	    // String projectId = "your-project-id";

	    // The ID of your GCS bucket
	    // String bucketName = "your-unique-bucket-name";

	    // The ID of your GCS object
	    // String objectName = "your-object-name";

	    // The path to which the file should be downloaded
	    // String destFilePath = "/local/path/to/file.txt";
		  StorageOptions storageOptions = StorageOptions.newBuilder()
			       .setProjectId("proyecto-1-94c3c")
			        .setCredentials(GoogleCredentials.fromStream(new 
			         FileInputStream("./ClaveProyecto.json"))).build();
			    Storage storage = storageOptions.getService();


	    Blob blob = storage.get(BlobId.of(bucketName, objectName));
	    blob.downloadTo(Paths.get(destFilePath));

	    System.out.println(
	        "Downloaded object "
	            + objectName
	            + " from bucket name "
	            + bucketName
	            + " to "
	            + destFilePath);
	  }
	  private URL generateV4GetObjectSignedUrl(String projectId, String bucketName, String objectName) throws StorageException,IOException {
		    // String projectId = "my-project-id";
		    // String bucketName = "my-bucket";
		    // String objectName = "my-object";

		  StorageOptions storageOptions = StorageOptions.newBuilder()
			       .setProjectId("proyecto-1-94c3c")
			        .setCredentials(GoogleCredentials.fromStream(new 
			         FileInputStream("./ClaveProyecto.json"))).build();
			    Storage storage = storageOptions.getService();

		    // Define resource
		    BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();

		    URL url =storage.signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());

		    System.out.println("Generated GET signed URL:");
		    System.out.println(url);
		    System.out.println("You can use this URL with any user agent, for example:");
		    System.out.println("curl '" + url + "'");
		    return url;
		  }
	
}
