package dao;

import models.Admin;
import models.UserAccount;

import java.sql.*;
import java.util.Arrays;

public class AdminDao {
    private final static String INSERT = "INSERT INTO Admin (lastname, firstname, phonenumber, email, privileges, idUserAccount) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE Admin SET lastname=?, firstname=?, phonenumber=?, email=?, privileges=? WHERE idUserAccount=?";
    private final static String DELETE = "DELETE FROM Admin WHERE idUserAccount=?";
    private final static String READ = "SELECT * FROM Admin WHERE idUserAccount=?";


    /*public Admin createAdmin(Admin admin){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");

            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, admin.getLastName());
            statement.setString(2, admin.getFirstName());
            statement.setString(3, admin.getPhoneNumber());
            statement.setString(4, admin.getEmail());
            return admin;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    public Admin createAdmin(Admin admin){

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin")) {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, admin.getLastName());
            statement.setString(2, admin.getFirstName());
            statement.setString(3, admin.getPhoneNumber());
            statement.setString(4, admin.getEmail());
            statement.setString(5, String.join(";", admin.getPrivileges().stream().map(Enum::name).toArray(String[]::new)));
            statement.setLong(6, admin.getUserAccount().getId()); // FK vers UserAccount


            /*int createUser = statement.executeUpdate();
            if(createUser != 0){
                ResultSet id = statement.getGeneratedKeys();
                if (id.next()){
                    admin.setId(id.getLong(1));
                    System.out.println(admin.afficherInfo());
                }else{
                    System.out.println("Échec de la récupération de l'ID généré ADMIN.");
                }
            }*/

            statement.executeUpdate(); // exécute l'insertion
            return admin;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Admin updateAdmin(Admin admin){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");

            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, admin.getLastName());
            statement.setString(2, admin.getFirstName());
            statement.setString(3, admin.getPhoneNumber());
            statement.setString(4, admin.getEmail());
            statement.setLong(5, admin.getUserAccount().getId());
            statement.setString(6, String.join(";", admin.getPrivileges().stream().map(Enum::name).toArray(String[]::new)));
            statement.setLong(7, admin.getUserAccount().getId());
            statement.executeUpdate();
            System.out.println("Modification effectuée");
            return admin;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Admin deleteAdmin(Admin admin){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");

            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, admin.getUserAccount().getId());
            statement.executeUpdate();

            UserAccountDao userAccountDao = new UserAccountDao();
            userAccountDao.deleteUser(admin.getUserAccount());
            System.out.println("Suppression effectuée");

            return admin;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Admin selectAdmin(Admin admin) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MY_DB", "root", "admin");
            PreparedStatement statement = connection.prepareStatement(READ);

            statement.setLong(1, admin.getUserAccount().getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                admin.setId(rs.getLong("id"));
                admin.setLastName(rs.getString("lastName"));
                admin.setFirstName(rs.getString("firstName"));
                admin.setPhoneNumber(rs.getString("phoneNumber"));
                admin.setEmail(rs.getString("email"));

                Long idUserAccount = rs.getLong("idUserAccount");
                UserAccountDao userAccountDao = new UserAccountDao();
                UserAccount userA = new UserAccount("");
                userA.setId(idUserAccount);
                userAccountDao.selectUser(userA);
                admin.setUserAccount(userA);


                admin.setPrivileges(Arrays.stream(rs.getString("privileges").split(";"))
                        .map(models.enumeration.Privileges::valueOf)
                        .collect(java.util.stream.Collectors.toList()));
                System.out.println(admin.afficherUser());
            } else {
                System.out.println("Aucun utilisateur trouvé avec ce login.");
            }

            return admin;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
