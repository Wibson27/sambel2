package com.sambel;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;

    // Constructor
    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void register(String username, String email, String password) {
        // Generate hashed password using PBKDF2
        String hashedPassword = hashPassword(password);

        String sql = "INSERT INTO users(name, email, password) VALUES(?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, hashedPassword);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Login user
    public static User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                // Verify password using PBKDF2
                if (verifyPassword(password, storedHash)) {
                    // If password is correct, create and return a User object
                    return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        email,
                        storedHash  // Store the hashed password, not the plain text
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if login fails
    }

    // Helper method to hash password with PBKDF2
    private static String hashPassword(String password) {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 256);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper method to verify password with PBKDF2
    private static boolean verifyPassword(String password, String storedHash) {
        String[] parts = storedHash.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] storedHashBytes = Base64.getDecoder().decode(parts[1]);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 256);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return MessageDigest.isEqual(storedHashBytes, hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Get user by ID
    public static User getById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update user information
    public void update() {
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.username);
            pstmt.setString(2, this.email);
            pstmt.setString(3, this.password);
            pstmt.setInt(4, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete user
    public void delete() {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Project createProject(String title, String description, String imagePath, 
                                 double fundingGoal, String fundingDeadline, String category) {
        Project project = new Project(0, this.id, title, description, imagePath, 
                                      fundingGoal, fundingDeadline, category);
        project.create();
        return project;
    }

    public Donation donate(int projectId, double amount, String paymentMethod) {
        Donation donation = new Donation(0, this.id, projectId, amount, null, paymentMethod);
        donation.create();
        return donation;
    }

    public ArrayList<Project> getProjects() {
        return Project.getByCreatorId(this.id);
    }

    public ArrayList<Donation> getDonations() {
        return Donation.getByUserId(this.id);
    }

    public ArrayList<BadgeAndAchievement> getBadges() {
        return BadgeAndAchievement.getByUserId(this.id);
    }
}