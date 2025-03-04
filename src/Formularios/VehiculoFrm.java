package Formularios;


import DbContext.ClienteCtx;
import DbContext.VehiculoCtx;
import Entidades.Clientes;
import Entidades.Vehiculos;
import Fuentes.Tipografia;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class VehiculoFrm extends javax.swing.JFrame {

    /**
     * Creates new form Matricula
     */
    
    VehiculoCtx vehiculoCtx = new VehiculoCtx();
    ClienteCtx clienteCtx = new ClienteCtx();
    
    public VehiculoFrm() {
        initComponents();
        
        Tipografia fuente = new Tipografia("Fuente.ttf", 14f);
        fuente.asignarFuente(this);
    }
    
    private void validarCampos(String credential, String registration) throws Exception {
        if (credential.isEmpty() || registration.isEmpty()) {
            throw new Exception("Los campos deben estar llenos.");
        }
    }

    private String formatRegistration(String registration) {
        return registration.replaceAll("\\s+", "").toUpperCase();
    }

    private Clientes obtenerCliente(String credential) throws Exception {
        Clientes client = clienteCtx.obtenerPorCredencial(credential);
        if (client == null) {
            throw new Exception("El cliente no existe.");
        }
        return client;
    }

    private void verificarVehiculoExistente(String registrationNoSpaces) throws Exception {
        Vehiculos existingVehicle = vehiculoCtx.obtenerPorMatricula(registrationNoSpaces);
        if (existingVehicle != null) {
            throw new Exception("Ya existe este vehículo.");
        }
    }

    private Vehiculos crearVehiculo(String registration, String type, Clientes client) {
        Vehiculos vehicle = new Vehiculos();
        vehicle.setId(UUID.randomUUID().toString());
        vehicle.setMatricula(registration);
        vehicle.setTipo(type);
        vehicle.setFkClienteId(client);
        return vehicle;
    }

    private void guardarVehiculo(Vehiculos vehicle)throws Exception {
        vehiculoCtx.guardar(vehicle);
    }

    private void limpiarCampos() {
        txt_credencial.setText("");
        txt_matricula.setText("");
    }

    private void mostrarMensajeExito(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }

    private void mostrarMensajeError(Exception e) {
        JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_credencial = new javax.swing.JTextField();
        txt_matricula = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbx_clase = new javax.swing.JComboBox<>();
        btn_guardar = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Geist Mono Light", 0, 12)); // NOI18N
        jLabel1.setText("Crear Vehiculo");

        jLabel2.setFont(new java.awt.Font("Geist Mono Light", 0, 12)); // NOI18N
        jLabel2.setText("Matricula:");

        jLabel4.setFont(new java.awt.Font("Geist Mono Light", 0, 12)); // NOI18N
        jLabel4.setText("Credencial Cliente:");

        txt_credencial.setFont(new java.awt.Font("Geist Mono Light", 0, 12)); // NOI18N

        txt_matricula.setFont(new java.awt.Font("Geist Mono Light", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Geist Mono Light", 0, 12)); // NOI18N
        jLabel5.setText("Clase:");

        cbx_clase.setFont(new java.awt.Font("Geist Mono Light", 0, 12)); // NOI18N
        cbx_clase.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Motoclicletas", "Automoviles", "Camionetas", "Furgonetas", "Camiones", "Buses", "Taxis", "Remolques", "Servicio Especial", "Hibridos" }));

        btn_guardar.setFont(new java.awt.Font("Geist Mono Light", 0, 12)); // NOI18N
        btn_guardar.setText("Guardar");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_credencial)
                            .addComponent(txt_matricula)
                            .addComponent(cbx_clase, 0, 293, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(btn_guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_credencial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_matricula, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbx_clase, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        try {
            String credencial = txt_credencial.getText();
            String registration = txt_matricula.getText();
            String type = (String) cbx_clase.getSelectedItem();

            validarCampos(credencial, registration);

            String matriculaSinEspacios = formatRegistration(registration);

            Clientes cliente = obtenerCliente(credencial);

            verificarVehiculoExistente(matriculaSinEspacios);

            Vehiculos vehiculo = crearVehiculo(matriculaSinEspacios, type, cliente);

            guardarVehiculo(vehiculo);

            limpiarCampos();

            mostrarMensajeExito("Vehiculo creado correctamente.");

        } catch (Exception e) {
            mostrarMensajeError(e);
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); 
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VehiculoFrm form = new VehiculoFrm();
                form.setVisible(true);
                form.setResizable(false);
                form.setLocationRelativeTo(null);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_guardar;
    private javax.swing.JComboBox<String> cbx_clase;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txt_credencial;
    private javax.swing.JTextField txt_matricula;
    // End of variables declaration//GEN-END:variables
}
