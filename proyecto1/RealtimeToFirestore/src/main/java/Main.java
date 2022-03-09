import java.io.IOException;

import Servicios.*;
public class Main {

	public static void main(String[] args) {
		
		try {
			new AccesoDatos().conectar();
			while(true) {
				int i=0;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
