import services.*;
import dao.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner saisie = new Scanner(System.in);
        RegistrationService registrationService = new RegistrationService();
        //LoginService loginService = new LoginService();

        CustomerDao customerDao = new CustomerDao();
        MerchantDao merchantDao = new MerchantDao();
        MenuAdminService menuAdminService = new MenuAdminService(customerDao, merchantDao);

        LoginService loginService = new LoginService(menuAdminService);


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


/*import adapter.OdsSource;
import adapter.OdsUserAccountAdapter;
import adapter.OdsAdminAdapter;
import dao.AdminDao;
import dao.CustomerDao;
import dao.MerchantDao;
import dao.UserAccountDao;
import models.Admin;
import models.UserAccount;
import services.LoginService;
import services.MenuAdminService;
import services.RegistrationService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner saisie = new Scanner(System.in);

        // ==============================================================
        // 1️⃣ IMPORT DES DONNÉES EXTERNES (UserAccount & Admin depuis .ods)
        // ==============================================================

        try {
            // --- Lecture du fichier des UserAccounts ---
            OdsSource userSource = new OdsSource("src/resources/USERACCOUNT.ods");
            OdsUserAccountAdapter userAdapter = new OdsUserAccountAdapter(userSource);
            List<UserAccount> accounts = userAdapter.readData();

            UserAccountDao userAccountDao = new UserAccountDao();

            for (UserAccount ua : accounts) {
                userAccountDao.createUser(ua);
                System.out.println("✅ UserAccount importé : " + ua.getLogin());
            }

            // --- Lecture du fichier des Admins ---
            OdsSource adminSource = new OdsSource("src/resources/ADMIN.ods");
            OdsAdminAdapter adminAdapter = new OdsAdminAdapter(adminSource, accounts);
            List<Admin> admins = adminAdapter.readData();

            AdminDao adminDao = new AdminDao();

            for (Admin admin : admins) {
                if (admin.getUserAccount() != null) {
                    adminDao.createAdmin(admin);
                    System.out.println("✅ Admin importé : " + admin.getFirstName() + " " + admin.getLastName());
                } else {
                    System.out.println("⚠ Admin ignoré (pas de UserAccount trouvé) : " + admin.getFirstName());
                }
            }

        } catch (Exception e) {
            System.out.println("⚠ Erreur lors de l'importation des données depuis les fichiers .ods : " + e.getMessage());
        }

        // ==============================================================
        // 2️⃣ INITIALISATION DES SERVICES
        // ==============================================================

        RegistrationService registrationService = new RegistrationService();
        CustomerDao customerDao = new CustomerDao();
        MerchantDao merchantDao = new MerchantDao();
        MenuAdminService menuAdminService = new MenuAdminService(customerDao, merchantDao);
        LoginService loginService = new LoginService(menuAdminService);

        // ==============================================================
        // 3️⃣ MENU PRINCIPAL INTERACTIF
        // ==============================================================

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
}*/

