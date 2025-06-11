package combo.bo;

import combo.bd.E_BD;
import combo.dao.DaoCombo;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Daniel
 * @author pedro gado
 */
public class BoCombo {
    // Atributos
    private final BoConexao conexao;
    private final DaoCombo dao;

    // Construtor
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
    
    public ResultSet listaLivros() throws SQLException, E_BD, ClassNotFoundException {
        return this.getDao().listaLivros();
    }
    
    public BoConexao getConexao() {
        return conexao;
    }

    public DaoCombo getDao() {
        return dao;
    }
}