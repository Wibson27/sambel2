package com.sambel;

import java.sql.*;
import java.util.ArrayList;

public class Donation {
    private int id;
    private int userId;
    private int projectId;
    private double amount;
    private String date;
    private String paymentMethod;

    public Donation(int id, int userId, int projectId, double amount, String date, String paymentMethod) {
        this.id = id;
        this.userId = userId;
        this.projectId = projectId;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getProjectId() {
        return projectId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void create() {
        String sql = "INSERT INTO donations (user_id, project_id, amount, payment_method) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, this.userId);
            pstmt.setInt(2, this.projectId);
            pstmt.setDouble(3, this.amount);
            pstmt.setString(4, this.paymentMethod);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                        
                        // Create associated transaction
                        Transaction transaction = new Transaction(0, this.id, this.amount, this.paymentMethod, null);
                        transaction.create();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Donation> getByProjectId(int projectId) {
        ArrayList<Donation> donations = new ArrayList<>();
        String sql = "SELECT * FROM donations WHERE project_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                donations.add(new Donation(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("project_id"),
                    rs.getDouble("amount"),
                    rs.getString("date"),
                    rs.getString("payment_method")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }

    public static ArrayList<Donation> getByUserId(int userId) {
        ArrayList<Donation> donations = new ArrayList<>();
        String sql = "SELECT * FROM donations WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                donations.add(new Donation(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("project_id"),
                    rs.getDouble("amount"),
                    rs.getString("date"),
                    rs.getString("payment_method")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }

    public static double getTotalDonationForProject(int projectId) {
        String sql = "SELECT SUM(amount) as total FROM donations WHERE project_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public ArrayList<Transaction> getTransactions() {
        return Transaction.getByDonationId(this.id);
    }
}