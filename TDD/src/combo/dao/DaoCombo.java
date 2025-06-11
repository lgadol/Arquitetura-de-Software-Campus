/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

        // A sintaxe pode variar um pouco entre PostgreSQL e MySQL
        // PostgreSQL e MySQL (versões mais novas) suportam LIMIT e OFFSET.
        String sql = "select l.titulo, a.nome, e.numero, e.ano"
                + "   from livros l inner join livroAutor la on l.codigo = la.codigoLivro "
                + "   inner join autor a on a.codigo = la.codigoAutor"
                + "   inner join edicao e on a.codigo = e.codigoLivro"
                + "   order by l.titulo" // É importante ter um ORDER BY para resultados consistentes
                + "   LIMIT ? OFFSET ?";

        PreparedStatement ps = this.getConexao().getBd().getStatement(sql);
        ps.setInt(1, limit);   // Define o limite de registros
        ps.setInt(2, offset);  // Define o ponto de partida

        ResultSet rs = this.getConexao().getBd().consulta(ps);
        return rs;
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
