package combo.controller;

import combo.bd.E_BD;
import combo.bo.BoCombo;
import combo.bo.BoConexao;
import combo.gui.GuiCombo;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author Daniel
 * @author pedro gado
 */
public class CoCombo {
    // --- ATRIBUTOS ---
    private final BoCombo bo;
    private final GuiCombo gui;
    
    // --- ATRIBUTOS DE ESTADO DA PAGINAÇÃO ---
    private int paginaAtual = 1;
    private final int TAMANHO_PAGINA = 20;
    private int totalDePaginas = 0;

    // --- CONSTRUTOR ---
    public CoCombo(GuiCombo gui, BoConexao conexao) {
        this.gui = gui;
        this.bo = new BoCombo(conexao);
    }

    // --- MÉTODOS PÚBLICOS (CHAMADOS PELA GUI) ---

    /**
     * Ponto de entrada principal. Chamado pelo botão "Carregar".
     * Calcula o total de páginas e carrega a primeira.
     */
    public void carregarDadosIniciais() {
        try {
            // 1. Pede à camada de negócio para contar o total de registros no banco.
            int totalRegistros = bo.countTotalLivros();
            if (totalRegistros == 0) {
                JOptionPane.showMessageDialog(gui, "Nenhum livro encontrado no banco de dados.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // 2. Calcula o número total de páginas.
            this.totalDePaginas = (int) Math.ceil((double) totalRegistros / TAMANHO_PAGINA);
            
            // 3. Define a página atual como a primeira e carrega os dados.
            this.paginaAtual = 1;
            atualizarTabela();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gui, "Opa! Ocorreu um erro ao buscar os dados iniciais:\n" + e.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Chamado pelo botão "Próximo" da GUI.
     */
    public void proximaPagina() {
        if (paginaAtual < totalDePaginas) {
            paginaAtual++;
            atualizarTabela();
        }
    }

    /**
     * Chamado pelo botão "Anterior" da GUI.
     */
    public void paginaAnterior() {
        if (paginaAtual > 1) {
            paginaAtual--;
            atualizarTabela();
        }
    }

    // --- MÉTODO PRIVADO (LÓGICA CENTRAL) ---

    /**
     * O coração da paginação. Busca os dados da página atual e atualiza a JTable.
     */
    private void atualizarTabela() {
        try {
            // 1. Calcula o OFFSET para a consulta SQL.
            int offset = (paginaAtual - 1) * TAMANHO_PAGINA;

            // 2. Pede ao BO os dados paginados.
            ResultSet rs = bo.pesquisaDadosLivrosPaginado(TAMANHO_PAGINA, offset);

            // 3. Converte o ResultSet em um TableModel, que a JTable entende.
            TableModel tableModel = buildTableModel(rs);
            
            // 4. Atualiza a JTable na GUI com os novos dados.
            gui.getTabelaLivros().setModel(tableModel);
            
            // 5. Atualiza os componentes da GUI (label de página e botões).
            gui.atualizarControlesPaginacao(paginaAtual, totalDePaginas);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gui, "Não foi possível carregar a página " + paginaAtual + ":\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Método utilitário para converter um ResultSet em um DefaultTableModel.
     * @param rs O ResultSet vindo do banco.
     * @return um TableModel pronto para ser usado em uma JTable.
     * @throws SQLException
     */
    private static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // Nomes das colunas
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // Dados das linhas
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }


    // --- GETTERS ---
    public BoCombo getBo() {
        return bo;
    }

    public GuiCombo getGui() {
        return gui;
    }
}