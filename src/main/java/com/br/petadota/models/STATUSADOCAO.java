package com.br.petadota.models;

public enum STATUSADOCAO {

	DISPONIVEL("Disponível"),
    EM_PROCESSO_ADOCAO("Em processo de adoção"),
    ADOTADO("Adotado");

    private final String descricao;

    STATUSADOCAO(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
