// ResumoVendedor -> Interface para receber os dados da query personalizada

package com.xbrain.api.vendascontrol.repositories;

public interface ResumoVendedor {
    String getNomeVendedor();
    Integer getTotalVendas();
    Double getMedia();
}
