package com.sambel;

import java.sql.*;
import java.util.ArrayList;

public class Transaction {
    private int id;
    private int donationId;
    private double amount;
    private String paymentMethod;
    private String date;

    public Transaction(int id, int donationId, double amount, String paymentMethod, String date) {
        this.id = id;
        this.donationId = donationId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.date = date;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getDonationId() { return donationId; }
    public void setDonationId(int donationId) { this.donationId = donationId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public void create() {
        String sql = "INSERT INTO transactions (donation_id, amount, payment_method) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, this.donationId);
            pstmt.setDouble(2, this.amount);
            pstmt.setString(3, this.paymentMethod);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Transaction> getByDonationId(int donationId) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE donation_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, donationId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getInt("id"),
                    rs.getInt("donation_id"),
                    rs.getDouble("amount"),
                    rs.getString("payment_method"),
                    rs.getString("date")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void update() {
        String sql = "UPDATE transactions SET amount = ?, payment_method = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, this.amount);
            pstmt.setString(2, this.paymentMethod);
            pstmt.setInt(3, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Transaction getById(int id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Transaction(
                    rs.getInt("id"),
                    rs.getInt("donation_id"),
                    rs.getDouble("amount"),
                    rs.getString("payment_method"),
                    rs.getString("date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
