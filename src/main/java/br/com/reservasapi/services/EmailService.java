package br.com.reservasapi.services;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviarEmail(String para, String assunto, String mensagem) {
        System.out.println("-----E-MAIL FAKE -----");
        System.out.println("Para: " + para);
        System.out.println("Assunto: " + assunto);
        System.out.println("Mensagem: " + mensagem);
        System.out.println("-------------------");
    }
}
