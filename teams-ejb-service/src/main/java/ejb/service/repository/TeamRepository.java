package ejb.service.repository;

import ejb.service.entity.TeamEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository {

    private static final String URL = "jdbc:postgresql://172.20.0.25:5434/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "1"; 


    public TeamEntity findById(Long id) {
        if (id == null) return null;

        String sql = "SELECT id, name, humans, is_deleted FROM teams WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            return mapRow(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TeamEntity save(TeamEntity entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");

        String sqlInsert = "INSERT INTO teams (name, humans, is_deleted) VALUES (?, ?, ?)";
        String sqlUpdate = "UPDATE teams SET name = ?, humans = ?, is_deleted = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            if (existsById(entity.getId())) {
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                    ps.setString(1, entity.getName());
                    ps.setString(2, joinHumans(entity.getHumans()));
                    ps.setBoolean(3, entity.getIsDeleted());
                    ps.setLong(4, entity.getId());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                    ps.setString(1, entity.getName());
                    ps.setString(2, joinHumans(entity.getHumans()));
                    ps.setBoolean(3, entity.getIsDeleted());
                    ps.executeUpdate();
                }
            }

            return entity;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsById(Long id) {
        if (id == null) return false;

        String sql = "SELECT 1 FROM teams WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMember(Long humanId) {
        if (humanId == null) return;

        String selectSql = "SELECT id, humans FROM teams";
        String updateSql = "UPDATE teams SET humans = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement psSelect = conn.prepareStatement(selectSql);
             ResultSet rs = psSelect.executeQuery()) {

            while (rs.next()) {
                Long teamId = rs.getLong("id");
                String humansStr = rs.getString("humans");
                List<Long> humans = parseHumans(humansStr);

                if (humans.remove(humanId)) {
                    try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                        psUpdate.setString(1, joinHumans(humans));
                        psUpdate.setLong(2, teamId);
                        psUpdate.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private TeamEntity mapRow(ResultSet rs) throws SQLException {
        TeamEntity team = new TeamEntity();
        team.setId(rs.getLong("id"));
        team.setName(rs.getString("name"));
        team.setHumans(parseHumans(rs.getString("humans")));
        team.setIsDeleted(rs.getBoolean("is_deleted"));
        return team;
    }

    private List<Long> parseHumans(String humansStr) {
        List<Long> humans = new ArrayList<>();
        if (humansStr == null || humansStr.isEmpty()) return humans;

        for (String s : humansStr.split(",")) {
            humans.add(Long.parseLong(s.trim()));
        }
        return humans;
    }

    private String joinHumans(List<Long> humans) {
        if (humans == null || humans.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Long h : humans) {
            if (sb.length() > 0) sb.append(",");
            sb.append(h);
        }
        return sb.toString();
    }
}
