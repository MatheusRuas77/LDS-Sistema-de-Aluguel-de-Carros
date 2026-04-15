package com.carrental.dto;

import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDateTime;
import java.util.List;

@Serdeable
public class PedidoRequest {

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private List<Long> automovelIds;

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public List<Long> getAutomovelIds() {
        return automovelIds;
    }

    public void setAutomovelIds(List<Long> automovelIds) {
        this.automovelIds = automovelIds;
    }
}