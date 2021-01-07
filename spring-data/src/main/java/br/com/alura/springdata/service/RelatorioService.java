package br.com.alura.springdata.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.orm.projecoes.FuncionarioProjecao1;
import br.com.alura.springdata.repository.FuncionarioRepository;

@Service
public class RelatorioService {

    private final FuncionarioRepository fRepository;
    private Boolean system = true;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RelatorioService(FuncionarioRepository fRepository) {
        this.fRepository = fRepository;
    }

    public void menuRelatorio(Scanner scan) {
        system = true;
        while (system) {
            System.out.println("\n === Menu de relatorios ===");
            System.out.println("[1] Funcionario por nome");
            System.out.println("[2] Funcionario por nome salario e data adminsão");
            System.out.println("[3] Funcionario por data adminsão");
            System.out.println("[4] Funcionario id, nome, salario");
            System.out.println("[0] Menu principal");
            System.out.print("Opção: ");

            int menu = scan.nextInt();
            scan.nextLine();

            if (menu != 0) {
                opcoesRelatorio(menu, scan);
            } else {
                system = false;
            }
        }
    }

    public void opcoesRelatorio(int menu, Scanner scan) {
        switch (menu) {
            case 1 -> relatorioPorNome(scan);
            case 2 -> relatorioPorNomeSalarioAdmisao(scan);
            case 3 -> relatorioPorDataAdmisao(scan);
            case 4 -> relatorioIdNomeSalario(scan);
            default -> System.out.println("\nOpção invalida");
        }
    }

    private void relatorioIdNomeSalario(Scanner scan) {
        System.out.println("\n === Relatorio Id / Nome / Salario ===");

        List<FuncionarioProjecao1> funcionarios = fRepository.findFuncionarioProjecao1();

        if (!funcionarios.isEmpty()) {
            funcionarios.forEach(f -> {
                System.out.println(
                        "\nUsuarioId: " + f.getId() + "\nNome: " + f.getNome() + "\nSalario: " + f.getSalario());
            });
        } else {
            System.out.println("** Nenhum funcionario encontrado **");
        }

    }

    public List<Funcionario> relatorioPorNome(Scanner scan) {
        System.out.println("\n === Relatorio por nome ===");
        System.out.print("Nome: ");
        String nome = scan.nextLine();

        List<Funcionario> funcionarios = fRepository.findByNomeContains(nome);

        if (!funcionarios.isEmpty()) {
            funcionarios.forEach(f -> {
                System.out.println("\nCPF: " + f.getCpf() + "\nNome: " + f.getNome() + "\nSalario: " + f.getSalario()
                        + "\nCargo: " + f.getCargo().getDescricao() + "\nData admisão: " + f.getDataContratacao());
            });
        } else {
            System.out.println("** Funcionario não encontrado **");
        }

        System.out.println("\nEnter para voltar ao menu");
        scan.nextLine();

        return funcionarios;
    }

    public List<Funcionario> relatorioPorNomeSalarioAdmisao(Scanner scan) {
        System.out.println("\n === Relatorio por nome ===");
        System.out.print("Nome: ");
        String nome = scan.nextLine();
        System.out.print("Salario maior que: ");
        Double salario = scan.nextDouble();
        scan.nextLine();
        System.out.print("Data admisão: ");
        String data = scan.nextLine();
        LocalDate dataADM = null;

        try {
            dataADM = LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("\nData invalida");
        }

        List<Funcionario> funcionarios = fRepository.findByNomeDataSalarioDataContratacao(nome, salario, dataADM);

        if (!funcionarios.isEmpty()) {
            funcionarios.forEach(f -> {
                System.out.println("\nCPF: " + f.getCpf() + "\nNome: " + f.getNome() + "\nSalario: " + f.getSalario()
                        + "\nCargo: " + f.getCargo().getDescricao() + "\nData admisão: " + f.getDataContratacao());
            });
        } else {
            System.out.println("** Funcionario não encontrado **");
        }

        System.out.println("\nEnter para voltar ao menu");
        scan.nextLine();

        return funcionarios;
    }

    public List<Funcionario> relatorioPorDataAdmisao(Scanner scan) {
        System.out.println("\n === Relatorio por data de admisão ===");
        System.out.print("Data admisão maior que: ");
        String data = scan.nextLine();
        LocalDate dataADM = null;

        try {
            dataADM = LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("\nData invalida");
        }

        List<Funcionario> funcionarios = fRepository.findByDataAdmisaoMaior(dataADM);

        if (!funcionarios.isEmpty()) {
            funcionarios.forEach(f -> {
                System.out.println("\nCPF: " + f.getCpf() + "\nNome: " + f.getNome() + "\nSalario: " + f.getSalario()
                        + "\nCargo: " + f.getCargo().getDescricao() + "\nData admisão: " + f.getDataContratacao());
            });
        } else {
            System.out.println("** Funcionario não encontrado **");
        }

        System.out.println("\nEnter para voltar ao menu");
        scan.nextLine();

        return funcionarios;
    }

}
