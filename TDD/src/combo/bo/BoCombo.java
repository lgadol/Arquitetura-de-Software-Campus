package combo.bo;

import combo.bd.E_BD;
import combo.dao.DaoCombo;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Camada de Negócio (Business Object).
 * Repassa as solicitações do Controller para a camada DAO.
 * @author Daniel
 * @author pedro gado
 */
public class BoCombo {
    // Atributos
    private final BoConexao conexao;
    private final DaoCombo dao;

    // Construtor (continua o mesmo, está correto)
    public BoCombo(BoConexao conexao) {
        this.conexao = conexao;
        this.dao = new DaoCombo(conexao);
    }

    // --- NOVOS MÉTODOS PARA SUPORTE À PAGINAÇÃO ---

    /**
     * Repassa a chamada para contar o total de livros para o DAO.
     * @return O número total de registros.
     */
    public int countTotalLivros() throws SQLException, E_BD, ClassNotFoundException {
        return this.getDao().countTotalLivros();
    }
    
    /**
     * Repassa a chamada de busca paginada para o DAO.
     * @param limit O número de registros por página.
     * @param offset O ponto de partida da busca.
     * @return Um ResultSet com os dados da página atual.
     */
    public ResultSet pesquisaDadosLivrosPaginado(int limit, int offset) throws SQLException, E_BD, ClassNotFoundException {
        return this.getDao().pesquisaDadosLivrosPaginado(limit, offset);
    }
    
    // --- MÉTODOS ANTIGOS (Não são mais usados pela nova lógica de paginação) ---
    
    /**
     * MÉTODO ANTIGO: obtem lista de livros para o JComboBox.
     * Não é mais necessário para a JTable paginada.
     */
    public ResultSet listaLivros() throws SQLException, E_BD, ClassNotFoundException {
        return this.getDao().listaLivros();
    }
    
    /**
     * MÉTODO ANTIGO: obtem lista completa de livros e seus dados.
     * Foi substituído pela versão paginada.
     */
    /*public ResultSet pesquisaDadosLivros() throws SQLException, E_BD, ClassNotFoundException {
        return this.getDao().pesquisaDadosLivros();
    }*/
    
    // --- GETTERS (continuam os mesmos) ---
    
    public BoConexao getConexao() {
        return conexao;
    }

    public DaoCombo getDao() {
        return dao;
    }
}