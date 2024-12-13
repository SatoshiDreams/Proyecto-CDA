/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fuentes;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.io.InputStream;
import javax.swing.JOptionPane;


public class Tipografia {
    private Font fuentePersonalizada;
    
    public Tipografia(String rutaFuente, float tamanoFuente) {
        inicializarFuente(rutaFuente, tamanoFuente);
    }
    
    private void inicializarFuente(String rutaFuente, float tamanoFuente) {
        try (InputStream flujoEntrada = getClass().getResourceAsStream(rutaFuente)) {
            if (flujoEntrada != null) {
                fuentePersonalizada = Font.createFont(Font.TRUETYPE_FONT, flujoEntrada).deriveFont(tamanoFuente);
            } else {
                throw new Exception("No se encontró la fuente: " + rutaFuente);
            }
        } catch (Exception excepcion) {
            JOptionPane.showMessageDialog(null, excepcion.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void asignarFuente(Component componente) {
      if (fuentePersonalizada != null) {
            componente.setFont(fuentePersonalizada); 
            if (componente instanceof Container) { 
                for (Component comp : ((Container) componente).getComponents()) {
                    asignarFuente(comp); 
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No fue posible cargar la fuente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}
