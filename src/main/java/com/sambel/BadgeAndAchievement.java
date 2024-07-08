package com.sambel;

import java.sql.*;
import java.util.ArrayList;

public class BadgeAndAchievement {
    private int id;
    private String name;
    private String description;
    private String criteria;

    public BadgeAndAchievement(int id, String name, String description, String criteria) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.criteria = criteria;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCriteria() { return criteria; }
    public void setCriteria(String criteria) { this.criteria = criteria; }

    public void create() {
        String sql = "INSERT INTO badges_and_achievements (name, description, criteria) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.description);
            pstmt.setString(3, this.criteria);
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

    public static void awardToUser(int userId, int badgeId) {
        String sql = "INSERT INTO user_badges (user_id, badge_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, badgeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<BadgeAndAchievement> getByUserId(int userId) {
        ArrayList<BadgeAndAchievement> badges = new ArrayList<>();
        String sql = "SELECT b.* FROM badges_and_achievements b " +
                     "JOIN user_badges ub ON b.id = ub.badge_id " +
                     "WHERE ub.user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BadgeAndAchievement badge = new BadgeAndAchievement(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("criteria")
                );
                badges.add(badge);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return badges;
    }

    public static boolean userHasBadge(int userId, int badgeId) {
        String sql = "SELECT * FROM user_badges WHERE user_id = ? AND badge_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, badgeId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // If there's a result, the user has the badge
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void update() {
        String sql = "UPDATE badges_and_achievements SET name = ?, description = ?, criteria = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.description);
            pstmt.setString(3, this.criteria);
            pstmt.setInt(4, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String sql = "DELETE FROM badges_and_achievements WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeBadgeFromUser(int userId, int badgeId) {
        String sql = "DELETE FROM user_badges WHERE user_id = ? AND badge_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, badgeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}