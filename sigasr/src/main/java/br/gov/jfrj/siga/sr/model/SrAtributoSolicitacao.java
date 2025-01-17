package br.gov.jfrj.siga.sr.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.gov.jfrj.siga.cp.model.HistoricoSuporte;
import br.gov.jfrj.siga.dp.DpLotacao;
import br.gov.jfrj.siga.dp.DpPessoa;
import br.gov.jfrj.siga.model.Assemelhavel;

@Entity
@Table(name="SR_ATRIBUTO_SOLICITACAO", schema = "SIGASR")
public class SrAtributoSolicitacao extends HistoricoSuporte {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(sequenceName = "SIGASR" +".SR_ATRIBUTO_SOLICITACAO_SEQ", name = "srAtributoSolicitacaoSeq")
	@GeneratedValue(generator = "srAtributoSolicitacaoSeq")
	@Column(name = "ID_ATRIBUTO_SOLICITACAO")
    private Long id;
	
	@Column(name = "VALOR_ATRIBUTO_SOLICITACAO")
    private String valorAtributoSolicitacao;
	
	@ManyToOne
	@JoinColumn(name="ID_ATRIBUTO")
    private SrAtributo atributo;
	
	@ManyToOne
	@JoinColumn(name="ID_SOLICITACAO")
    private SrSolicitacao solicitacao;
	
	@ManyToOne
	@JoinColumn(name = "ID_CADASTRANTE")
	private DpPessoa cadastrante;
	
	@ManyToOne
	@JoinColumn(name = "ID_LOTA_CADASTRANTE")
	private DpLotacao lotaCadastrante;
	
	@ManyToOne
	@JoinColumn(name="HIS_ID_INI", insertable = false, updatable = false)
	private SrAtributoSolicitacao atributoSolicitacaoInicial;
	
	@OneToMany(targetEntity = SrAtributoSolicitacao.class, mappedBy = "atributoSolicitacaoInicial", fetch = FetchType.LAZY)
	@OrderBy("hisDtIni desc")
	private List<SrAtributoSolicitacao> meuAtributoSolicitacaoHistorico; 
	
	public SrAtributoSolicitacao(){
	}
	
	public SrAtributoSolicitacao(SrAtributo tipo, String valor, SrSolicitacao sol){
		this.setAtributo(tipo);
		this.setValorAtributoSolicitacao(valor);
		this.setSolicitacao(sol);
	}
	
    public SrAtributoSolicitacao(String valorAtributoSolicitacao,
			SrAtributo atributo, SrSolicitacao solicitacao,
			DpPessoa cadastrante, DpLotacao lotaCadastrante) {
		this.valorAtributoSolicitacao = valorAtributoSolicitacao;
		this.atributo = atributo;
		this.solicitacao = solicitacao;
		this.cadastrante = cadastrante;
		this.lotaCadastrante = lotaCadastrante;
	}

	public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValorAtributoSolicitacao() {
        return valorAtributoSolicitacao;
    }

    public void setValorAtributoSolicitacao(String valorAtributoSolicitacao) {
        this.valorAtributoSolicitacao = valorAtributoSolicitacao;
    }

    public SrAtributo getAtributo() {
        return atributo;
    }

    public void setAtributo(SrAtributo atributo) {
        this.atributo = atributo;
    }

    public SrSolicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(SrSolicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

	public SrAtributoSolicitacao getAtributoSolicitacaoInicial() {
		return atributoSolicitacaoInicial;
	}

	public void setAtributoSolicitacaoInicial(SrAtributoSolicitacao atributoSolicitacaoInicial) {
		this.atributoSolicitacaoInicial = atributoSolicitacaoInicial;
	}

	public List<SrAtributoSolicitacao> getMeuAtributoSolicitacaoHistorico() {
		return meuAtributoSolicitacaoHistorico;
	}

	public void setMeuAtributoSolicitacaoHistorico(List<SrAtributoSolicitacao> meuAtributoSolicitacaoHistorico) {
		this.meuAtributoSolicitacaoHistorico = meuAtributoSolicitacaoHistorico;
	}
	
	public DpPessoa getCadastrante() {
		return cadastrante;
	}

	public void setCadastrante(DpPessoa cadastrante) {
		this.cadastrante = cadastrante;
	}

	public DpLotacao getLotaCadastrante() {
		return lotaCadastrante;
	}

	public void setLotaCadastrante(DpLotacao lotaCadastrante) {
		this.lotaCadastrante = lotaCadastrante;
	}

	@Override
	public boolean semelhante(Assemelhavel obj, int profundidade) {
		return false;
	}
	
	public List<SrAtributoSolicitacao> getHistoricoAtributoSolicitacao() {
		if (getAtributoSolicitacaoInicial() != null)
			return getAtributoSolicitacaoInicial().getMeuAtributoSolicitacaoHistorico();
		return null;
	}
	
	public SrAtributoSolicitacao getAtual() {
		if (isAtivo())
			return this;
		List<SrAtributoSolicitacao> att = getHistoricoAtributoSolicitacao();
		if (att == null)
			return null;
		return att.get(0);
	}
}
