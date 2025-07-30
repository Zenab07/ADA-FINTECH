package dao;

import models.Wallet;

import java.sql.*;

public class WalletDao {
    private final static String INSERT = "INSERT INTO Wallet (balance) VALUES (?)";
    private final static String UPDATE = "UPDATE Wallet SET balance=? WHERE id=?";
    private final static String DELETE = "DELETE FROM Wallet WHERE id=?";
    private final static String READ = "SELECT * FROM Wallet WHERE id=?";

    public Wallet createWallet(Wallet wallet) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");

            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setBigDecimal(1, wallet.getBalance());
            int createUser = statement.executeUpdate();

            if(createUser != 0){
                ResultSet id = statement.getGeneratedKeys();
                if (id.next()){
                    wallet.setId(id.getLong(1));
                    System.out.println("Utilisateur inséré avec ID : " + wallet.getId() + " | " + wallet.getBalance());
                }else{
                    System.out.println("Échec lors de la récupération de l'ID généré.");
                }
            }

            return wallet;
        } catch (SQLException e) {
            throw new RuntimeException("Echec de l'enregistrement", e);
        }
    }

    public Wallet updateWallet(Wallet wallet) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");

            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setLong(1, wallet.getId());
            statement.setBigDecimal(2, wallet.getBalance());
            statement.executeUpdate();
            System.out.println("Modification effectuée");
            return wallet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Wallet deleteWallet(Wallet wallet) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");

            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, wallet.getId());
            statement.executeUpdate();
            return wallet;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Wallet selectWallet(Wallet wallet) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");

            PreparedStatement statement = connection.prepareStatement(READ);
            statement.setLong(1, wallet.getId());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                wallet.setId(rs.getLong("id"));
                wallet.setBalance(rs.getBigDecimal("balance"));
            } else {
                System.out.println("Aucun solde trouvé avec .");
            }
            return wallet;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recuperation de l'id
    public Long walletID(Wallet wallet) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");

            PreparedStatement statement = connection.prepareStatement(READ);
            statement.setLong(1, wallet.getId());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                wallet.setId(rs.getLong("id"));
                return rs.getLong("id");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
