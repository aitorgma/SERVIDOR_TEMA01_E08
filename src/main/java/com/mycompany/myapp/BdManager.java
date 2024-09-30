package com.mycompany.myapp;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BdManager {

    int vaciarTabla() throws SQLException;

    int cargaInicial(ArrayList<Empleado> empleados) throws SQLException;

    ArrayList<Empleado> consultar() throws SQLException;

    ArrayList<Empleado> consultar(double minSalar, double maxSalar) throws SQLException;

    int insertar(Empleado empleado) throws SQLException;

    int borrar(Long id) throws SQLException;

    int actualizar(Empleado empleado) throws SQLException;
}
