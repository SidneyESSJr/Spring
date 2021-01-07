package br.com.alura.springdata.service;

import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.springdata.orm.UnidadeTrabalho;
import br.com.alura.springdata.repository.UnidadeTrabalhoRepository;

@Service
public class UnidadeTrabalhoCrudService {

    private final UnidadeTrabalhoRepository repository;
    public Boolean system;

    public UnidadeTrabalhoCrudService(UnidadeTrabalhoRepository repository) {
        this.repository = repository;
    }

    public void menuUnidade(Scanner scan) {
        system = true;
        while (system) {
            System.out.println("\n === Menu de unidades ===");
            System.out.println("Selecione uma opção");
            System.out.println("[1] Criar unidade");
            System.out.println("[2] Listar unidades");
            System.out.println("[3] Modificar unidade");
            System.out.println("[4] Remover unidade");
            System.out.println("[0] Menu principal");
            System.out.print("Opção: ");

            Integer menu = scan.nextInt();
            scan.nextLine();

            if (menu != 0) {
                opcoesUnidade(menu, scan);
            } else {
                system = false;
            }
        }
    }

    private void opcoesUnidade(Integer menu, Scanner scan) {
        switch (menu) {
            case 1 -> criar(scan);
            case 2 -> listar(scan);
            case 3 -> atualizar(scan);
            case 4 -> remover(scan);
            default -> System.out.println("\nOpção invalida");
        }
    }

    public void criar(Scanner scan) {
        System.out.println("\n === Nova unidade === ");
        UnidadeTrabalho unidade = new UnidadeTrabalho();
        System.out.print("Descrição: ");
        unidade.setDescricao(scan.nextLine());
        System.out.print("Endereço: ");
        unidade.setEndereco(scan.nextLine());
        repository.save(unidade);
        System.out.println(" ** Novo unidade criada ** ");
        System.out.println("\nEnter para voltar ao menu");
        scan.nextLine();
    }

    public void listar(Scanner scan) {
        System.out.println("\n === Lista de unidades === ");
        System.out.print("Digite a pagina que deseja visualizar: ");
        int page = scan.nextInt() - 1;
        scan.nextLine();

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "descricao"));

        Page<UnidadeTrabalho> pages = repository.findAll(pageable);

        System.out.println("\nIndice " + (pages.getNumber() + 1) + "-" + pages.getTotalPages());

        pages.forEach(u -> {
            System.out.println(
                    "\nId: " + u.getId() + "\nDescrição: " + u.getDescricao() + "\nEndereço: " + u.getEndereco());
        });

        System.out.println("\nTotal elementos: " + pages.getTotalElements());

        System.out.println("\nEnter para voltar ao menu");
        scan.nextLine();
    }

    public void atualizar(Scanner scan) {
        System.out.println("\n === Atualizar unidade === ");
        System.out.print("Id da unidade: ");
        Integer id = scan.nextInt();
        scan.nextLine();
        if (id != null) {
            UnidadeTrabalho unidade = repository.findById(id).get();
            System.out.print("Descricao: ");
            unidade.setDescricao(scan.nextLine());
            System.out.print("Endereço: ");
            unidade.setEndereco(scan.nextLine());
            repository.save(unidade);
            System.out.println(" ** Unidade atualizada ** ");
            System.out.println("\nEnter para voltar ao menu");
            scan.nextLine();
        }
    }

    public void remover(Scanner scan) {
        System.out.println("\n === Remover unidade ===");
        System.out.print("Id da unidade: ");
        Integer id = scan.nextInt();
        scan.nextLine();
        if (id != null) {
            UnidadeTrabalho unidade = repository.findById(id).get();
            repository.delete(unidade);
            System.out.println(" ** Unidade removida ** ");
            System.out.println("\nEnter para voltar ao menu");
            scan.nextLine();
        }
    }
}
