package combo.dao;

import combo.bo.BoConexao;
import combo.bd.E_BD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dlnotari
 * @author pedro gado
 */
public class DaoCombo {
    // atributos
    private BoConexao conexao;  

    // construtor
    public DaoCombo(BoConexao conexao) {
        this.conexao = conexao;
    }  
    
    /**
     * 
     * @return
     * @throws SQLException
     * @throws E_BD
     * @throws ClassNotFoundException 
     */
    public ResultSet listaLivros() throws SQLException, E_BD, ClassNotFoundException {
        if (this.getConexao() == null || this.getConexao().getBd() == null) {
            throw new E_BD("Erro Crítico: A camada de acesso a dados (DAO) recebeu uma conexão não inicializada.");
        }
        
        // consultar o código
        String sql = "select titulo from livros";

        // obtem objeto
        PreparedStatement ps = this.getConexao().getBd().getStatement(sql);
        
        // executar sql
        ResultSet rs = this.getConexao().getBd().consulta(ps);

        // return
        return rs;
    }  

    /**
     * 
     * @return
     * @throws SQLException
     * @throws E_BD
     * @throws ClassNotFoundException 
     */
 // DaoCombo.java

    // Método modificiado para pesquisa
    public ResultSet pesquisaDadosLivrosPaginado(int limit, int offset) throws SQLException, E_BD, ClassNotFoundException {
        if (this.getConexao() == null || this.getConexao().getBd() == null) {
            throw new E_BD("Erro Crítico: A camada de acesso a dados (DAO) recebeu uma conexão não inicializada.");
        }

        String sgbd = this.getConexao().getConexao().getVoConexao().getSgbd();
        String sql;

        if (sgbd.equalsIgnoreCase("PostgreSQL") || sgbd.equalsIgnoreCase("postgres")) {
            // Para PostgreSQL
            sql = "SELECT l.titulo, "
                + "   string_agg(DISTINCT a.nome, ', ') as autores, "
                + "   COUNT(e.numero) as n_edicoes, "
                + "   MAX(e.ano) as ultimo_ano "
                + "FROM livros l "
                + "LEFT JOIN livroAutor la ON l.codigo = la.codigoLivro "
                + "LEFT JOIN autor a ON a.codigo = la.codigoAutor "
                + "LEFT JOIN edicao e ON l.codigo = e.codigoLivro "
                + "GROUP BY l.codigo, l.titulo "
                + "ORDER BY l.titulo "
                + "LIMIT ? OFFSET ?";

        } else {
            // Para MySQL
            sql = "SELECT l.titulo, "
                + "   GROUP_CONCAT(DISTINCT a.nome SEPARATOR ', ') as autores, "
                + "   COUNT(e.numero) as n_edicoes, "
                + "   MAX(e.ano) as ultimo_ano "
                + "FROM livros l "
                + "LEFT JOIN livroAutor la ON l.codigo = la.codigoLivro "
                + "LEFT JOIN autor a ON a.codigo = la.codigoAutor "
                + "LEFT JOIN edicao e ON l.codigo = e.codigoLivro "
                + "GROUP BY l.codigo, l.titulo "
                + "ORDER BY l.titulo "
                + "LIMIT ? OFFSET ?";
        }

        PreparedStatement ps = this.getConexao().getBd().getStatement(sql);
        ps.setInt(1, limit);
        ps.setInt(2, offset);

        return this.getConexao().getBd().consulta(ps);
    }
    
    // Novo método para contar o total de registros
    public int countTotalLivros() throws SQLException, E_BD, ClassNotFoundException {
        String sql = "select count(*) from livros";
        PreparedStatement ps = this.getConexao().getBd().getStatement(sql);
        ResultSet rs = this.getConexao().getBd().consulta(ps);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
    
    // getter
    public BoConexao getConexao() {
        return conexao;
    }
}
