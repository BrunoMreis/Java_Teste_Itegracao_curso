package br.com.alura.leilao.util.builder;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

public class LeilaoBuilder {

	private String nomeLeiloado;
	private BigDecimal valorLeiloadoInicial;
	private LocalDate dataLeilao;
	private Usuario usuario;

	public LeilaoBuilder comNome(String nomeLeiloado) {
		this.nomeLeiloado = nomeLeiloado;
		return this;
	}

	public LeilaoBuilder comValorInicial(String valorLeiloadoInicial) {
		this.valorLeiloadoInicial = new BigDecimal(valorLeiloadoInicial);
		return this;
	}

	public LeilaoBuilder comData(LocalDate dataLeilao) {
		this.dataLeilao = dataLeilao;
		return this;
	}

	public LeilaoBuilder comUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	public Leilao criar() {
		
		return new Leilao(nomeLeiloado, valorLeiloadoInicial, dataLeilao, usuario);
	}

}
