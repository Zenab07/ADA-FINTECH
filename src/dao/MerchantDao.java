package dao;

import models.Merchant;
import models.UserAccount;
import models.Wallet;

import java.sql.*;

public class MerchantDao {
    private final static String INSERT = "INSERT INTO Merchant (lastname, firstname, phonenumber, email, location, idUserAccount, idWallet) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE Merchant SET lastname=?, firstname=?, phonenumber=?, email=?, location=?, idUserAccount=?, idWallet=? WHERE idUserAccount=?";
    private final static String DELETE = "DELETE FROM Merchant WHERE idUserAccount=?";
    private final static String READ = "SELECT * FROM Merchant WHERE idUserAccount=?";


    public Merchant createMerchant(Merchant merchant){
        if (merchant.getUserAccount() == null || merchant.getUserAccount().getId() == null) {
            throw new IllegalArgumentException("L'utilisateur associé au marchand doit être initialisé avec un id.");
        }
        if (merchant.getWallet() == null || merchant.getWallet().getId() == null) {
            throw new IllegalArgumentException("Le wallet associé au marchand doit être initialisé avec un id.");
        }

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");

            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, merchant.getLastName());
            statement.setString(2, merchant.getFirstName());
            statement.setString(3, merchant.getPhoneNumber());
            statement.setString(4, merchant.getEmail());
            statement.setString(5, merchant.getLocation());
            statement.setLong(6, merchant.getUserAccount().getId());
            statement.setLong(7, merchant.getWallet().getId());
            int createUser = statement.executeUpdate();
            if(createUser != 0){
                ResultSet id = statement.getGeneratedKeys();
                if (id.next()){
                    merchant.setId(id.getLong(1));
                }else{
                    System.out.println("Échec de la récupération de l'ID généré.");
                }
            }

            return  merchant;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Merchant updateMerchant(Merchant merchant){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, merchant.getLastName());
            statement.setString(2, merchant.getFirstName());
            statement.setString(3, merchant.getPhoneNumber());
            statement.setString(4, merchant.getEmail());
            statement.setString(5, merchant.getLocation());
            statement.setLong(6, merchant.getUserAccount().getId());
            statement.setLong(7, merchant.getWallet().getId());
            statement.setLong(8, merchant.getUserAccount().getId());

            statement.executeUpdate();
            return merchant;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Merchant deleteMerchant(Merchant merchant){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, merchant.getUserAccount().getId());
            statement.executeUpdate();

            UserAccountDao userAccountDao = new UserAccountDao();
            userAccountDao.deleteUser(merchant.getUserAccount());

            WalletDao walletDao = new WalletDao();
            walletDao.deleteWallet(merchant.getWallet());
            return merchant;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Merchant selectMerchant(Merchant merchant) {
        try {
            if (merchant == null) {
                throw new IllegalArgumentException("Customer est null.");
            }
            if (merchant.getUserAccount() == null) {
                throw new IllegalArgumentException("Le ou son UserAccount est null.");
            }

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
            PreparedStatement statement = connection.prepareStatement(READ);
            statement.setString(1, merchant.getLastName());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                merchant.setId(rs.getLong("id"));
                merchant.setLastName(rs.getString("lastName"));
                merchant.setFirstName(rs.getString("firstName"));
                merchant.setPhoneNumber(rs.getString("phoneNumber"));
                merchant.setEmail(rs.getString("email"));
                merchant.setLocation(rs.getString("localisation"));

                Long idUserAccount = rs.getLong("idUserAccount");
                UserAccountDao userAccountDAO = new UserAccountDao();
                UserAccount userA = new UserAccount("");
                userA.setId(idUserAccount);
                userAccountDAO.selectUser(userA);
                merchant.setUserAccount(userA);

                Long idWallet = rs.getLong("idWallet");
                Wallet wallet = new Wallet();
                wallet.setId(idWallet);
                WalletDao walletDao = new WalletDao();
                walletDao.selectWallet(wallet);
                merchant.setWallet(wallet);

            } else {
                System.out.println("Aucun utilisateur trouvé avec ce login.");
            }

            return merchant;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
