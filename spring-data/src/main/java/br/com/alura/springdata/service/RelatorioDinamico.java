package br.com.alura.springdata.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.orm.UnidadeTrabalho;
import br.com.alura.springdata.repository.FuncionarioRepository;
import br.com.alura.springdata.repository.UnidadeTrabalhoRepository;
import br.com.alura.springdata.specification.SpecificationFuncionario;
import br.com.alura.springdata.specification.SpecificationUnidadeTrabalho;

@Service
public class RelatorioDinamico {

    private final FuncionarioRepository fRepository;
    private final UnidadeTrabalhoRepository uRepository;
    private Boolean system = true;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RelatorioDinamico(FuncionarioRepository fRepository, UnidadeTrabalhoRepository uRepository) {
        this.fRepository = fRepository;
        this.uRepository = uRepository;
    }

    public void menuRelatorioDinamico(Scanner scan) {
        system = true;
        while (system) {
            System.out.println("\n === Menu de relatorios dinâmicos ===");
            System.out.println("[1] Relatorio dinâmico funcionario");
            System.out.println("[2] Relatorio dinâmico unidade de trabalho");
            System.out.println("[0] Menu principal");
            System.out.print("Opção: ");

            int menu = scan.nextInt();
            scan.nextLine();

            if (menu != 0) {
                opcoesRelatorioDinamico(menu, scan);
            } else {
                system = false;
            }
        }
    }

    public void opcoesRelatorioDinamico(int menu, Scanner scan) {
        switch (menu) {
            case 1 -> relatorioDinamicoFuncionario(scan);
            case 2 -> relatorioDinamicoUnidade(scan);
            default -> System.out.println("\nOpção invalida");
        }
    }

    private void relatorioDinamicoFuncionario(Scanner scan) {
        System.out.println("\n === Relatorio dinâmico funcionario ===");

        System.out.print("Nome: ");
        String nome = scan.nextLine();

        System.out.print("cpf: ");
        String cpf = scan.nextLine();

        System.out.print("Salario: ");
        Double salario = scan.nextDouble();
        scan.nextLine();

        System.out.print("Data contratação: ");
        String data = scan.nextLine();

        LocalDate dataContratacao;
        if (data.equalsIgnoreCase("NULL") || data.isEmpty()) {
            dataContratacao = null;
        } else {
            dataContratacao = LocalDate.parse(data, formatter);
        }

        List<Funcionario> funcionarios = fRepository.findAll(Specification
                .where(SpecificationFuncionario.consultaCompletaFuncionario(nome, cpf, salario, dataContratacao)));

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
    }

    private void relatorioDinamicoUnidade(Scanner scan) {
        System.out.println("\n === Relatorio dinâmico unidade de trabalho ===");

        System.out.print("Descrição: ");
        String descricao = scan.nextLine();

        System.out.print("Endereço: ");
        String endereco = scan.nextLine();

        List<UnidadeTrabalho> unidades = uRepository.findAll(
                Specification.where(SpecificationUnidadeTrabalho.consultaCompletaUnidadeTrabalho(descricao, endereco)));

        if (!unidades.isEmpty()) {
            unidades.forEach(u -> {
                System.out.println("\nDescrição: " + u.getDescricao() + "\nEndereço: " + u.getEndereco());
            });
        } else {
            System.out.println("** Unidade de trabalho não encontrada **");
        }

        System.out.println("\nEnter para voltar ao menu");
        scan.nextLine();
    }

}
