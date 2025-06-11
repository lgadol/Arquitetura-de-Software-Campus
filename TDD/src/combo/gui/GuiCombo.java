/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package combo.gui;

import combo.bd.DaoStringConexao;
import combo.bd.DaoStringConexaoMySQL;
import combo.bd.DaoStringConexaoPostgreSQL;
import combo.bo.BoConexao;
import combo.controller.CoCombo;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author dlnotari
 * @author pedro gado
 */

public class GuiCombo extends javax.swing.JDialog {
    // atributos
    private CoCombo co;
    private javax.swing.JLabel jLabelBancoDeDados;
    private javax.swing.JComboBox<String> jComboBancoDeDados;
    
 // NOVO CONSTRUTOR
    public GuiCombo(boolean modal) {
        super(new JFrame(), modal);
        initComponents(); // Este método desenha a tela
        
        this.co = null; 
        
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButtonInserirCliente = new javax.swing.JButton();
        jComboBoxCliente = new javax.swing.JComboBox();
        jButtonFechar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButtonExcluirCliente = new javax.swing.JButton();

        // --- CÓDIGO NOVO ---
        // 1. Instanciar e configurar os novos componentes
        jLabelBancoDeDados = new javax.swing.JLabel();
        jComboBancoDeDados = new javax.swing.JComboBox<>();

        jLabelBancoDeDados.setFont(new java.awt.Font("Tahoma", 1, 14)); // Definindo uma fonte
        jLabelBancoDeDados.setText("Banco de Dados:");

        jComboBancoDeDados.setFont(new java.awt.Font("Tahoma", 0, 14)); // Definindo uma fonte
        jComboBancoDeDados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PostgreSQL", "MySQL" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TDD: teste com combobox e jtable");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20));
        jLabel2.setText("Livro");
        jLabel2.setToolTipText("");

        jButtonInserirCliente.setFont(new java.awt.Font("Tahoma", 1, 18));
        jButtonInserirCliente.setText("Carregar");
        jButtonInserirCliente.setToolTipText("");
        jButtonInserirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserirClienteActionPerformed(evt);
            }
        });

        jComboBoxCliente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jComboBoxCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxCliente.setToolTipText("Selecione um ou mais clientes para a reserva deste horário!");

        jButtonFechar.setFont(new java.awt.Font("Tahoma", 1, 18));
        jButtonFechar.setText("Fechar");
        jButtonFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFecharActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20));
        jLabel5.setText("Livros e seus dados");
        jLabel5.setToolTipText("");

        jButtonExcluirCliente.setFont(new java.awt.Font("Tahoma", 1, 18));
        jButtonExcluirCliente.setText("Carregar");
        jButtonExcluirCliente.setToolTipText("");
        jButtonExcluirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirClienteActionPerformed(evt);
            }
        });

        // 2. MODIFICAR O LAYOUT
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // MODIFICAÇÃO NO GRUPO HORIZONTAL
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelBancoDeDados) // ADICIONADO
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED) // ADICIONADO
                        .addComponent(jComboBancoDeDados, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE) // ADICIONADO
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonInserirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jComboBoxCliente, 0, 582, Short.MAX_VALUE)
                                .addGap(131, 131, 131))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonExcluirCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jButtonFechar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );

        // MODIFICAÇÃO NO GRUPO VERTICAL
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25) // Espaçamento inicial
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE) // ADICIONADO: Grupo para alinhar o novo label e combo
                    .addComponent(jLabelBancoDeDados)
                    .addComponent(jComboBancoDeDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18) // Espaçamento entre os componentes
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonInserirCliente))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jButtonExcluirCliente))
                .addGap(48, 48, 48)
                .addComponent(jButtonFechar)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel5});

        pack();
    }

    private void jButtonInserirClienteActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        String bancoSelecionado = (String) jComboBancoDeDados.getSelectedItem();
        DaoStringConexao provedor;
        if ("MySQL".equals(bancoSelecionado)) {
            provedor = new DaoStringConexaoMySQL();
        } else {
            provedor = new DaoStringConexaoPostgreSQL();
        }

        try {
            System.out.println("Tentando conectar ao " + bancoSelecionado);
            // Cria um novo BoConexao para garantir que a conexão seja nova
            BoConexao boConexao = new BoConexao();
            boConexao.conectar(provedor);
            
            System.out.println("Conectado com sucesso!");

            // Cria o controller com a conexão ATIVA
            this.co = new CoCombo(this, boConexao);

            // Chama o método para carregar os livros
            this.getCo().carregarListaLivros();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Falha na conexão: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }  

    private void jButtonFecharActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
    }

    private void jButtonExcluirClienteActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        String bancoSelecionado = (String) jComboBancoDeDados.getSelectedItem();
        DaoStringConexao provedor;
        if ("MySQL".equals(bancoSelecionado)) {
            provedor = new DaoStringConexaoMySQL();
        } else {
            provedor = new DaoStringConexaoPostgreSQL();
        }

        try {
            System.out.println("Tentando conectar ao " + bancoSelecionado);
            BoConexao boConexao = new BoConexao();
            boConexao.conectar(provedor);

            // Cria o controller com a conexão ATIVA
            this.co = new CoCombo(this, boConexao);

            // Chama o método obterLista()
            this.getCo().obterLista();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Falha na conexão: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public CoCombo getCo() {
        return co;
    }

    public JComboBox getjComboBoxCliente() {
        return jComboBoxCliente;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExcluirCliente;
    private javax.swing.JButton jButtonFechar;
    private javax.swing.JButton jButtonInserirCliente;
    private javax.swing.JComboBox jComboBoxCliente;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
