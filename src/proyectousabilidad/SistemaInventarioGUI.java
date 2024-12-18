/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectousabilidad;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;

public class SistemaInventarioGUI {
      private static final String ARCHIVO = "inventario.txt";
   private static final java.util.List<Producto> INVENTARIO = new java.util.ArrayList<>();
    private static DefaultTableModel modeloTabla;

    public static void iniciarSistema() {
        cargarInventario();
        SwingUtilities.invokeLater(() -> crearVentanaPrincipal());
      
}
     private static void crearVentanaPrincipal() {
        JFrame ventana = new JFrame("Sistema de Inventario");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(600, 400);
        ventana.setLayout(new BorderLayout());

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Cantidad", "Precio"}, 0);
        JTable tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        ventana.add(scrollPane, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Producto");
        JButton btnActualizar = new JButton("Actualizar Cantidad");
        JButton btnEliminar = new JButton("Eliminar Producto");
        JButton btnGuardar = new JButton("Guardar y Salir");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGuardar);
        ventana.add(panelBotones, BorderLayout.SOUTH);

        // Cargar productos en la tabla
        actualizarTabla();

        // Eventos de botones
        btnAgregar.addActionListener(e -> agregarProducto());
        btnActualizar.addActionListener(e -> actualizarCantidad(tabla));
        btnEliminar.addActionListener(e -> eliminarProducto(tabla));
        btnGuardar.addActionListener(e -> {
            guardarInventario();
            ventana.dispose();
        });

        ventana.setVisible(true);
    }

    private static void cargarInventario() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                INVENTARIO.add(Producto.fromString(linea));
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar el inventario. Se creará uno nuevo.");
        }
    }

    private static void guardarInventario() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Producto p : INVENTARIO) {
                bw.write(p.toString());
                bw.newLine();
            }
            JOptionPane.showMessageDialog(null, "Inventario guardado correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el inventario.");
        }
    }

    private static void agregarProducto() {
        JTextField campoID = new JTextField();
        JTextField campoNombre = new JTextField();
        JTextField campoCantidad = new JTextField();
        JTextField campoPrecio = new JTextField();

        Object[] campos = {
                "ID:", campoID,
                "Nombre:", campoNombre,
                "Cantidad:", campoCantidad,
                "Precio:", campoPrecio
        };

        int opcion = JOptionPane.showConfirmDialog(null, campos, "Agregar Producto", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(campoID.getText());
                String nombre = campoNombre.getText();
                int cantidad = Integer.parseInt(campoCantidad.getText());
                double precio = Double.parseDouble(campoPrecio.getText());
                INVENTARIO.add(new Producto(id, nombre, cantidad, precio));
                actualizarTabla();
                JOptionPane.showMessageDialog(null, "Producto agregado correctamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Datos inválidos. Intente de nuevo.");
            }
        }
    }

    private static void actualizarCantidad(JTable tabla) {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nuevaCantidad = JOptionPane.showInputDialog("Nueva cantidad:");
            try {
                int cantidad = Integer.parseInt(nuevaCantidad);
               INVENTARIO.get(filaSeleccionada).setCantidad(cantidad);
                actualizarTabla();
                JOptionPane.showMessageDialog(null, "Cantidad actualizada.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor inválido.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para actualizar.");
        }
    }

    private static void eliminarProducto(JTable tabla) {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            INVENTARIO.remove(filaSeleccionada);
            actualizarTabla();
            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar.");
        }
    }

    private static void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : INVENTARIO) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getCantidad(), p.getPrecio()});
        }
    }
    
}
