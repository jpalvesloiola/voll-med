package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repository;

    /**
     * quando o usuário efetuar o login, o Spring busca pela classe AutenticacaoService - por ser a responsável por
     * implementar a UserDetailsService - e chama o método loadUserByUsername, passando o username digitado no
     * formulário de login.
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
}
