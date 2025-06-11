package combo.bo;

import combo.bd.DaoConectarBD;
import combo.bd.DaoConsultarBD;
import combo.bd.DaoStringConexao; // IMPORTANTE: Importar a interface
import combo.bd.E_BD;
import java.sql.SQLException;

/**
 *
 * @author daniel
 * @author pedro gado
 */
public class BoConexao {
    private DaoConectarBD conexao;
    private DaoConsultarBD bd;

    // NOVO CONSTRUTOR 
    public BoConexao() {
        // this.setConexao(new DaoConectarBD());
        // this.setBd(new DaoConsultarBD(this.getConexao()));
    }
    
    /**
     * @param provedor A configuração do banco de dados (Postgres ou MySQL).
     */
    // MÉTODO CONECTAR MODIFICADO
    // Agora ele recebe o provedor (Postgres ou MySQL) e passa para o DAO.
    public void conectar(DaoStringConexao provedor) throws E_BD, ClassNotFoundException, SQLException {
        this.setConexao(new DaoConectarBD());
        this.setBd(new DaoConsultarBD(this.getConexao()));

        this.getConexao().conectar(provedor);
    }

    public void desconectar() throws SQLException {
        if (this.getConexao() != null && this.getConexao().getConexao() != null) {
            this.getConexao().desConectar();
        }
    }

    public DaoConectarBD getConexao() {
        return conexao;
    }

    private void setConexao(DaoConectarBD conexao) {
        this.conexao = conexao;
    }

    public DaoConsultarBD getBd() {
        return bd;
    }

    private void setBd(DaoConsultarBD bd) {
        this.bd = bd;
    }
}