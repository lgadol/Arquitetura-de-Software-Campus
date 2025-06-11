package combo.bd;

import combo.vo.VoConexao;

/**
 @author pedro gado
**/

public class DaoStringConexaoMySQL implements DaoStringConexao {

    @Override
    public String getStringConexao(VoConexao vo) {
        String url = "jdbc:mysql://" + vo.getHost() +
                ":" + vo.getPorta() + "/" + vo.getBaseDados() +
                "?useTimezone=true&serverTimezone=UTC";

        System.out.println("Conectando em: " + url);
        return url;
    }

    @Override
    public VoConexao getConfiguracaoDefault() {
        return getConfiguracaoAlternativa();
    }

    @Override
    public VoConexao getConfiguracaoAlternativa() {
        VoConexao vo = new VoConexao();

        // Dados para a conexão MySQL
        vo.setSgbd("MySQL");
        vo.setHost("localhost");
        vo.setPorta("3306");
        vo.setBaseDados("livros");
        vo.setUsuario("root"); 
        vo.setSenha("admin");
        vo.setClassDriver("com.mysql.cj.jdbc.Driver");

        return vo;
    }
}