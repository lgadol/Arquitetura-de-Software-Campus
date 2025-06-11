package combo.bd;

import combo.vo.VoConexao;
import java.sql.*;

/**
 * @author felipe.bogo
 * @author pedro gado 
 **/

public class DaoConectarBD {

    private VoConexao voConexao;
    private Connection conexao;

    private DaoConectarBD(VoConexao voConexao, Connection conexao) {
        this.voConexao = voConexao;
        this.conexao = conexao;
    }

    public DaoConectarBD() throws E_BD, ClassNotFoundException, SQLException {
       this(null, null);
    }

   /* MÉTODO ANTIGO
    public Connection conectar() throws E_BD, java.lang.ClassNotFoundException, SQLException {
        // pegar configuração padrão
        this.voConexao = new DaoStringConexaoPostgreSQL().getConfiguracaoAlternativa();

        // testa dados da conexão, se não existem gera exceção
        if ((this.getVoConexao() == null) || (this.getVoConexao().getBaseDados() == null) || (this.getVoConexao().getHost() == null) || (this.getVoConexao().getPorta() == null) || (this.getVoConexao().getSenha() == null) || (this.getVoConexao().getSgbd() == null) || (this.getVoConexao().getUsuario() == null)) {
            throw new E_BD("Não foi possível conectar com o SGBD com as" +
                    " informações " + this.getVoConexao());
        }

        // realiza conexão e carrega driver
        DaoStringConexao conexaoConfig = new DaoStringConexaoPostgreSQL();

        // pega configuração da conexao
        String url = conexaoConfig.getStringConexao(this.getVoConexao());

        // carrega o Driver
        Class.forName(this.getVoConexao().getClassDriver());

        // faz a conexao com o SGBD
        conexao = DriverManager.getConnection(url,
                this.getVoConexao().getUsuario(),
                this.getVoConexao().getSenha());
        conexao.setAutoCommit(false);

        // retorna conexao
        return conexao;
    }
    */
    
    // MÉTODO NOVO PARA DUAS CONEXÕES
    public Connection conectar(DaoStringConexao provedorConexao) throws E_BD, java.lang.ClassNotFoundException, SQLException {
        // Pega a configuração do provedor que foi passado como parâmetro (seja ele MySQL ou PostgreSQL)
        this.voConexao = provedorConexao.getConfiguracaoAlternativa();

        // testa dados da conexão, se não existem gera exceção (código existente)
        if ((this.getVoConexao() == null) || (this.getVoConexao().getBaseDados() == null) || (this.getVoConexao().getHost() == null) || (this.getVoConexao().getPorta() == null) || (this.getVoConexao().getSenha() == null) || (this.getVoConexao().getSgbd() == null) || (this.getVoConexao().getUsuario() == null)) {
            throw new E_BD("Não foi possível conectar com o SGBD com as" +
                    " informações " + this.getVoConexao());
        }

        // Pega a string de conexão usando o provedor correto
        String url = provedorConexao.getStringConexao(this.getVoConexao());

        // Carrega o Driver (o nome da classe vem do VoConexao, que foi preenchido corretamente)
        Class.forName(this.getVoConexao().getClassDriver());

        // Faz a conexao com o SGBD (código existente)
        conexao = DriverManager.getConnection(url,
                this.getVoConexao().getUsuario(),
                this.getVoConexao().getSenha());
        conexao.setAutoCommit(false);

        // Retorna conexao
        return conexao;
    }

    public void desConectar() throws SQLException {
        conexao.close();
    }

    public VoConexao getVoConexao() {
        return voConexao;
    }

    public void setVoConexao(VoConexao voConexao) {
        this.voConexao = voConexao;
    }

    public Connection getConexao() {
        return conexao;
    }
}
