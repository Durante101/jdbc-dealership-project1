package com.yearup.dealership.db;

import com.yearup.dealership.models.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (VIN, make, model, year, SOLD, color, vehicleType, odometer, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vehicle.getVin());
            stmt.setString(2, vehicle.getMake());
            stmt.setString(3, vehicle.getModel());
            stmt.setInt(4, vehicle.getYear());
            stmt.setBoolean(5, vehicle.isSold());
            stmt.setString(6, vehicle.getColor());
            stmt.setString(7, vehicle.getVehicleType());
            stmt.setInt(8, vehicle.getOdometer());
            stmt.setDouble(9, vehicle.getPrice());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
        }
    }

    public void removeVehicle(String vin) {
        String sql = "DELETE FROM vehicles WHERE VIN = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vin);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error removing vehicle: " + e.getMessage());
        }
    }

    public List<Vehicle> searchByPriceRange(double minPrice, double maxPrice) {
        String sql = "SELECT * FROM vehicles WHERE price BETWEEN ? AND ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehicles.add(createVehicleFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error searching by price range: " + e.getMessage());
        }

        return vehicles;
    }

    public List<Vehicle> searchByMakeModel(String make, String model) {
        String sql = "SELECT * FROM vehicles WHERE make = ? AND model = ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, make);
            stmt.setString(2, model);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehicles.add(createVehicleFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error searching by make/model: " + e.getMessage());
        }

        return vehicles;
    }


    public List<Vehicle> searchByYearRange(int minYear, int maxYear) {
        String sql = "SELECT * FROM vehicles WHERE year BETWEEN ? AND ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, minYear);
            stmt.setInt(2, maxYear);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehicles.add(createVehicleFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error searching by year range: " + e.getMessage());
        }

        return vehicles;
    }


    public List<Vehicle> searchByColor(String color) {
        String sql = "SELECT * FROM vehicles WHERE color = ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, color);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehicles.add(createVehicleFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error searching by color: " + e.getMessage());
        }

        return vehicles;
    }

    public List<Vehicle> searchByMileageRange(int minMileage, int maxMileage) {
        String sql = "SELECT * FROM vehicles WHERE odometer BETWEEN ? AND ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, minMileage);
            stmt.setInt(2, maxMileage);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehicles.add(createVehicleFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error searching by mileage range: " + e.getMessage());
        }

        return vehicles;
    }


    public List<Vehicle> searchByType(String type) {
        String sql = "SELECT * FROM vehicles WHERE vehicleType = ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehicles.add(createVehicleFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error searching by type: " + e.getMessage());
        }

        return vehicles;
    }

    private Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(resultSet.getString("VIN"));
        vehicle.setMake(resultSet.getString("make"));
        vehicle.setModel(resultSet.getString("model"));
        vehicle.setYear(resultSet.getInt("year"));
        vehicle.setSold(resultSet.getBoolean("SOLD"));
        vehicle.setColor(resultSet.getString("color"));
        vehicle.setVehicleType(resultSet.getString("vehicleType"));
        vehicle.setOdometer(resultSet.getInt("odometer"));
        vehicle.setPrice(resultSet.getDouble("price"));
        return vehicle;
    }
}
