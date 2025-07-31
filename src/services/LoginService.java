package services;

import dao.UserAccountDao;
import dao.AdminDao;
import dao.CustomerDao;
import dao.MerchantDao;

import models.*;
import java.util.Scanner;

public class LoginService {

    private final UserAccountDao userAccountDao = new UserAccountDao();
    private final AdminDao adminDao = new AdminDao();
    private final CustomerDao customerDao = new CustomerDao();
    private final MerchantDao merchantDao = new MerchantDao();

    private final MenuAdminService menuAdminService;

    // ✅ Injection du service dans le constructeur
    public LoginService(MenuAdminService menuAdminService) {
        this.menuAdminService = menuAdminService;
    }

    public void login(Scanner scanner) {
        System.out.print("Login : ");
        String login = scanner.nextLine().trim();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine().trim();

        // Recherche du compte utilisateur en base
        UserAccount userAccount = userAccountDao.findByLoginAndPassword(login, password);

        if (userAccount == null) {
            System.out.println("❌ Identifiant ou mot de passe incorrect !");
            return;
        }

        System.out.println("✅ Connexion réussie !");
        System.out.println("Type de compte : " + userAccount.getCompteType());

        // En fonction du type de compte, récupérer et afficher l’utilisateur complet
        switch (userAccount.getCompteType()) {
            case ADMIN -> {
                Admin admin = new Admin();
                admin.setUserAccount(userAccount);
                adminDao.selectAdmin(admin);
                System.out.println("Bienvenue ADMIN : " + admin.getFirstName());

                // ✅ Appel direct du menu admin
                menuAdminService.afficherMenuAdmin(scanner);
            }
            case CUSTOMER -> {
                Customer customer = new Customer();
                customer.setUserAccount(userAccount);
                customerDao.selectCustomer(customer);
                System.out.println("Bienvenue CLIENT : " + customer.getFirstName());
            }
            case MERCHANT -> {
                Merchant merchant = new Merchant();
                merchant.setUserAccount(userAccount);
                merchantDao.selectMerchant(merchant);
                System.out.println("Bienvenue MARCHAND : " + merchant.afficherInfo());
            }
            default -> System.out.println("Type de compte inconnu !");
        }
    }
}
