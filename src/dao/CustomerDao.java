package dao;

import models.enumeration.*;

import java.sql.*;

import models.Customer;
import models.UserAccount;
import models.Wallet;

public class CustomerDao {
    private final static String INSERT = "INSERT INTO Customer(lastname, firstname, phonenumber, email, idUserAccount, idWallet, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE Customer SET lastname=?, firstname=?, phonenumber=?, email=?, idUserAccount=?, idWallet=?, gender=? WHERE idUserAccount=?";
    private final static String DELETE = "DELETE FROM Customer WHERE idUserAccount=?";
    private final static String READ = "SELECT * FROM Customer WHERE idUserAccount=?";


    /*public Customer createCustomer(Customer customer) {
        if (customer.getUserAccount() == null || customer.getUserAccount().getId() == null) {
            throw new IllegalArgumentException("L'utilisateur associé au Client doit être initialisé avec un id.");
        }
        if (customer.getWallet() == null || customer.getWallet().getId() == null) {
            throw new IllegalArgumentException("Le wallet associé au Client doit être initialisé avec un id.");
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");

            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, customer.getLastName());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEmail());
            statement.setLong(5, customer.getUserAccount().getId());
            statement.setLong(6, customer.getWallet().getId());
            statement.setString(7, customer.getGender().name());

            int createUser = statement.executeUpdate();
            if(createUser != 0){
                ResultSet id = statement.getGeneratedKeys();
                if (id.next()){
                    customer.setId(id.getLong(1));
                    System.out.println("Merchant inséré avec ID : " + customer.getId() + " | " + customer);
                }else{
                    System.out.println("Échec de la récupération de l'ID généré.");
                }
            }
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    public Customer createCustomer(Customer customer) {
        if (customer.getUserAccount() == null || customer.getUserAccount().getId() == null) {
            throw new IllegalArgumentException("L'utilisateur associé au Client doit être initialisé avec un id.");
        }
        if (customer.getWallet() == null || customer.getWallet().getId() == null) {
            throw new IllegalArgumentException("Le wallet associé au Client doit être initialisé avec un id.");
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin")) {

            // ✅ Ajout de RETURN_GENERATED_KEYS
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getLastName());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEmail());
            statement.setLong(5, customer.getUserAccount().getId());
            statement.setLong(6, customer.getWallet().getId());
            statement.setString(7, customer.getGender().name());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                // ✅ Récupération correcte de l'ID auto-généré
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        customer.setId(id);
                    } else {
                        System.out.println("Échec de la récupération de l'ID généré.");
                    }
                }
            }
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion du client : " + e.getMessage(), e);
        }
    }


    public Customer updateCustomer(Customer customer) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, customer.getLastName());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEmail());
            statement.setLong(5, customer.getUserAccount().getId());
            statement.setLong(6, customer.getWallet().getId());
            statement.setString(7, customer.getGender().name());
            statement.setLong(8, customer.getUserAccount().getId());

            statement.executeUpdate();
            System.out.println("Modification effectuée");
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer deleteCustomer(Customer customer) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
            PreparedStatement statement = connection.prepareStatement(DELETE);

            statement.setLong(1, customer.getUserAccount().getId());
            statement.executeUpdate();

            UserAccountDao userAccountDao = new UserAccountDao();
            userAccountDao.deleteUser(customer.getUserAccount());

            WalletDao walletDao = new WalletDao();
            walletDao.deleteWallet(customer.getWallet());

            return customer;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer selectCustomer(Customer customer) {
        try {
            if (customer == null) {
                throw new IllegalArgumentException("Customer est null.");
            }
            if (customer.getUserAccount() == null) {
                throw new IllegalArgumentException("Le ou son UserAccount est null.");
            }

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
            PreparedStatement statement = connection.prepareStatement(READ);

            statement.setLong(1, customer.getUserAccount().getId());
            ResultSet rs = statement.executeQuery();


            if (rs.next()) {
                customer.setId(rs.getLong("id"));
                customer.setLastName(rs.getString("lastName"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setPhoneNumber(rs.getString("phoneNumber"));
                customer.setEmail(rs.getString("email"));
                customer.setGender(Gender.valueOf(rs.getString("gender")));

                Long idUserAccount = rs.getLong("idUserAccount");
                UserAccountDao userAccountDAO = new UserAccountDao();
                UserAccount userA = new UserAccount("");
                userA.setId(idUserAccount);
                userAccountDAO.selectUser(userA);
                customer.setUserAccount(userA);

                Long idWallet = rs.getLong("idWallet");
                Wallet wallet = new Wallet();
                wallet.setId(idWallet);
                WalletDao walletDao = new WalletDao();
                walletDao.selectWallet(wallet);
                customer.setWallet(wallet);

            } else {
                System.out.println("Aucun utilisateur trouvé avec ce login.");
            }

            return customer;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
