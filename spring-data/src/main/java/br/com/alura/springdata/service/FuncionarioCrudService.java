package br.com.alura.springdata.service;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.springdata.orm.Cargo;
import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.orm.UnidadeTrabalho;
import br.com.alura.springdata.repository.CargoRepository;
import br.com.alura.springdata.repository.FuncionarioRepository;
import br.com.alura.springdata.repository.UnidadeTrabalhoRepository;

@Service
public class FuncionarioCrudService {

    private final FuncionarioRepository repository;
    private final CargoRepository cargoRepository;
    private final UnidadeTrabalhoRepository unidadeRepository;
    public Boolean system;

    public FuncionarioCrudService(FuncionarioRepository repository, CargoRepository cargoRepository,
            UnidadeTrabalhoRepository unidadeRepository) {
        this.repository = repository;
        this.cargoRepository = cargoRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public void menuFuncionario(Scanner scan) {
        system = true;
        while (system) {
            System.out.println("\n === Menu de funcionarios ===");
            System.out.println("Selecione uma opção");
            System.out.println("[1] Criar funcionario");
            System.out.println("[2] Listar funcionarios");
            System.out.println("[3] Modificar funcionario");
            System.out.println("[4] Remover funcionario");
            System.out.println("[0] Menu principal");
            System.out.print("Opção: ");

            Integer menu = scan.nextInt();
            scan.nextLine();

            if (menu != 0) {
                opcoesFuncionario(menu, scan);
            } else {
                system = false;
            }
        }
    }

    private void opcoesFuncionario(Integer menu, Scanner scan) {
        switch (menu) {
            case 1 -> criar(scan);
            case 2 -> listar(scan);
            case 3 -> atualizar(scan);
            case 4 -> remover(scan);
            default -> System.out.println("\nOpção invalida");
        }
    }

    public void criar(Scanner scan) {
        System.out.println("\n === Novo funcionario === ");
        Funcionario funcionario = new Funcionario();
        System.out.print("Nome: ");
        funcionario.setNome(scan.nextLine());
        System.out.print("CPF: ");
        funcionario.setCpf(scan.nextLine());
        System.out.print("Salario: ");
        funcionario.setSalario(scan.nextDouble());
        scan.nextLine();
        System.out.print("CargoId: ");
        Cargo cargo = cargoRepository.findById(scan.nextInt()).get();
        funcionario.setCargo(cargo);
        funcionario.setUnidades(unidades(scan));
        repository.save(funcionario);
        System.out.println(" ** Novo funcionario criado ** ");
        System.out.println("\nEnter para voltar ao menu");
        scan.nextLine();
    }

    public void listar(Scanner scan) {
        System.out.println("\n === Listar funcionarios ==");

        System.out.print("Digite a pagina que deseja visualizar: ");
        int page = scan.nextInt() - 1;
        scan.nextLine();

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "nome"));

        Page<Funcionario> pages = repository.findAll(pageable);

        System.out.println("\nIndice " + (pages.getNumber() + 1) + "-" + pages.getTotalPages());

        pages.forEach(f -> {
            System.out.println("\nId: " + f.getId() + "\nNome: " + f.getNome() + "\nCPF: " + f.getCpf() + "\nSalario: "
                    + f.getSalario() + "\nData contratação: " + f.getDataContratacao() + "\nUnidades: ");
            f.getUnidades().forEach(u -> {
                System.out.println(
                        "Id: " + u.getId() + "\nDescrição: " + u.getDescricao() + "\nEndereço: " + u.getEndereco());
            });
        });

        System.out.println("\nTotal elementos: " + pages.getNumberOfElements());

        System.out.println("\nEnter para voltar ao menu");
        scan.nextLine();
    }

    public void atualizar(Scanner scan) {
        System.out.println("\n === Atualizar funcionario ===");
        System.out.print("Id do funcionario: ");
        Integer id = scan.nextInt();
        scan.nextLine();
        if (id != null) {
            Funcionario funcionario = repository.findById(id).get();
            System.out.print("Nome: ");
            funcionario.setNome(scan.nextLine());
            System.out.print("CPF: ");
            funcionario.setCpf(scan.nextLine());
            System.out.print("Salario: ");
            funcionario.setSalario(scan.nextDouble());
            scan.nextLine();
            System.out.print("CargoId: ");
            Cargo cargo = cargoRepository.findById(scan.nextInt()).get();
            funcionario.setCargo(cargo);
            funcionario.setUnidades(unidades(scan));
            repository.save(funcionario);
            System.out.println(" ** Funcionario atualizado ** ");
            System.out.println("\nEnter para voltar ao menu");
            scan.nextLine();
        }
    }

    public void remover(Scanner scan) {
        System.out.println("\n === Remover funcionario ===");
        System.out.println("Id do funcionario");
        Integer id = scan.nextInt();
        if (id != null) {
            Funcionario funcionario = repository.findById(id).get();
            repository.delete(funcionario);
            System.out.println(" ** Funcionario removido ** ");
            System.out.println("\nEnter para voltar ao menu");
            scan.nextLine();
        }
    }

    private Set<UnidadeTrabalho> unidades(Scanner scan) {
        Set<UnidadeTrabalho> unidades = new HashSet<>();
        Boolean isTrue = true;
        while (isTrue) {
            System.out.print("UnidadeId ([0] para sair): ");
            Integer id = scan.nextInt();
            scan.nextLine();
            if (id != 0) {
                UnidadeTrabalho unidade = unidadeRepository.findById(id).get();
                unidades.add(unidade);
            } else {
                isTrue = false;
            }
        }
        return unidades;
    }

}
