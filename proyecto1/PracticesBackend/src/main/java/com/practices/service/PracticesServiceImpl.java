package com.practices.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csvreader.CsvWriter;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.practices.commons.GenericServiceImpl;
import com.practices.dto.PracticesDTO;
import com.practices.dto.SendPracticesDTO;
import com.practices.model.Practices;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;

@Service
public class PracticesServiceImpl  extends GenericServiceImpl<Practices, PracticesDTO> implements PracticesServiceAPI{ 
	
	@Autowired
	private Firestore firestore;

	@Override
	public CollectionReference getCollection() {
		return firestore.collection("Practices");
	}
	
	@Override
	public List<SendPracticesDTO> getByStudent(String idStudent) throws Exception {
		List<PracticesDTO> lista = getFromMapByKey("students", idStudent);
		List<SendPracticesDTO> listaAux = convertirLista(lista);
		return listaAux;
	}
	
	@Override
	public List<String> getDatesByTopic(String idTopic) throws Exception {
		List<String> dates = new ArrayList<String>();
		SimpleDateFormat formato1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formato2 = new SimpleDateFormat("HH:mm:ss");
		List<PracticesDTO> practices = getListByFieldPath("topic_id", idTopic);
		for (PracticesDTO practice : practices) {
			String fechaCompleta = formato1.format(practice.getStart())+"T"+formato2.format(practice.getStart());
			dates.add(fechaCompleta);
			System.out.println(practice.getStart());
		}
		return dates;
	}

	@Override
	public boolean reportarAnomalia(String idResultado, String anomalia) throws Exception {
		for(PracticesDTO practice : getAll()) {
			if(practice!=null) {
				if(practice.getId().compareTo(idResultado)==0) {
					Map<String, String> anomalias=practice.getAnomalias();
					anomalias.put(String.valueOf(practice.getNext_anomaly_id()), anomalia);
						//Actualización en la base de datos
						DocumentReference docRef=firestore.collection("Practices").document(practice.getId());
						ApiFuture<WriteResult> future=docRef.update("anomalias",anomalias);
						future.get();
						int aux=practice.getNext_anomaly_id();
						aux=aux+1;
						ApiFuture<WriteResult> futur=docRef.update("nextAnomalyId",aux);
						futur.get();
						return true;
				}
			}
		}
		return false;
	}

	@Override
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

	@Override
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


	@Override
	public String crearCSV(String idResultado) throws Exception {
		String nombreArchivo="./"+idResultado+".csv";
		
		String id_topic;
		try	{
			
			//Crerar el archivo
			CsvWriter salidaCSV=new CsvWriter(new FileWriter(nombreArchivo, true), ';');
			
			//
			salidaCSV.write("*****************RESULTADOS*******************");
			salidaCSV.endRecord();
			salidaCSV.write("Estudiantes: ");
			
			for(PracticesDTO practice : getAll()) {
				if(practice!=null) {
					if(practice.getId().compareTo(idResultado)==0) {
						id_topic=practice.getTopic_id();
						String topic=getTopic(id_topic);
						System.out.println("Topic: "+id_topic);
						for(String student: practice.getStudents().values()) {
							salidaCSV.write(student);
						}
						salidaCSV.endRecord();
						salidaCSV.write("Asistentes: ");
						
						for(String student: practice.getAttendees().values()) {
							salidaCSV.write(student);
						}
						salidaCSV.endRecord();
						SimpleDateFormat formato1 = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat formato2 = new SimpleDateFormat("HH:mm:ss");
						String fechaInicio = formato1.format(practice.getStart())+"  "+formato2.format(practice.getStart());
						String fechaFin = formato1.format(practice.getEnd())+"  "+formato2.format(practice.getEnd());
						salidaCSV.write("Fecha Y Hora Inicio: ");
						salidaCSV.write(fechaInicio);
						salidaCSV.endRecord();
						salidaCSV.write("Fecha y Hora Fin: ");
						salidaCSV.write(fechaFin);
						salidaCSV.endRecord();
						if(topic.compareTo("Ley de Hook")==0) {
							System.out.println("Entro Ley de Hook");
							salidaCSV.write("Planta usada: ");
							salidaCSV.write("Ley Hook");
							salidaCSV.endRecord();
							salidaCSV.write("***********Elongaciones***************");
							salidaCSV.endRecord();
							for(String key : practice.getData().get("Elongaciones").keySet()) {
								salidaCSV.write(key);
								salidaCSV.write(practice.getData().get("Elongaciones").get(key).toString());
								salidaCSV.endRecord();
							}
							salidaCSV.write("*************Pesos***********************");
							salidaCSV.endRecord();
							for(String key : practice.getData().get("Pesos").keySet()) {
								salidaCSV.write(key);
								salidaCSV.write(practice.getData().get("Pesos").get(key).toString());
								salidaCSV.endRecord();
							}
						}else if(topic.compareTo("Caida Libre")==0) {
							System.out.println("Caida de Libre");
							salidaCSV.write("Planta usada: ");
							salidaCSV.write("Caida Libre");
							salidaCSV.endRecord();
							salidaCSV.write("***********Errores***************");
							salidaCSV.endRecord();
							for(String key : practice.getData().get("Errores").keySet()) {
								salidaCSV.write(key);
								salidaCSV.write(practice.getData().get("Errores").get(key).toString());
								salidaCSV.endRecord();
							}
							salidaCSV.write("*************GravedadN***********************");
							salidaCSV.endRecord();
							for(String key : practice.getData().get("GravedadN").keySet()) {
								salidaCSV.write(key);
								salidaCSV.write(practice.getData().get("GravedadN").get(key).toString());
								salidaCSV.endRecord();
							}
							salidaCSV.write("*************Tiempo***********************");
							salidaCSV.endRecord();
							for(String key : practice.getData().get("Tiempo").keySet()) {
								salidaCSV.write(key);
								salidaCSV.write(practice.getData().get("Tiempo").get(key).toString());
								salidaCSV.endRecord();
							}
						}else {
							System.out.println("Lanzamiento Parabolico");
							salidaCSV.write("Planta usada: ");
							salidaCSV.write("Lanzamiento Parabolico");
							salidaCSV.endRecord();
							salidaCSV.write("***********Datos_x***************");
							salidaCSV.endRecord();
							for(String key : practice.getData().get("datos_x").keySet()) {
								salidaCSV.write(key);
								salidaCSV.write(practice.getData().get("datos_x").get(key).toString());
								salidaCSV.endRecord();
							}
							salidaCSV.write("*************Datos_y***********************");
							salidaCSV.endRecord();
							for(String key : practice.getData().get("datos_y").keySet()) {
								salidaCSV.write(key);
								salidaCSV.write(practice.getData().get("datos_y").get(key).toString());
								salidaCSV.endRecord();
							}
							salidaCSV.write("*************Variables***********************");
							salidaCSV.endRecord();
							salidaCSV.write("Posicion_x: ");
							salidaCSV.write(practice.getVariables().get("posicion_X"));
							salidaCSV.endRecord();
							salidaCSV.write("Posicion_y: ");
							salidaCSV.write(practice.getVariables().get("posicion_Y"));
							salidaCSV.endRecord();
							salidaCSV.write("Tiempo: ");
							salidaCSV.write(practice.getVariables().get("tiempo"));
							salidaCSV.endRecord();
							salidaCSV.write("url_imagen: ");
							salidaCSV.write(practice.getVariables().get("url_imagen"));
							salidaCSV.endRecord();
						}
						break;
					}
				}
			}
			salidaCSV.close();
			subirArchivo("proyecto-1-94c3c", "proyecto-1-94c3c.appspot.com", idResultado+".csv", nombreArchivo);
			URL url=generateV4GetObjectSignedUrl("proyecto-1-94c3c", "proyecto-1-94c3c.appspot.com", idResultado+".csv");
			boolean existe=new File(nombreArchivo).exists();
			if(existe) {
				File archivoDatos=new  File(nombreArchivo);
				archivoDatos.delete();
			}
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
		       .setProjectId(projectId)
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
	private String getTopic(String id_topic) throws InterruptedException, ExecutionException {
		String result="";
		DocumentReference docRef = firestore.collection("Topics").document(id_topic);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document=future.get();
		if(document.exists()) {
			result=document.getData().get("name").toString();
		}
		return result;
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
	
	private List<SendPracticesDTO> convertirLista(List<PracticesDTO> lista){
		List<SendPracticesDTO> listAux = new ArrayList<>();
		for (int i = 0; i < lista.size(); i++) {
			SendPracticesDTO practices = new SendPracticesDTO();
			practices = convertirObjeto(lista.get(i));
			listAux.add(practices);
		}
		return listAux;
		
	}
	@Override
	public void practicaEnEjecucion(String planta, String idPractice) throws InterruptedException, ExecutionException {
		DocumentReference docRef=firestore.collection("InExecution").document("BCcz7EchFcDdTQeD1edo");
		ApiFuture<WriteResult> future=docRef.update(planta,idPractice);
		future.get();;
	}
	@Override
	public Map<String, Map<String, Float>> getDataPractice(String idPractice) throws Exception{
		for(PracticesDTO practice : getAll()) {
			if(practice!=null) {
				if(practice.getId().compareTo(idPractice)==0) {
					return practice.getData();
				}
			}
		}
		return null;
	}

	@Override
	public SendPracticesDTO scheduledPractice(String idStudent, String idWorkshop) throws Exception {
		SendPracticesDTO schedule = new SendPracticesDTO();
		List<PracticesDTO> lista = getFromMapByKey("students", idStudent);
		
		for(PracticesDTO practice : lista) {
			System.out.println(practice.getLeaderName());
			System.out.println(practice.getWorkshop_id());
			System.out.println(idWorkshop);
			String id = practice.getWorkshop_id();
			if(id.equals(idWorkshop)) {
				//schedule = convertirObjeto(practice);
				
				System.out.println(schedule.getLeaderName());
				return schedule;
			}
		}
		return null;
	}
	
	private SendPracticesDTO convertirObjeto(PracticesDTO practice){
		
		SimpleDateFormat formato1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formato2 = new SimpleDateFormat("HH:mm:ss");
		
		SendPracticesDTO schedule = new SendPracticesDTO();
		schedule.setTopic_id(practice.getTopic_id());
		schedule.setId(practice.getId());
		schedule.setWorkshop_id(practice.getWorkshop_id());
		schedule.setLeader_id(practice.getLeader_id());
		schedule.setStudents(practice.getStudents());
		schedule.setAttendees(practice.getAttendees());
		schedule.setData(practice.getData());
		schedule.setAnomalias(practice.getAnomalias());
		schedule.setLeaderName(practice.getLeaderName());
		schedule.setPracticeName(practice.getPracticeName());
		schedule.setNextAnomalyId(practice.getNext_anomaly_id());
		String fechaInicio = formato1.format(practice.getStart())+"T"+formato2.format(practice.getStart());
		String fechaFin = formato1.format(practice.getEnd())+"T"+formato2.format(practice.getEnd());
		schedule.setStart(fechaInicio);
		schedule.setEnd(fechaFin);
		
		return schedule;
	}
	
}
