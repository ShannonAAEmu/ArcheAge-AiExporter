package utils;

import models.AreasMission;
import models.NetHideMission;
import models.PointPos;
import models.Triangulation;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class SqliteUtils {

    private static Connection connection;

    public static void exportAreasMissionData(File aiGeoDataFolder, String zoneId, List<AreasMission> areasMissionList, String geoType) throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite:" + aiGeoDataFolder.getPath() + "\\" + geoType + "_ai_geo_data.sqlite3");
        assert null != connection;
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatementAreas;
        PreparedStatement preparedStatementPoints;
        statement.execute("CREATE TABLE IF NOT EXISTS areas_mission (id INT, zone_key INT, name TEXT, type TEXT, point_count INT);");
        statement.execute("CREATE TABLE IF NOT EXISTS areas_mission_points (id INT, zone_key INT, x REAL, y REAL, z REAL);");
        preparedStatementAreas = connection.prepareStatement("INSERT INTO areas_mission (id, zone_key, name, type, point_count) VALUES (?, ?, ?, ?, ?);");
        preparedStatementPoints = connection.prepareStatement("INSERT INTO areas_mission_points (id, zone_key, x, y, z) VALUES (?, ?, ?, ?, ?);");
        System.out.print("Exporting AREAS_MISSION from " + zoneId + " zone ... ");
        int percentLog = 10;
        int tick = percentLog;
        int i = 0;
        int fullTick = areasMissionList.size();
        for (AreasMission areasMission : areasMissionList) {
            preparedStatementAreas.setInt(1, ++i);
            preparedStatementAreas.setInt(2, Integer.parseInt(zoneId));
            preparedStatementAreas.setString(3, areasMission.getName());
            preparedStatementAreas.setString(4, areasMission.getType());
            preparedStatementAreas.setInt(5, areasMission.getPoints());
            preparedStatementAreas.executeUpdate();
            for (PointPos pointPos : areasMission.getPointsCoords()) {
                preparedStatementPoints.setInt(1, i);
                preparedStatementPoints.setInt(2, Integer.parseInt(zoneId));
                preparedStatementPoints.setDouble(3, pointPos.getX());
                preparedStatementPoints.setDouble(4, pointPos.getY());
                preparedStatementPoints.setDouble(5, pointPos.getZ());
                preparedStatementPoints.executeUpdate();
            }
            percentLog = percentLog(i, fullTick, percentLog, tick);
        }
        System.out.println("done!");
        preparedStatementAreas.close();
        statement.close();
        connection.close();
    }

    public static void exportNetMissionData(boolean isHide, File aiGeoDataFolder, String zoneId, List<NetHideMission> netHideMissionList, String geoType) throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite:" + aiGeoDataFolder.getPath() + "\\" + geoType + "_ai_geo_data.sqlite3");
        assert null != connection;
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement;
        if (isHide) {
            statement.execute("CREATE TABLE IF NOT EXISTS hide_mission (id INT, zone_key INT, x REAL, y REAL, z REAL);");
            preparedStatement = connection.prepareStatement("INSERT INTO hide_mission (id, zone_key, x, y, z) VALUES (?, ?, ?, ?, ?);");
        } else {
            statement.execute("CREATE TABLE IF NOT EXISTS net_mission (id INT, zone_key INT, x REAL, y REAL, z REAL);");
            preparedStatement = connection.prepareStatement("INSERT INTO net_mission (id, zone_key, x, y, z) VALUES (?, ?, ?, ?, ?);");
        }
        System.out.print("Exporting MISSION from " + zoneId + " zone ... ");
        int percentLog = 10;
        int tick = percentLog;
        int i = 0;
        int fullTick = netHideMissionList.size();
        for (NetHideMission netHideMission : netHideMissionList) {
            preparedStatement.setInt(1, netHideMission.getId());
            preparedStatement.setInt(2, Integer.parseInt(zoneId));
            preparedStatement.setDouble(3, netHideMission.getX());
            preparedStatement.setDouble(4, netHideMission.getY());
            preparedStatement.setDouble(5, netHideMission.getZ());
            preparedStatement.executeUpdate();
            i++;
            percentLog = percentLog(i, fullTick, percentLog, tick);
        }
        System.out.println("done!");
        preparedStatement.close();
        statement.close();
        connection.close();
    }

    public static void exportAiNavigationData(File aiGeoDataFolder, String zoneId, List<Triangulation> triangulationList, String geoType) throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite:" + aiGeoDataFolder.getPath() + "\\" + geoType + "_ai_geo_data.sqlite3");
        assert null != connection;
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement;
        statement.execute("CREATE TABLE IF NOT EXISTS ai_navigation (id INT, zone_key INT, start_point INT, end_point INT, x REAL, y REAL, z REAL);");
        preparedStatement = connection.prepareStatement("INSERT INTO ai_navigation (id, zone_key, start_point, end_point, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?);");
        System.out.print("Exporting AI_NAVIGATION from " + zoneId + " zone ... ");
        int percentLog = 10;
        int tick = percentLog;
        int i = 0;
        int fullTick = triangulationList.size();
        for (Triangulation triangulation : triangulationList) {
            preparedStatement.setInt(1, ++i);
            preparedStatement.setInt(2, Integer.parseInt(zoneId));
            preparedStatement.setInt(3, triangulation.getStartPoint());
            preparedStatement.setInt(4, triangulation.getEndPoint());
            preparedStatement.setDouble(5, triangulation.getX());
            preparedStatement.setDouble(6, triangulation.getY());
            preparedStatement.setDouble(7, triangulation.getZ());
            preparedStatement.executeUpdate();
            percentLog = percentLog(i, fullTick, percentLog, tick);
        }
        System.out.println("done!");
        preparedStatement.close();
        statement.close();
        connection.close();
    }

    public static void exportVertexMissionData(File aiGeoDataFolder, String zoneId, List<PointPos> vertexMissionList, String geoType) throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite:" + aiGeoDataFolder.getPath() + "\\" + geoType + "_ai_geo_data.sqlite3");
        assert null != connection;
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement;
        statement.execute("CREATE TABLE IF NOT EXISTS vertex_mission (id INT, zone_key INT, x REAL, y REAL, z REAL);");
        preparedStatement = connection.prepareStatement("INSERT INTO vertex_mission (id, zone_key, x, y, z) VALUES (?, ?, ?, ?, ?);");
        System.out.print("Exporting VERTEX_MISSION from " + zoneId + " zone ... ");
        int percentLog = 10;
        int tick = percentLog;
        int i = 0;
        int fullTick = vertexMissionList.size();
        for (PointPos pointPos : vertexMissionList) {
            preparedStatement.setInt(1, ++i);
            preparedStatement.setInt(2, Integer.parseInt(zoneId));
            preparedStatement.setDouble(3, pointPos.getX());
            preparedStatement.setDouble(4, pointPos.getY());
            preparedStatement.setDouble(5, pointPos.getZ());
            preparedStatement.executeUpdate();
            percentLog = percentLog(i, fullTick, percentLog, tick);
        }
        System.out.println("done!");
        preparedStatement.close();
        statement.close();
        connection.close();
    }

    public static int percentLog(int i, int fullSize, int percentLog, int tick) {
        if (i == fullSize * percentLog / 100) {
            System.out.print(percentLog + "% ");
            percentLog += tick;
        }
        return percentLog;
    }
}
