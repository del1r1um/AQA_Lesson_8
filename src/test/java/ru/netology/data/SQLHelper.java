package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    public static String getVerificationCode(DataHelper.AuthInfo authInfo) {
        val login = authInfo.getLogin();
        String userId = null;
        val searchForId = "SELECT id FROM users WHERE login = ?;";
        try (val connection = getConnection();
             val idStmt = connection.prepareStatement(searchForId)) {
            idStmt.setString(1, login);
            try (val rs = idStmt.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getString("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String code = "";
        val verificationCode = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1;";
        try (val connection = getConnection();
             val codeStmt = connection.prepareStatement(verificationCode)) {
            codeStmt.setString(1, userId);
            try (val rs = codeStmt.executeQuery()) {
                if (rs.next()) {
                    code = rs.getString("code");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static void cleanDb() {
        val runner = new QueryRunner();
        val codes = "DELETE FROM auth_codes";
        val cards = "DELETE FROM cards";
        val users = "DELETE FROM users";

        try (val connection = getConnection()) {
            runner.update(connection, codes);
            runner.update(connection, cards);
            runner.update(connection, users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

