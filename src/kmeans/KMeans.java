package kmeans;

import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;




public class KMeans
{
  public static HashMap<Integer, Pessoa> pessoas;
  public static double[] pivos;
  public static ArrayList<Integer> grupoUm;
  public static ArrayList<Integer> grupoDois;
  public static ArrayList<Integer> grupoTres;
  public static ArrayList<Integer> grupoQuatro;
  public static ArrayList<Integer> grupoCinco;
  public static ArrayList<Integer> grupoSeis;
  public static ArrayList<Integer> grupoSete;
  public static ArrayList<ArrayList<Integer>> gruposAtuais;
  public static ArrayList<ArrayList<Integer>> gruposAnteriores;
  
  public KMeans() {}
  
  public static void main(String[] args)
  {
    iniciarListas();
    lerArquivo();
    int grupo; while (gruposDiferentes()) {
      gruposAnteriores = clonarGruposAtuais();
      setarPivos();
      resetaGrupos();
      for (int i = 1; i <= pessoas.size(); i++) {
        grupo = encontrarMaisProximo(((Pessoa)pessoas.get(Integer.valueOf(i))).getIdade());
        ((ArrayList)gruposAtuais.get(grupo)).add(Integer.valueOf(i));
      }
    }
    int cont = 0;
    for (ArrayList i : gruposAtuais) {
      String idades = " ";
      for (Object j : i) {
        cont++;
        int chave = Integer.parseInt(j.toString());
        int idade = ((Pessoa)pessoas.get(Integer.valueOf(chave))).getIdade();
        idades = idades.concat(" " + idade);
      }
      System.out.println(idades);
    }
    System.out.println(cont);
  }
  
  public static void iniciarListas()
  {
    pessoas = new HashMap();
    grupoUm = new ArrayList();
    grupoDois = new ArrayList();
    grupoTres = new ArrayList();
    grupoQuatro = new ArrayList();
    grupoCinco = new ArrayList();
    grupoSeis = new ArrayList();
    grupoSete = new ArrayList();
    gruposAnteriores = new ArrayList();
    gruposAtuais = new ArrayList();
    gruposAtuais.add(grupoUm);
    gruposAtuais.add(grupoDois);
    gruposAtuais.add(grupoTres);
    gruposAtuais.add(grupoQuatro);
    gruposAtuais.add(grupoCinco);
    gruposAtuais.add(grupoSeis);
    gruposAtuais.add(grupoSete);
  }
  
  public static void lerArquivo() {
    try {
      RandomAccessFile arquivo = new RandomAccessFile("Grupo de Pessoas.dat", "r");
      while (arquivo.read() != -1) {
        arquivo.seek(arquivo.getFilePointer() - 1L);
        String linha = arquivo.readLine();
        Pessoa p = new Pessoa(Integer.parseInt(linha.split("::")[0]), linha.split("::")[1].charAt(0), Integer.parseInt(linha.split("::")[2]), Integer.parseInt(linha.split("::")[3]), linha.split("::")[4]);
        pessoas.put(Integer.valueOf(p.getId()), p);
      }
    } catch (IOException|NumberFormatException ex) {
      System.out.println("ERRO");
    }
  }
  
  public static boolean gruposDiferentes() {
    return !gruposAnteriores.equals(gruposAtuais);
  }
  
  public static ArrayList<ArrayList<Integer>> clonarGruposAtuais() {
    ArrayList<ArrayList<Integer>> clone = new ArrayList();
    for (ArrayList i : gruposAtuais) {
      ArrayList<Integer> lista = new ArrayList();
      for (Object j : i) {
        lista.add(Integer.valueOf(Integer.parseInt(j.toString())));
      }
      clone.add(lista);
    }
    return clone;
  }
  
  public static void resetaGrupos() {
    grupoUm = new ArrayList();
    grupoDois = new ArrayList();
    grupoTres = new ArrayList();
    grupoQuatro = new ArrayList();
    grupoCinco = new ArrayList();
    grupoSeis = new ArrayList();
    grupoSete = new ArrayList();
    gruposAtuais = new ArrayList();
    gruposAtuais.add(grupoUm);
    gruposAtuais.add(grupoDois);
    gruposAtuais.add(grupoTres);
    gruposAtuais.add(grupoQuatro);
    gruposAtuais.add(grupoCinco);
    gruposAtuais.add(grupoSeis);
    gruposAtuais.add(grupoSete);
  }
  
  public static void setarPivos() {
    if (pivos == null) {
      pivos = new double[7];
      pivos[0] = retornaRandom(0);
      pivos[1] = retornaRandom(1);
      pivos[2] = retornaRandom(2);
      pivos[3] = retornaRandom(3);
      pivos[4] = retornaRandom(4);
      pivos[5] = retornaRandom(5);
      pivos[6] = retornaRandom(6);
    } else {
      pivos[0] = calcularMedia(grupoUm);
      pivos[1] = calcularMedia(grupoDois);
      pivos[2] = calcularMedia(grupoTres);
      pivos[3] = calcularMedia(grupoQuatro);
      pivos[4] = calcularMedia(grupoCinco);
      pivos[5] = calcularMedia(grupoSeis);
      pivos[6] = calcularMedia(grupoSete);
    }
  }
  
  public static int retornaRandom(int pivo) {
    int valor = -1;
    Random rand = new Random();
    do {
      valor = ((Pessoa)pessoas.get(Integer.valueOf(rand.nextInt(pessoas.size()) + 1))).getIdade();
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
      soma += ((Pessoa)pessoas.get(i)).getIdade();
    }
    if (grupo.isEmpty()) {
      return 0.0D;
    }
    return soma / grupo.size();
  }
  
  public static int encontrarMaisProximo(int idade)
  {
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
}