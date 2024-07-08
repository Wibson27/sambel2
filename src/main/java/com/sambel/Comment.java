package com.sambel;

import java.sql.*;
import java.util.ArrayList;

public class Comment {
    private int id;
    private int userId;
    private int projectId;
    private String content;
    private String date;

    public Comment(int id, int userId, int projectId, String content, String date) {
        this.id = id;
        this.userId = userId;
        this.projectId = projectId;
        this.content = content;
        this.date = date;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public void create() {
        String sql = "INSERT INTO comments (user_id, project_id, content) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, this.userId);
            pstmt.setInt(2, this.projectId);
            pstmt.setString(3, this.content);
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

    public static ArrayList<Comment> getByProjectId(int projectId) {
        ArrayList<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE project_id = ? ORDER BY date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("project_id"),
                    rs.getString("content"),
                    rs.getString("date")
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public void update() {
        String sql = "UPDATE comments SET content = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.content);
            pstmt.setInt(2, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String sql = "DELETE FROM comments WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
