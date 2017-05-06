package modelo;

import java.time.LocalDate;


public class Movimento {

    private String descricao;
    private LocalDate dataValor;
    private LocalDate dataLancamento;
    private double valor;
    private TipoMovimento tipo;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataValor() {
        return dataValor;
    }

    public void setDataValor(LocalDate dataValor) {
        this.dataValor = dataValor;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoMovimento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimento tipo) {
        this.tipo = tipo;
    }

    public Movimento(String descricao, LocalDate dataValor, LocalDate dataLancamento, double valor, TipoMovimento tipo) {
        this.descricao = descricao;
        this.dataValor = dataValor;
        this.dataLancamento = dataLancamento;
        this.valor = valor;
        this.tipo = tipo;
    }

}
