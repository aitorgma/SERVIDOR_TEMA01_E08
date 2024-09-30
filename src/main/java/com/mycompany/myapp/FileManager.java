package com.mycompany.myapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FileManager {

    public static ArrayList<Empleado> leerFichero() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        String archivoCSV = "archivos/empleados.csv";  // Cambia por la ruta de tu archivo CSV
        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                Long id = Long.parseLong(datos[0]);
                String nombre = datos[1];
                Double salario = Double.parseDouble(datos[2]);
                empleados.add(new Empleado(id, nombre, salario));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return empleados;
    }
}