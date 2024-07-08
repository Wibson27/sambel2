package com.sambel;

import java.sql.*;
import java.util.ArrayList;

public class Message {
    private int id;
    private int senderId;
    private int receiverId;
    private int projectId;
    private String content;
    private String date;

    public Message(int id, int senderId, int receiverId, int projectId, String content, String date) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.projectId = projectId;
        this.content = content;
        this.date = date;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }
    public int getReceiverId() { return receiverId; }
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }
    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public void create() {
        String sql = "INSERT INTO messages (sender_id, receiver_id, project_id, content) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, this.senderId);
            pstmt.setInt(2, this.receiverId);
            pstmt.setInt(3, this.projectId);
            pstmt.setString(4, this.content);
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

    public static ArrayList<Message> getByReceiverAndProject(int receiverId, int projectId) {
        ArrayList<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE receiver_id = ? AND project_id = ? ORDER BY date ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, receiverId);
            pstmt.setInt(2, projectId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("id"),
                    rs.getInt("sender_id"),
                    rs.getInt("receiver_id"),
                    rs.getInt("project_id"),
                    rs.getString("content"),
                    rs.getString("date")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public static ArrayList<Message> getConversation(int user1Id, int user2Id, int projectId) {
        ArrayList<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE ((sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)) AND project_id = ? ORDER BY date ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user1Id);
            pstmt.setInt(2, user2Id);
            pstmt.setInt(3, user2Id);
            pstmt.setInt(4, user1Id);
            pstmt.setInt(5, projectId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("id"),
                    rs.getInt("sender_id"),
                    rs.getInt("receiver_id"),
                    rs.getInt("project_id"),
                    rs.getString("content"),
                    rs.getString("date")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}