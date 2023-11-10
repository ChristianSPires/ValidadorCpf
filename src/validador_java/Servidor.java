/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validador_java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author christian.pires
 */
public class Servidor {
    
    private static Socket socket;
    private static ServerSocket server;
    
    private static DataInputStream entrada;
    private static DataOutputStream saida;
    
    public static boolean recebeCPF(String cpf) {
        // VERIFICA SE O CPF É TODO COMPOSTO POR 0 OU SE RECEBEU UM VALOR COM + OU - DIGITOS
        if (cpf.equals("00000000000") || cpf.length() != 11) {
            return false;
        }

        char priVerif, segVerif;
        int soma, i, num, multi, resto;

        try {
            // PRIMEIRO DIGITO VERIFICADOR
            soma = 0;
            multi = 10;
            // FOR QUE PASSA POR CADA NUMERO DO CPF
            for (i = 0; i < 9; i++) {
                // TRANSFORMA O i DO LOOP ATUAL EM INT
                num = (int) (cpf.charAt(i) - 48);
                // MULTIPLICA O i ATUAL E ADICIONA O VALOR NA VARIAVEL SOMA
                soma = soma + (num * multi);
                // DIMINUI O MULTIPLICADOR ATUAL ANTES DO PROXIMO LOOP
                multi = multi - 1;
            }
            // CALCULO PARA SABER SE O RESTO É MAIOR OU IGUAL A 2
            resto = 11 - (soma % 11);
            if((resto == 10) || (resto == 11)){
                priVerif = '0';
            }
            else{
                priVerif = (char) (resto + 48);
            }

            // SEGUNDO DIGITO VERIFICADOR
            soma = 0;
            multi = 11;
            // FOR QUE PASSA POR CADA NUMERO DO CPF
            for (i = 0; i < 10; i++) {
                // TRANSFORMA O i DO LOOP ATUAL EM INT
                num = (int) (cpf.charAt(i) - 48);
                // MULTIPLICA O i ATUAL E ADICIONA O VALOR NA VARIAVEL SOMA
                soma = soma + (num * multi);
                // DIMINUI O MULTIPLICADOR ATUAL ANTES DO PROXIMO LOOP
                multi = multi - 1;
            }
            // CALCULO PARA SABER SE O RESTO É MAIOR OU IGUAL A 2
            resto = 11 - (soma % 11);
            if((resto == 10) || (resto == 11)){
                segVerif = '0';
            }
            else{
                segVerif = (char) (resto + 48);
            }

            return (priVerif == cpf.charAt(9)) && (segVerif == cpf.charAt(10));

        } catch (Exception e) {
            return false;
        }
    }
    
    public static void main(String[] args) {
        try{
            server = new ServerSocket(50000);
            socket = server.accept();
            
            entrada = new DataInputStream(socket.getInputStream());
            saida = new DataOutputStream(socket.getOutputStream());
            
            String cpf = entrada.readUTF();
            boolean cpfValido = recebeCPF(cpf);
            String mensagem;

            if (cpfValido) {
                mensagem = "Este CPF é válido";
            } else {
                mensagem = "Este CPF é inválido";
            }

            saida.writeUTF(mensagem);
            
            socket.close();
        } catch (Exception e){
            
        }
    }
}
