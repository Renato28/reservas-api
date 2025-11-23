package br.com.reservasapi.services;

import br.com.reservasapi.dto.DashBoardResumoDto;
import br.com.reservasapi.repositories.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    private final ReservaRepository reservaRepository;

    public DashboardService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public DashBoardResumoDto getResumo() {
        LocalDate hoje = LocalDate.now();
        DashBoardResumoDto resumo = new DashBoardResumoDto();

        resumo.setCheckIns(reservaRepository.countByDataCheckIn(hoje));
        resumo.setCheckOuts(reservaRepository.countByDataCheckOut(hoje));
        resumo.setHospedesAtuais(reservaRepository.countHospedesAtuais(hoje));

        Long totalReservas = reservaRepository.countTotalReservas();
        int taxaOcupacao = totalReservas != 0 ? (int)((resumo.getHospedesAtuais() * 100) / totalReservas) : 0;
        resumo.setTaxaOcupacao(taxaOcupacao);

        List<String> labels = new ArrayList<>();
        List<Integer> dadosCheckIns = new ArrayList<>();
        List<Integer> dadosCheckOuts = new ArrayList<>();

        LocalDate startOfWeek = hoje.with(DayOfWeek.MONDAY);
        for (int i = 0; i < 7; i++) {
            LocalDate dia = startOfWeek.plusDays(i);
            labels.add(dia.getDayOfWeek().toString().substring(0, 3));

            Long checkInsDia = reservaRepository.countByDataCheckIn(dia);
            Long checkOutsDia = reservaRepository.countByDataCheckOut(dia);

            dadosCheckIns.add(checkInsDia.intValue());
            dadosCheckOuts.add(checkOutsDia.intValue());
        }

        resumo.setGraficoLabels(labels);
        resumo.setGraficoCheckIns(dadosCheckIns);
        resumo.setGraficoCheckOuts(dadosCheckOuts);

        return resumo;
    }
}
