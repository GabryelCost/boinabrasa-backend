package com.cefetmg.boinabrasa.config;

import com.cefetmg.boinabrasa.entity.Pessoa;
import com.cefetmg.boinabrasa.entity.Role;
import com.cefetmg.boinabrasa.entity.TipoPessoa;
import com.cefetmg.boinabrasa.entity.Usuario;
import com.cefetmg.boinabrasa.repository.PessoaRepository;
import com.cefetmg.boinabrasa.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome("Grente Base");
            pessoa.setEmail("gerente@gmail.com");
            pessoa.setCpfCnpj("00000000000");
            pessoa.setTipo(TipoPessoa.Gerente);

            Usuario admin = Usuario.builder()
                    .login("gerente@gmail.com")
                    .senha(passwordEncoder.encode("@Gerente1234"))
                    .role(Role.GERENTE)
                    .pessoa(pessoa)
                    .build();

            usuarioRepository.save(admin);
            System.out.println("Usuário administrador criado: login=admin@gmail.com, senha=@Admin1234");

            Pessoa pessoaAcougueiro = new Pessoa();
            pessoaAcougueiro.setNome("Açougueiro Base");
            pessoaAcougueiro.setEmail("acougueiro@gmail.com");
            pessoaAcougueiro.setCpfCnpj("11111111111");
            pessoaAcougueiro.setTipo(TipoPessoa.Acougueiro);

            Usuario acougueiro = Usuario.builder()
                    .login("acougueiro@gmail.com")
                    .senha(passwordEncoder.encode("@Acougueiro1234"))
                    .role(Role.ACOUGUEIRO)
                    .pessoa(pessoaAcougueiro)
                    .build();

            usuarioRepository.save(acougueiro);
            System.out.println("Usuário açougueiro criado: login=acougueiro@gmail.com, senha=@Acougueiro1234");

            Pessoa pessoaAtendente = new Pessoa();
            pessoaAtendente.setNome("Atendente Base");
            pessoaAtendente.setEmail("atendente@gmail.com");
            pessoaAtendente.setCpfCnpj("22222222222");
            pessoaAtendente.setTipo(TipoPessoa.Atendente);

            Usuario atendente = Usuario.builder()
                    .login("atendente@gmail.com")
                    .senha(passwordEncoder.encode("@Atendente1234"))
                    .role(Role.ATENDENTE)
                    .pessoa(pessoaAtendente)
                    .build();

            usuarioRepository.save(atendente);
            System.out.println("Usuário atendente criado: login=atendente@gmail.com, senha=@Atendente1234");
        }
    }
}
