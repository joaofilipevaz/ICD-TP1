package modelo;


import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 Sobre os clientes titulares de uma ou mais contas bancárias é mantida informação
 sobre:  nome  completo,  documento  identificação  (cartão  cidadão,  passaporte,  …),
 data  nascimento,  assinatura  e fotografia.
 */
public class Cliente {

    private String nomeCliente;
    private int bi;
    private int nif;
    private LocalDate dateOfBirth;
    private String morada;
    private int numTelefone;
    private int numConta;
    private ArrayList<Conta> contas;
    private Image foto;
    private Image assinatura;

    public Cliente(String nomeCliente, int bi, int nif, Image foto, Image assinatura, int ano, int mes, int dia) {
        this.nomeCliente = nomeCliente;
        this.bi = bi;
        this.nif = nif;
        this.foto = foto;
        this.assinatura = assinatura;
        this.dateOfBirth = LocalDate.of(ano, mes , dia);
        contas = new ArrayList<Conta>();
    }

    public int getBi() {
        return bi;
    }

    public void setBi(int bi) {
        this.bi = bi;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ArrayList<Conta> getContas() {
        return contas;
    }

    public void setContas(ArrayList<Conta> contas) {
        this.contas = contas;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public Image getFoto() {
        return foto;
    }

    public void setFoto(Image foto) {
        this.foto = foto;
    }

    public Image getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(Image assinatura) {
        this.assinatura = assinatura;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getNumTelefone() {
        return numTelefone;
    }

    public void setNumTelefone(int numTelefone) {
        this.numTelefone = numTelefone;
    }

    public int getNumConta() {
        return numConta;
    }

    public void setNumConta(int numConta) {
        this.numConta = numConta;
    }
}
