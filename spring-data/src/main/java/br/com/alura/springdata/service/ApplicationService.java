package br.com.alura.springdata.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    private final CargoCrudService cargoCrudService;
    private final UnidadeTrabalhoCrudService unidadeTrabalhoCrudService;
    private final FuncionarioCrudService funcionarioCrudService;
    private final RelatorioService relatorioService;
    private final RelatorioDinamico relatorioDinamico;
    private Boolean system = true;

    public ApplicationService(CargoCrudService cargoCrudService, UnidadeTrabalhoCrudService unidadeTrabalhoCrudService,
            FuncionarioCrudService funcionarioCrudService, RelatorioService relatorioService, RelatorioDinamico relatorioDinamico) {
        this.cargoCrudService = cargoCrudService;
        this.unidadeTrabalhoCrudService = unidadeTrabalhoCrudService;
        this.funcionarioCrudService = funcionarioCrudService;
        this.relatorioService = relatorioService;
        this.relatorioDinamico = relatorioDinamico;
    }

    public void menuPrincipal(Scanner scan) {
        while (system) {
            System.out.println("\n === Menu Principal ===");
            System.out.println("Selecione uma opção");
            System.out.println("[1] Cargo");
            System.out.println("[2] Funcionario");
            System.out.println("[3] Unidade");
            System.out.println("[4] Relatorio");
            System.out.println("[5] Relatorio Dinâmico");
            System.out.println("[0] Sair");
            System.out.print("Opção: ");

            Integer menu = scan.nextInt();
            scan.nextLine();

            if (menu != 0) {
                opcoesPrincipal(menu, scan);
            } else {
                system = false;
            }
        }
    }

    private void opcoesPrincipal(Integer menu, Scanner scan) {
        switch (menu) {
            case 1 -> cargoCrudService.menuCargo(scan);
            case 2 -> funcionarioCrudService.menuFuncionario(scan);
            case 3 -> unidadeTrabalhoCrudService.menuUnidade(scan);
            case 4 -> relatorioService.menuRelatorio(scan);
            case 5 -> relatorioDinamico.menuRelatorioDinamico(scan);
            default -> System.out.println("\nOpção invalida");
        }
    }
}
