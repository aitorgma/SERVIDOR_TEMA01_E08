package com.mycompany.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BdManagerImpl implements BdManager {

    private final String URL = "jdbc:sqlite:C:/Users/AGM/Desktop/RecursosCurso/CrudEmpleadosJdbc/archivos/empleados.db";

    @Override
    public int vaciarTabla() throws SQLException {
        int cantFilas = 0;
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM empleados");
            cantFilas = ps.executeUpdate();
            ps.close();
        }
        return cantFilas;
    }

    @Override
public int cargaInicial(ArrayList<Empleado> empleados) throws SQLException {
    String sql = "INSERT INTO empleados(id, nombre, salario) VALUES (?, ?, ?)";
    int cantFilas = 0;
    try (Connection connection = DriverManager.getConnection(URL)) {
        PreparedStatement ps = connection.prepareStatement(sql);
        for (Empleado emp : empleados) {
            ps.setLong(1, emp.getId());
            ps.setString(2, emp.getNombre());
            ps.setDouble(3, emp.getSalario());
            cantFilas += ps.executeUpdate();
        }
        ps.close();
    }
    return cantFilas;
}

@Override
public ArrayList<Empleado> consultar() throws SQLException {
    ArrayList<Empleado> empleados = new ArrayList<>();
    String sql = "SELECT * FROM empleados";
    try (Connection connection = DriverManager.getConnection(URL)) {
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Long id = rs.getLong("id");
            String nombre = rs.getString("nombre");
            Double salario = rs.getDouble("salario");
            empleados.add(new Empleado(id, nombre, salario));
        }
        ps.close();
    }
    return empleados;
}

@Override
public ArrayList<Empleado> consultar(double minSalar, double maxSalar) throws SQLException {
    ArrayList<Empleado> empleados = new ArrayList<>();
    String sql = "SELECT * FROM empleados WHERE salario BETWEEN ? AND ?";
    try (Connection connection = DriverManager.getConnection(URL)) {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1, minSalar);
        ps.setDouble(2, maxSalar);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Long id = rs.getLong("id");
            String nombre = rs.getString("nombre");
            Double salario = rs.getDouble("salario");
            empleados.add(new Empleado(id, nombre, salario));
        }
        ps.close();
    }
    return empleados;
}

@Override
public int insertar(Empleado empleado) throws SQLException {
    String sql = "INSERT INTO empleados(id, nombre, salario) VALUES (?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(URL)) {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, empleado.getId());
        ps.setString(2, empleado.getNombre());
        ps.setDouble(3, empleado.getSalario());
        int cantFilas = ps.executeUpdate();
        ps.close();
        return cantFilas;
    }
}
@Override
public int borrar(Long id) throws SQLException {
    String sql = "DELETE FROM empleados WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(URL)) {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        int cantFilas = ps.executeUpdate();
        ps.close();
        return cantFilas;
    }
}

@Override
public int actualizar(Empleado empleado) throws SQLException {
    String sql = "UPDATE empleados SET nombre = ?, salario = ? WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(URL)) {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, empleado.getNombre());
        ps.setDouble(2, empleado.getSalario());
        ps.setLong(3, empleado.getId());
        int cantFilas = ps.executeUpdate();
        ps.close();
        return cantFilas;
    }
}


}
