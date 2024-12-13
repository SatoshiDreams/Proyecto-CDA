/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Cronometro {
    
    public Timer temporizador; 
    public int segundos, minutos, horas;

    public Cronometro() {
        temporizador = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTiempo(); 
            }
        });
    }

    public void iniciarCronometro() {
        temporizador.start();
    }

    public void reiniciarCronometro() {
        segundos = 0;
        minutos = 0;
        horas = 0;
    }

    public void actualizarTiempo() {
        segundos++;
        if (segundos == 60) {
            segundos = 0;
            minutos++;
        }
        if (minutos == 60) {
            minutos = 0;
            horas++;
        }
    }

    public String tiempo() {
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    
    
}
