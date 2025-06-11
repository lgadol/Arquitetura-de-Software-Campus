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
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnProximo;
    private javax.swing.JLabel lblInfoPagina;
    private javax.swing.JTable tabelaLivros;
    private javax.swing.JScrollPane scrollPaneTabela;
    
 // NOVO CONSTRUTOR
    public GuiCombo(boolean modal) {
        super(new JFrame(), modal);
        initComponents(); // Este método desenha a tela
        
        this.co = null; 
        
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // jComboBoxCliente = new javax.swing.JComboBox();
        // jLabel2 = new javax.swing.JLabel(); // Relacionado ao JComboBox

        // --- Componentes principais ---
        jLabelBancoDeDados = new javax.swing.JLabel();
        jComboBancoDeDados = new javax.swing.JComboBox<>();
        jButtonInserirCliente = new javax.swing.JButton();
        jButtonFechar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        // --- Componentes da Tabela e Paginação ---
        tabelaLivros = new javax.swing.JTable();
        scrollPaneTabela = new javax.swing.JScrollPane(tabelaLivros);
        btnAnterior = new javax.swing.JButton("Anterior");
        btnProximo = new javax.swing.JButton("Próximo");
        lblInfoPagina = new javax.swing.JLabel("Página - de -");

        // --- Configuração dos Componentes ---
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta Paginada de Livros");

        jLabelBancoDeDados.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabelBancoDeDados.setText("Banco de Dados:");
        jComboBancoDeDados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PostgreSQL", "MySQL" }));

        // O botão principal agora é "Buscar Dados"
        jButtonInserirCliente.setFont(new java.awt.Font("Tahoma", 1, 14));
        jButtonInserirCliente.setText("Buscar Dados");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Livros Cadastrados");

        lblInfoPagina.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButtonFechar.setFont(new java.awt.Font("Tahoma", 1, 14));
        jButtonFechar.setText("Fechar");
        
        try {
            jButtonInserirCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lupa.png")));
            btnAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anterior.png")));
            btnProximo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proximo.png")));
        } catch (Exception ex) {
            System.err.println("Erro ao carregar os ícones: " + ex.getMessage());
        }

        // --- Action Listeners ---
        // O botão de carregar agora chama o método para carregar a primeira página.
        // A lógica de conexão foi movida para o Controller.
        jButtonInserirCliente.addActionListener(evt -> {
            // A lógica de conexão e criação do controller aqui.
            String bancoSelecionado = (String) jComboBancoDeDados.getSelectedItem();
            DaoStringConexao provedor;
            if ("MySQL".equals(bancoSelecionado)) {
                provedor = new DaoStringConexaoMySQL();
            } else {
                provedor = new DaoStringConexaoPostgreSQL();
            }

            try {
                BoConexao boConexao = new BoConexao();
                boConexao.conectar(provedor);
                this.co = new CoCombo(this, boConexao);
                
                // Chama o método correto no controller
                this.co.carregarDadosIniciais();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Falha na conexão: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAnterior.addActionListener(evt -> {
            if (co != null) co.paginaAnterior();
        });
        btnProximo.addActionListener(evt -> {
            if (co != null) co.proximaPagina();
        });
        jButtonFechar.addActionListener(evt -> this.setVisible(false));
        
        // --- DEFINIÇÃO DO LAYOUT (GroupLayout) ---
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelBancoDeDados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBancoDeDados, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonInserirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblInfoPagina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProximo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonFechar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBancoDeDados, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBancoDeDados, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonInserirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnterior)
                    .addComponent(btnProximo)
                    .addComponent(lblInfoPagina))
                .addGap(18, 18, 18)
                .addComponent(jButtonFechar)
                .addGap(20, 20, 20))
        );

        pack();
    }
    
    /* DOIS MÉTODOS ANTIGOS QUE NÃO SÃO MAIS UTILIZADOS
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
    }*/

    public CoCombo getCo() {
        return co;
    }

    public JComboBox getjComboBoxCliente() {
        return jComboBoxCliente;
    }
    
    // Método para atualizar os controles
    public void atualizarControlesPaginacao(int paginaAtual, int totalPaginas) {
        lblInfoPagina.setText("Página " + paginaAtual + " de " + totalPaginas);
        btnAnterior.setEnabled(paginaAtual > 1);
        btnProximo.setEnabled(paginaAtual < totalPaginas);
    }
    
    /**
     * Ajusta a largura de cada coluna de uma JTable com base no conteúdo
     * da célula mais larga daquela coluna.
     * @param table A JTable a ser ajustada.
     */
    public void ajustarLarguraColunas(javax.swing.JTable table) {
        // Desativa o redimensionamento automático para definir larguras customizadas
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        
        for (int column = 0; column < table.getColumnCount(); column++) {
            javax.swing.table.TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            
            // Itera sobre cada linha para encontrar a largura da célula mais larga
            for (int row = 0; row < table.getRowCount(); row++) {
                javax.swing.table.TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                java.awt.Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);
            }

            // Define a largura preferencial da coluna com um pouco de espaço extra (padding)
            tableColumn.setPreferredWidth(preferredWidth + 15);
        }
    }
    
    public javax.swing.JTable getTabelaLivros() {
        return tabelaLivros;
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
