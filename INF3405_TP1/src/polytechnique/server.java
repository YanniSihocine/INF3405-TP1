package polytechnique;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Scanner;
import java.net.Socket;

public class Server {
    private static ServerSocket listener;

    public static void main(String[] arg) {
        Scanner scanneur = new Scanner(System.in);
        String adresseIPServeur;
        int portServeur;

        while (true) {
            System.out.print("Veuillez entrez l'adresse du serveur: ");
            adresseIPServeur = scanneur.nextLine();
            if (adresseIPValide(adresseIPServeur)) {
                break;
            }
            System.out.print("L'adresse IP du serveur est invalide, veuillez essayer à nouveau: ");
        }

        while (true) {
            System.out.print("Veuillez entrez le port du serveur (entre 5000 et 5050): ");
            portServeur = scanneur.nextInt();
            if (portServeur >= 5000 && portServeur <= 5050) {
                break;
            }
            System.out.print("Le port du serveur est invalide. ");
        }

        try {
            InetAddress IPServeur = InetAddress.getByName(adresseIPServeur);
            listener = new ServerSocket(portServeur);
            System.out.format("Le serveur est en cours d'exécution sur %s: %d%n", adresseIPServeur, portServeur);
            
            while (true) {
                Socket clientSocket = listener.accept(); // Accepte la connexion
                new ClientHandler(clientSocket).start(); // Gère le client dans un nouveau thread
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du démarrage du serveur : " + e.getMessage());
        } finally {
            try {
                listener.close();
            } catch (Exception e) {
                System.out.println("Erreur lors de la fermeture du serveur : " + e.getMessage());
            }
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
