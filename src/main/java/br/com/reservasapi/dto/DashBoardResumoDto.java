package br.com.reservasapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardResumoDto {

    private Long checkIns;
    private Long checkOuts;
    private Long hospedesAtuais;
    private Integer taxaOcupacao;
    private List<String> graficoLabels;
    private List<Integer> graficoCheckIns;
    private List<Integer> graficoCheckOuts;

}
