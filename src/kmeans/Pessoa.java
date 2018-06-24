package kmeans;

/**
 *
 * @author Iago
 */
public class Pessoa {
    private int id;
    private char sexo;
    private int idade;
    private int ocupacao;
    private String codigoPostal;

    public Pessoa(int id, char sexo, int idade, int ocupacao, String codigoPostal) {
        this.id = id;
        this.sexo = sexo;
        this.idade = idade;
        this.ocupacao = ocupacao;
        this.codigoPostal = codigoPostal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(int ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
