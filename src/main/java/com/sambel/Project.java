package com.sambel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Project {
    private int id;
    private int creatorId;
    private String title;
    private String description;
    private String imagePath;
    private double fundingGoal;
    private String fundingDeadline;
    private String category;

    public Project(int id, int creatorId, String title, String description, String imagePath, 
                   double fundingGoal, String fundingDeadline, String category) {
        this.id = id;
        this.creatorId = creatorId;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.fundingGoal = fundingGoal;
        this.fundingDeadline = fundingDeadline;
        this.category = category;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getFundingGoal() {
        return fundingGoal;
    }

    // public String getFundingDeadline() {
    //     return fundingDeadline;
    // }

    public LocalDate getFundingDeadline() {
        return LocalDate.parse(this.fundingDeadline);
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setFundingGoal(double fundingGoal) {
        this.fundingGoal = fundingGoal;
    }

    public void setFundingDeadline(String fundingDeadline) {
        this.fundingDeadline = fundingDeadline;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //Other method
    public void create() {
        String sql = "INSERT INTO projects (creator_id, title, description, image_path, funding_goal, funding_deadline, category) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, this.creatorId);
            pstmt.setString(2, this.title);
            pstmt.setString(3, this.description);
            if (this.imagePath != null) {
                pstmt.setString(4, this.imagePath);
            } else {
                pstmt.setNull(4, java.sql.Types.VARCHAR);
            }
            pstmt.setDouble(5, this.fundingGoal);
            pstmt.setString(6, this.fundingDeadline);
            pstmt.setString(7, this.category);
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
        String sql = "UPDATE projects SET title = ?, description = ?, image_path = ?, funding_goal = ?, funding_deadline = ?, category = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.title);
            pstmt.setString(2, this.description);
            if (this.imagePath != null) {
                pstmt.setString(3, this.imagePath);
            } else {
                pstmt.setNull(3, java.sql.Types.VARCHAR);
            }
            pstmt.setDouble(4, this.fundingGoal);
            pstmt.setString(5, this.fundingDeadline);
            pstmt.setString(6, this.category);
            pstmt.setInt(7, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Project getById(int id) {
        String sql = "SELECT * FROM projects WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Project(
                    rs.getInt("id"),
                    rs.getInt("creator_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("image_path"),  // This can be null
                    rs.getDouble("funding_goal"),
                    rs.getString("funding_deadline"),
                    rs.getString("category")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete() {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Project> getByCreatorId(int creatorId) {
        ArrayList<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE creator_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, creatorId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                projects.add(new Project(
                    rs.getInt("id"),
                    rs.getInt("creator_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("image_path"),
                    rs.getDouble("funding_goal"),
                    rs.getString("funding_deadline"),
                    rs.getString("category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public void addUpdate(String updateContent) {
        String sql = "INSERT INTO project_updates (project_id, content) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            pstmt.setString(2, updateContent);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Project project = new Project(
                    rs.getInt("id"),
                    rs.getInt("creator_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("image_path"),
                    rs.getDouble("funding_goal"),
                    rs.getString("funding_deadline"),
                    rs.getString("category")
                );
                projects.add(project);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving projects: " + e.getMessage());
            e.printStackTrace();
        }

        return projects;
    }

    public double getCurrentFunding() {
        String sql = "SELECT SUM(amount) as total FROM donations WHERE project_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving current funding: " + e.getMessage());
            e.printStackTrace();
        }
        return 0.0;
    }

    public int getDonorsCount() {
        String sql = "SELECT COUNT(DISTINCT user_id) as count FROM donations WHERE project_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<String> getUpdates() {
        ArrayList<String> updates = new ArrayList<>();
        String sql = "SELECT content, date FROM project_updates WHERE project_id = ? ORDER BY date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                updates.add(rs.getDate("date") + ": " + rs.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updates;
    }


    public double getFundingPercentage() {
        double currentFunding = getCurrentFunding();
        return (currentFunding / this.fundingGoal) * 100;
    }

    // public ArrayList<Comment> getComments() {
    //     return Comment.getByProjectId(this.id);
    // }

    public ArrayList<String> getComments() {
        ArrayList<String> comments = new ArrayList<>();
        String sql = "SELECT u.name, c.content, c.date FROM comments c JOIN users u ON c.user_id = u.id WHERE c.project_id = ? ORDER BY c.date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                comments.add(rs.getString("name") + " (" + rs.getDate("date") + "): " + rs.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }



    public ArrayList<Donation> getDonations() {
        return Donation.getByProjectId(this.id);
    }

    public double getTotalDonations() {
        return Donation.getTotalDonationForProject(this.id);
    }

    public ArrayList<RewardPack> getRewardPacks() {
        return RewardPack.getByProjectId(this.id);
    }
}