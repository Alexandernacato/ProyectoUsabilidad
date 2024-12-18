/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectousabilidad;

/**
 *
 * @author alexa
 */
public class Producto {
     private int id;
    private String nombre;
    private int cantidad;
    private double precio;

    public Producto(int id, String nombre, int cantidad, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
}
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    @Override
    public String toString() {
        return id + "," + nombre + "," + cantidad + "," + precio;
    }

    public static Producto fromString(String linea) {
        String[] datos = linea.split(",");
        return new Producto(Integer.parseInt(datos[0]), datos[1], Integer.parseInt(datos[2]), Double.parseDouble(datos[3]));
    }
}
