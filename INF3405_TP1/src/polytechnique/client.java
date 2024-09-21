package polytechnique;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client{
	private static Socket socket;
	
	public static void main(String[] arg){
		Scanner scanneur = new Scanner(System.in);
		String adresseIPServeur;
		int portServeur;
		
		while (true) {
			System.out.print("Veuillez entrez l'adresse du serveur: ");
			adresseIPServeur = scanneur.nextLine();
			if (adresseIPValide(adresseIPServeur)) {
				break;
			}
			System.out.print("L'adresse IP du serveur est invalide. ");
		}
		
		while (true) {
			System.out.print("Veuillez entrez le port du serveur (entre 5000 et 5050): ");
			portServeur = scanneur.nextInt();
			if (portServeur >= 5000 && portServeur <= 5050) {
				break;
			}
			System.out.print("Le port du serveur est invalide, veuillez essayer à nouveau: ");
		}
		

		try {
			socket = new Socket(adresseIPServeur, portServeur);
			System.out.format("Connexion au serveur [%s:%d]%n", adresseIPServeur, portServeur);

			DataInputStream in = new DataInputStream(socket.getInputStream());
    	String messageFromServer = in.readUTF();
    	System.out.println("Message du serveur : " + messageFromServer);

    	socket.close();
		} catch (Exception e) {
			System.out.println("Erreur lors de la connexion : " + e.getMessage());
		}
	}
	
	private static boolean adresseIPValide(String adresseIPServeur) {
		String[] partiesIP = adresseIPServeur.split("\\.");
	    if (partiesIP.length != 4) {
	        return false;
	    }
	    for (String partie : partiesIP) {
	        try {
	            int num = Integer.parseInt(partie);
	            if (num < 0 || num > 255) {
	                return false;
	            }
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    }
	    return true;
	}
}