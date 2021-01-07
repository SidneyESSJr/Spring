package br.com.alura.springdata.service;

import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.springdata.orm.Cargo;
import br.com.alura.springdata.repository.CargoRepository;

@Service
public class CargoCrudService {

    private final CargoRepository repository;
    public Boolean system;

    public CargoCrudService(CargoRepository repository) {
        this.repository = repository;
    }

    public void menuCargo(Scanner scan) {
        system = true;
        while (system) {
            System.out.println("\n === Menu de Cargos ===");
            System.out.println("Selecione uma opção");
            System.out.println("[1] Criar cargo");
            System.out.println("[2] Listar cargos");
            System.out.println("[3] Modificar cargo");
            System.out.println("[4] Remover cargo");
            System.out.println("[0] Menu principal");
            System.out.print("Opção: ");

            Integer menu = scan.nextInt();
            scan.nextLine();

            if (menu != 0) {
                opcoesCargo(menu, scan);
            } else {
                system = false;
            }
        }
    }

    public void opcoesCargo(Integer menu, Scanner scan) {
        switch (menu) {
            case 1 -> criar(scan);
            case 2 -> listar(scan);
            case 3 -> atualizar(scan);
            case 4 -> remover(scan);
            default -> System.out.println("\nOpção invalida");
        }
    }

    public void criar(Scanner scan) {
        System.out.println("\n === Novo cargo === ");
        Cargo cargo = new Cargo();
        System.out.print("Descrição: ");
        cargo.setDescricao(scan.nextLine());
        repository.save(cargo);
        System.out.println(" ** Novo cargo criado ** ");
        System.out.println("\nEnter para voltar ao menu");
        scan.nextLine();
    }

    public void listar(Scanner scan) {
        System.out.println("\n === Lista de cargos === ");

        System.out.print("Digite a pagina que deseja visualizar: ");
        int page = scan.nextInt() - 1;
        scan.nextLine();

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "descricao"));
        Page<Cargo> pages = repository.findAll(pageable);

        System.out.println("\nIndice " + (pages.getNumber() + 1) + "-" + pages.getTotalPages());

        pages.forEach(c -> System.out.println("\nId: " + c.getId() + "\n" + "Descrição: " + c.getDescricao()));

        System.out.println("\nTotal elementos: " + pages.getTotalElements());

        System.out.println("\nEnter para voltar ao menu");
        scan.nextLine();
    }

    public void atualizar(Scanner scan) {
        System.out.println("\n === Atualizar cargo === ");
        System.out.print("Id do cargo: ");
        Integer id = scan.nextInt();
        scan.nextLine();
        if (id != null) {
            Cargo cargo = repository.findById(id).get();
            System.out.print("Descricao: ");
            cargo.setDescricao(scan.nextLine());
            repository.save(cargo);
            System.out.println(" ** Cargo atualizado ** ");
            System.out.println("\nEnter para voltar ao menu");
            scan.nextLine();
        }
    }

    public void remover(Scanner scan) {
        System.out.println("\n === Remover Cargo ===");
        System.out.print("Id do cargo: ");
        Integer id = scan.nextInt();
        scan.nextLine();
        if (id != null) {
            Cargo cargo = repository.findById(id).get();
            repository.delete(cargo);
            System.out.println(" ** Cargo removido ** ");
            System.out.println("\nEnter para voltar ao menu");
            scan.nextLine();
        }
    }

    public Cargo findById(Integer id) {
        return repository.findById(id).get();
    }
}
