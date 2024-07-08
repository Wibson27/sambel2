package com.sambel;

import java.sql.*;
import java.util.ArrayList;

public class RewardPack {
    private int id;
    private int projectId;
    private String title;
    private int numberAvailable;
    private String description;
    private String imagePath;
    private double price;

    public RewardPack(int id, int projectId, String title, int numberAvailable, String description, String imagePath, double price) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.numberAvailable = numberAvailable;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getNumberAvailable() { return numberAvailable; }
    public void setNumberAvailable(int numberAvailable) { this.numberAvailable = numberAvailable; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public void create() {
        String sql = "INSERT INTO reward_packs (project_id, title, number_available, description, image_path, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, this.projectId);
            pstmt.setString(2, this.title);
            pstmt.setInt(3, this.numberAvailable);
            pstmt.setString(4, this.description);
            pstmt.setString(5, this.imagePath);
            pstmt.setDouble(6, this.price);
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

    public void update() {
        String sql = "UPDATE reward_packs SET title = ?, number_available = ?, description = ?, image_path = ?, price = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.title);
            pstmt.setInt(2, this.numberAvailable);
            pstmt.setString(3, this.description);
            pstmt.setString(4, this.imagePath);
            pstmt.setDouble(5, this.price);
            pstmt.setInt(6, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String sql = "DELETE FROM reward_packs WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static RewardPack getById(int id) {
        String sql = "SELECT * FROM reward_packs WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new RewardPack(
                    rs.getInt("id"),
                    rs.getInt("project_id"),
                    rs.getString("title"),
                    rs.getInt("number_available"),
                    rs.getString("description"),
                    rs.getString("image_path"),
                    rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<RewardPack> getByProjectId(int projectId) {
        ArrayList<RewardPack> rewardPacks = new ArrayList<>();
        String sql = "SELECT * FROM reward_packs WHERE project_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                RewardPack rewardPack = new RewardPack(
                    rs.getInt("id"),
                    rs.getInt("project_id"),
                    rs.getString("title"),
                    rs.getInt("number_available"),
                    rs.getString("description"),
                    rs.getString("image_path"),
                    rs.getDouble("price")
                );
                rewardPacks.add(rewardPack);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rewardPacks;
    }

    public void decreaseAvailability() {
        if (this.numberAvailable > 0) {
            this.numberAvailable--;
            this.update();
        }
    }
}