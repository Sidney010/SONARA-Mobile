package com.example.sonara.features.home.model


// ── Opções de filtro / ordenação ──────────────────────────────────────────────
enum class EventoFiltro(val label: String) {
    PADRAO("Padrão"),
    POR_DATA("Por data"),
    POR_HORA("Por hora"),
    POR_ARTISTA("Com artista"),
    POR_LOCALIZACAO("Por localização")
}