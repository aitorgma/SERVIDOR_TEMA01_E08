
package com.mycompany.myapp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner teclado;
    static BdManager baseDatos;

    public static void main(String[] args) throws SQLException {
        inicio();
        boolean salir = false;
        do {
            int opcion = menu();
            switch (opcion) {
                case 1 -> alta();
                case 2 -> baja();
                case 3 -> modificar();
                case 4 -> consultarPorSalario();
                case 5 -> salir = true;
                default -> System.out.println("Opción invalida");
            }
        } while (!salir);
    }

    static int menu() {
        System.out.println("\n\n----CRUD EMPLEADO-----\n\n");
        mostrarTodos();
        System.out.println("\n1)ALTA");
        System.out.println("\n2)BAJA");
        System.out.println("\n3)MODIFICAR");
        System.out.println("\n4)CONSULTA");
        System.out.println("\n5)SALIR\n\n");
        return Integer.parseInt(teclado.nextLine());
    }

    static void inicio() {
        teclado = new Scanner(System.in);
        ArrayList<Empleado> empleados = FileManager.leerFichero();
        baseDatos = new BdManagerImpl();
        try {
            int regVaciarTabla = baseDatos.vaciarTabla();
            System.out.printf("Vaciado previo: %d registros\n", regVaciarTabla);
            int regCargaInicial = baseDatos.cargaInicial(empleados);
            System.out.printf("Carga inicial: %d registros\n", regCargaInicial);
        } catch (SQLException e) {
            System.out.println("Error en BD 1");
            e.printStackTrace();
        }
    }

    static void alta() {
        System.out.println("Introduce id");
        Long id = Long.parseLong(teclado.nextLine());
        System.out.println("Introduce nombre");
        String nombre = teclado.nextLine();
        System.out.println("Introduce salario");
        Double salario = Double.parseDouble(teclado.nextLine());
        try {
            int cantFilas = baseDatos.insertar(new Empleado(id, nombre, salario));
            System.out.printf("%d filas insertadas \n", cantFilas);
        } catch (SQLException e) {
            System.out.println("Error en BD 2");
            e.printStackTrace();
        }
    }

    static void baja() {
        System.out.println("Introduce id");
        Long id = Long.parseLong(teclado.nextLine());
        try {
            int cantFilas = baseDatos.borrar(id);
            System.out.printf("%d filas eliminadas\n", cantFilas);
        } catch (SQLException e) {
            System.out.println("Error en BD 3");
            e.printStackTrace();
        }
    }

    static void modificar() {
        System.out.println("Introduce id a modificar");
        Long id = Long.parseLong(teclado.nextLine());
        System.out.println("Introduce nombre");
        String nombre = teclado.nextLine();
        System.out.println("Introduce salario");
        Double salario = Double.parseDouble(teclado.nextLine());
        try {
            int cantFilas = baseDatos.actualizar(new Empleado(id, nombre, salario));
            System.out.printf("%d filas actualizadas\n", cantFilas);
        } catch (SQLException e) {
            System.out.println("Error en BD 4");
            e.printStackTrace();
        }
    }

    static void consultarPorSalario() {
        ArrayList<Empleado> resultado;
        System.out.println("Introduce salario mínimo");
        Double salarMin = Double.parseDouble(teclado.nextLine());
        System.out.println("Introduce salario máximo");
        Double salarMax = Double.parseDouble(teclado.nextLine());
        try {
            resultado = baseDatos.consultar(salarMin, salarMax);
            for (Empleado empleado : resultado)
                System.out.println(empleado);
        } catch (SQLException e) {
            System.out.println("Error en BD 5");
            e.printStackTrace();
        }
    }

    static void mostrarTodos() {
        ArrayList<Empleado> resultado;
        try {
            resultado = baseDatos.consultar();
            for (Empleado empleado : resultado)
                System.out.println(empleado);
        } catch (SQLException e) {
            System.out.println("Error en BD 5");
            e.printStackTrace();
        }
    }
}
