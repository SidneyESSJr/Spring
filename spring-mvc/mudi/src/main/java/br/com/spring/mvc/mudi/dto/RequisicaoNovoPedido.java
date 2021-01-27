package br.com.spring.mvc.mudi.dto;

import javax.validation.constraints.NotBlank;

import br.com.spring.mvc.mudi.model.Pedido;

public class RequisicaoNovoPedido {

    @NotBlank(message = "O campo nome do produto é obrigatório")
    private String nomeProduto;

    @NotBlank(message = "O campo url do produto é obrigatório")
    private String urlProduto;

    @NotBlank(message = "O campo url da imagem é obrigatório")
    private String urlImagem;
    private String descricao;

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getUrlProduto() {
        return urlProduto;
    }

    public void setUrlProduto(String urlProduto) {
        this.urlProduto = urlProduto;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pedido toPedido() {
        Pedido pedido = new Pedido();
        pedido.setNomeProduto(nomeProduto);
        pedido.setUrlProduto(urlProduto);
        pedido.setUrlImagem(urlImagem);
        pedido.setDescricao(descricao);
        return pedido;
    }

}
