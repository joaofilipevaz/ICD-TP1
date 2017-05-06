package modelo;

import java.util.ArrayList;

/**
 * Created by Mónica on 26/04/2017.
 */
public class Conta {

    private String numConta;
    private String nib;
    private String iban;
    private int idCliente;
    private double saldoContabilistico;
    private double saldoDisponivel;
    private double saldoAutorizado;
    private ArrayList<Movimento> movimentos;


    public Conta(String numConta, int idCliente, double saldoContabilistico) {
        this.numConta = numConta;
        this.idCliente = idCliente;
        this.saldoContabilistico = saldoContabilistico;
    }


    public String getNib() {
        return nib;
    }

    public void setNib(String nib) {
        this.nib = nib;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getSaldoContabilistico() {
        return saldoContabilistico;
    }

    public void setSaldoContabilistico(double saldoContabilistico) {
        this.saldoContabilistico = saldoContabilistico;
    }

    public double getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(double saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public double getSaldoAutorizado() {
        return saldoAutorizado;
    }

    public void setSaldoAutorizado(double saldoAutorizado) {
        this.saldoAutorizado = saldoAutorizado;
    }

    public String getNumConta() {
        return numConta;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public ArrayList<Movimento> getMovimentos() {
        return movimentos;
    }

    public void setMovimentos(ArrayList<Movimento> movimentos) {
        this.movimentos = movimentos;
    }
}
