import services.RegistrationService;
import services.LoginService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner saisie = new Scanner(System.in);
        RegistrationService registrationService = new RegistrationService();
        LoginService loginService = new LoginService();

        System.out.println("\t********************************************************************************************");
        System.out.println("\t*                               BIENVENUE SUR ADA-FINTECH                                  *");
        System.out.println("\t********************************************************************************************");

        while (true) {
            System.out.println("\n\t##########################################");
            System.out.println("\t#              MENU PRINCIPAL            #");
            System.out.println("\t##########################################");
            System.out.println("\t# 1. Inscription                         #");
            System.out.println("\t# 2. Connexion                           #");
            System.out.println("\t# 0. Quitter                             #");
            System.out.println("\t##########################################");
            System.out.print("Votre choix : ");

            int choix = saisie.nextInt();
            saisie.nextLine();

            switch (choix) {
                case 1:
                    registrationService.registerUser(saisie);
                    break;

                case 2:
                    loginService.login(saisie);
                    break;

                case 0:
                    System.out.println("Merci d’avoir utilisé ADA-FINTECH. À bientôt !");
                    saisie.close();
                    return;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }
}
