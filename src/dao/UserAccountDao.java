package dao;

import models.UserAccount;
import models.enumeration.CompteType;

import java.sql.*;

public class UserAccountDao {
    /*private Connection connection;

    public UserAccountDao (){
        try{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin"));
        }
    }*/

    private final static String INSERT = "INSERT INTO Useraccount (login, password, comptetype) VALUES (?, ?, ?)";
    private final static String UPDATE_BY_LOGIN = "UPDATE Useraccount SET login=?, password=?, comptetype=? WHERE login=?";
    private final static String DELETE_BY_LOGIN = "DELETE FROM Useraccount WHERE login=?";
    private final static String READ_BY_LOGIN = "SELECT * FROM Useraccount WHERE login=?";
    private final static String READ_BY_ID = "SELECT * FROM Useraccount WHERE id=?";
    private final static String EXIST_LOGIN = "SELECT * FROM Useraccount WHERE login=?";
    private final static String FIND_LOGIN_AND_PASSWORD = "SELECT * FROM Useraccount WHERE login = ? AND password = ?";



    public UserAccount createUser(UserAccount userAccount) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin")) {

            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, userAccount.getLogin());
            statement.setString(2, userAccount.getPassword());
            statement.setString(3, userAccount.getCompteType().name());
            int createUser = statement.executeUpdate();


            if (createUser != 0) {
                ResultSet id = statement.getGeneratedKeys();
                if (id.next()) {
                    userAccount.setId(id.getLong(1)); // ici on récupère l'id généré automatiquement
                    System.out.println("\n" + userAccount);
                } else {
                    System.out.println("Échec lors de la récupération de l'ID généré.");
                }
            }

            return userAccount;
        } catch (SQLException e) {
            throw new RuntimeException("Echec lors de l'enregistrement. ", e);
        }
    }


    public UserAccount updateUser(UserAccount userAccount) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_LOGIN);
            statement.setString(1, userAccount.getLogin());
            statement.setString(2, userAccount.getPassword());
            statement.setString(3, userAccount.getCompteType().name());
            statement.setString(4, userAccount.getLogin());
            statement.executeUpdate();
            System.out.println("Modification éffectuée");
            return userAccount;
        } catch (SQLException e) {
            throw new RuntimeException("Echec lors de la modification", e);
        }
    }

    public UserAccount deleteUser(UserAccount userAccount) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_LOGIN);
            statement.setString(1, userAccount.getLogin());
            statement.executeUpdate();
            System.out.println("Suppression effectuée");
            return userAccount;

        } catch (SQLException e) {
            throw new RuntimeException("Echec lors de la suppression", e);
        }

    }

    public UserAccount selectUser(UserAccount userAccount) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
            PreparedStatement statement = connection.prepareStatement(READ_BY_LOGIN);

            statement.setString(1, userAccount.getLogin());
            ResultSet lg = statement.executeQuery();
            if (lg.next()) {
                userAccount.setId(lg.getLong("id"));
                userAccount.setCompteType(CompteType.valueOf(lg.getString("compteType")));
                userAccount.setLogin(lg.getString("login"));
                userAccount.setPassword(lg.getString("password"));
            }

            return userAccount;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long userAccountID(UserAccount userAccount) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
            PreparedStatement statement = connection.prepareStatement(READ_BY_ID);
            statement.setString(1, userAccount.getLogin());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                userAccount.setId(rs.getLong("id"));
                return rs.getLong("id");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Echec lors de la récupération", e);
        }
    }

    public boolean existsLogin(String login) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin")) {
            PreparedStatement statement = connection.prepareStatement(EXIST_LOGIN);
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next(); // true s'il y a au moins 1 résultat (login existant)
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'existence du login.", e);
        }
    }


    public UserAccount findByLoginAndPassword(String login, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
             PreparedStatement statement = connection.prepareStatement(FIND_LOGIN_AND_PASSWORD)) {

            // On injecte les paramètres dans la requête
            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            // Si on trouve un utilisateur correspondant
            if (rs.next()) {
                UserAccount user = new UserAccount();
                user.setId(rs.getLong("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setCompteType(CompteType.valueOf(rs.getString("comptetype")));
                return user;
            }

            // Aucun résultat trouvé
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'utilisateur", e);
        }
    }
}



