package services;

import dao.CustomerDao;
import dao.MerchantDao;
import models.Customer;
import models.Merchant;

import java.util.List;
import java.util.Scanner;

public class MenuAdminService {

    private final CustomerDao customerDao;
    private final MerchantDao merchantDao;

    public MenuAdminService(CustomerDao customerDao, MerchantDao merchantDao) {
        this.customerDao = customerDao;
        this.merchantDao = merchantDao;
    }

    // Méthode principale du menu admin
    public void afficherMenuAdmin(Scanner scanner) {
        while (true) {
            System.out.println("\n\t##########################################");
            System.out.println("\t#              MENU ADMIN                #");
            System.out.println("\t##########################################");
            System.out.println("\t# 1. Voir liste complète des utilisateurs#");
            System.out.println("\t# 0. Déconnexion                         #");
            System.out.println("\t##########################################");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    afficherListeUtilisateurs();
                    break;
                case 0:
                    System.out.println("Déconnexion admin...");
                    return;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    // Afficher toutes les informations sur Customers et Merchants
    private void afficherListeUtilisateurs() {
        System.out.println("\n==================== CLIENTS ====================");
        List<Customer> customers = customerDao.selectAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("Aucun client trouvé.");
        } else {
            for (Customer c : customers) {
                System.out.println("Nom : " + c.getLastName() +
                        " | Prénom : " + c.getFirstName() +
                        " | Login : " + c.getUserAccount().getLogin() +
                        " | Solde Wallet : " + (c.getWallet() != null ? c.getWallet().getBalance() : "0") + " FCFA");
            }
        }

        /*System.out.println("\n=================== MARCHANDS ===================");
        List<Merchant> merchants = merchantDao.selectMerchant();
        if (merchants.isEmpty()) {
            System.out.println("Aucun marchand trouvé.");
        } else {
            for (Merchant m : merchants) {
                System.out.println("Nom : " + m.getLastName() +
                        " | Prénom : " + m.getFirstName() +
                        " | Login : " + m.getUserAccount().getLogin() +
                        " | Solde Wallet : " + (m.getWallet() != null ? m.getWallet().getBalance() : "0") + " FCFA");
            }
        }*/
    }
}
