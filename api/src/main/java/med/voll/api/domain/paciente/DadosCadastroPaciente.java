package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
        @NotBlank(message = "{nome.obrigatorio}")
        String nome,
        @Email(message = "{email.invalido}")
        @NotBlank(message = "{email.obrigatorio}")
        String email,
        @NotBlank(message = "{telefone.obrigatorio}")
        String telefone,
        @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}$", message = "cpf.invalido")
        @NotBlank(message = "cpf.obrigatorio")
        String cpf,
        @NotNull(message = "{endereco.obrigatorio}")
        @Valid
        DadosEndereco endereco

) {
}

