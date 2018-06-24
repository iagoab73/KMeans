package kmeans;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**,
 * KMeans - Classe principal.
 * @author Diego Victor, Gabriel Pires, Hugo Silva, Iago Lopes, Jean Silva, Raphael Avelar
 */
public class KMeans {

    public static HashMap<Integer, Pessoa> pessoas;
    public static double[] pivos;
    public static ArrayList<ArrayList<Integer>> gruposAtuais;
    public static ArrayList<ArrayList<Integer>> gruposAnteriores;

    public static void main(String[] args) {
        iniciarListas();
        lerArquivo();
        realizarKMeans();
        salvarGrupos();
    }

    public static void iniciarListas() {
        pessoas = new HashMap();
        gruposAnteriores = new ArrayList();
        iniciarGruposAtuais();
    }
    
    public static void iniciarGruposAtuais(){
        gruposAtuais = new ArrayList();
        gruposAtuais.add(new ArrayList());
        gruposAtuais.add(new ArrayList());
        gruposAtuais.add(new ArrayList());
        gruposAtuais.add(new ArrayList());
        gruposAtuais.add(new ArrayList());
        gruposAtuais.add(new ArrayList());
        gruposAtuais.add(new ArrayList());
    }

    public static void lerArquivo() {
        try {
            RandomAccessFile arquivo = new RandomAccessFile("Grupo de Pessoas.dat", "r");
            while (arquivo.read() != -1) {
                arquivo.seek(arquivo.getFilePointer() - 1);
                String linha = arquivo.readLine();
                int id = Integer.parseInt(linha.split("::")[0]);
                char sexo = linha.split("::")[1].charAt(0);
                int idade = Integer.parseInt(linha.split("::")[2]);
                int ocupacao = Integer.parseInt(linha.split("::")[3]);
                String codigoPostal = linha.split("::")[4];
                Pessoa novaPessoa = new Pessoa(id, sexo, idade, ocupacao, codigoPostal);
                pessoas.put(id, novaPessoa);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
        
    public static void realizarKMeans(){
        while (gruposDiferentes()) {
            gruposAnteriores = clonarGruposAtuais();
            setarPivos();
            iniciarGruposAtuais();
            for (int i = 1; i <= pessoas.size(); i++) {
                int grupo = encontrarMaisProximo(pessoas.get(i).getIdade());
                gruposAtuais.get(grupo).add(i);
            }
        }
    }

    public static boolean gruposDiferentes() {
        return !gruposAnteriores.equals(gruposAtuais);
    }

    public static ArrayList<ArrayList<Integer>> clonarGruposAtuais() {
        ArrayList<ArrayList<Integer>> clone = new ArrayList();
        for (ArrayList i : gruposAtuais) {
            ArrayList<Integer> grupo = new ArrayList();
            for (Object j : i) {
                grupo.add(Integer.parseInt(j.toString()));
            }
            clone.add(grupo);
        }
        return clone;
    }

    public static void setarPivos() {
        if (pivos == null) {
            pivos = new double[7];
            for (int i = 0; i < 7; i++) {
                pivos[i] = retornaRandom(i);
            }
        } else {
            for (int i = 0; i < 7; i++) {
                pivos[i] = calcularMedia(gruposAtuais.get(i));
            }
        }
    }

    public static int retornaRandom(int pivo) {
        int valor = -1;
        Random rand = new Random();
        do {
            valor = pessoas.get((rand.nextInt(pessoas.size()) + 1)).getIdade();
            for (int i = 0; i < pivo; i++) {
                if (valor == pivos[i]) {
                    valor = -1;
                }
            }
        } while (valor == -1);
        return valor;
    }

    public static double calcularMedia(ArrayList<Integer> grupo) {
        int soma = 0;
        for (Integer i : grupo) {
            soma += pessoas.get(i).getIdade();
        }
        if (grupo.isEmpty()) {
            return 0;
        }
        return soma / grupo.size();
    }

    public static int encontrarMaisProximo(int idade) {
        int maisProximo = 0;
        double distanciaMaisProximo = Math.abs(idade - pivos[0]);
        for (int i = 1; i < 7; i++) {
            double distancia = Math.abs(idade - pivos[i]);
            if (distancia < distanciaMaisProximo) {
                maisProximo = i;
                distanciaMaisProximo = distancia;
            }
        }
        return maisProximo;
    }
    
    public static void salvarGrupos(){
        for (ArrayList i : gruposAtuais) {
            int cont = 0;
            String idades = " ";
            for (Object j : i) {
                cont++;
                int chave = Integer.parseInt(j.toString());
                int idade = pessoas.get(chave).getIdade();
                idades = idades.concat(" " + idade);
            }
            idades = (cont + "\t| ").concat(idades);
            System.out.println(idades);
        }
    }
}
