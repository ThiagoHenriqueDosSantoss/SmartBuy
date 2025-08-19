package com.example.SmartBuy.service;

import com.example.SmartBuy.dto.Usuario.UsuarioDTO;
import com.example.SmartBuy.entities.Usuario;
import com.example.SmartBuy.enums.SituacaoUsuarioEnum;
import com.example.SmartBuy.enums.UsuarioEnum;
import com.example.SmartBuy.repository.UsuarioRepository;
import com.example.SmartBuy.security.CriptografiaUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = false)
    public Usuario criarUsuario(UsuarioDTO dto) {
        try {
            Usuario usuario = new Usuario();

            if (dto.getNome() == null || dto.getNome().isBlank()) {
                throw new BadRequestException("O campo nome é obrigatório preencher!");
            }
            usuario.setNome(dto.getNome());

            if (dto.getEmail() == null || dto.getEmail().isBlank()) {
                throw new BadRequestException("O campo email é obrigatório preencher!");
            }
            usuario.setEmail(dto.getEmail());

            if (dto.getSenha() == null || dto.getSenha().isBlank()) {
                throw new BadRequestException("O campo senha é obrigatório preencher!");
            }
            String senhaHash = CriptografiaUtil.gerarHash(dto.getSenha());
            usuario.setSenha(senhaHash);

            if (dto.getTipo() == null) {
                throw new BadRequestException("Por favor informe um tipo de pessoa!");
            }


            if (dto.getTipo() == UsuarioEnum.FISICA) {
                if (dto.getCpf() == null || dto.getCpf().isBlank()) {
                    throw new BadRequestException("CPF é obrigatório para pessoa física.");
                }
                usuario.setCpf(dto.getCpf());
            } else if (dto.getTipo() == UsuarioEnum.JURIDICA) {
                if (dto.getCnpj() == null || dto.getCnpj().isBlank()) {
                    throw new BadRequestException("CNPJ é obrigatório para pessoa jurídica.");
                }
                usuario.setCnpj(dto.getCnpj());
            } else {
                throw new BadRequestException("Tipo de pessoa inválido. Use FISICA ou JURIDICA.");
            }

            usuario.setSituacaoUsuario(SituacaoUsuarioEnum.ATIVO);
            usuario.setTipo(dto.getTipo()); // seta o enum direto
            usuario.setDataCriacao(LocalDateTime.now());

            return usuarioRepository.save(usuario);

        } catch (Exception e) {
            logger.severe("Erro para criar o usuario no services: " + e.getMessage());
            return null;
        }
    }

    @Transactional(readOnly = false)
    public boolean deletarUsuario(long idUsuario){
        try{
            Optional<Usuario> existingUsuario =usuarioRepository.findById(idUsuario);

            if(existingUsuario == null){
                throw new BadRequestException("Usuário não encontrado ou não existe!");
            }else{
                usuarioRepository.deleteById(idUsuario);
                return true;
            }
        } catch (Exception e) {
            logger.severe("Erro ao deletar usuario no services"+e);
        }
        return false;
    }

    @Transactional(readOnly = false)
    public Usuario atualizarUsuario(long idUser, UsuarioDTO dto) {
        try {
            Optional<Usuario> procuraUsuario = usuarioRepository.findById(idUser);

            if (procuraUsuario.isEmpty()) {
                throw new BadRequestException("O id informado não foi encontrado!");
            }

            Usuario usuario = procuraUsuario.get();

            if (dto.getNome() != null && !dto.getNome().isBlank()) {
                usuario.setNome(dto.getNome());
            }

            if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
                usuario.setEmail(dto.getEmail());
            }

            if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
                usuario.setSenha(dto.getSenha());
            }

            if (dto.getTipo() != null) {
                usuario.setTipo(dto.getTipo());

                if (dto.getTipo() == UsuarioEnum.FISICA) {
                    if (dto.getCpf() != null && !dto.getCpf().isBlank()) {

                        usuario.setCpf(dto.getCpf());
                        usuario.setCnpj(null);

                    } else if (usuario.getCpf() == null || usuario.getCpf().isBlank()) {
                        throw new BadRequestException("CPF é obrigatório para pessoa física.");
                    }
                } else if (dto.getTipo() == UsuarioEnum.JURIDICA) {
                    if (dto.getCnpj() != null && !dto.getCnpj().isBlank()) {

                        usuario.setCnpj(dto.getCnpj());
                        usuario.setCpf(null);

                    } else if (usuario.getCnpj() == null || usuario.getCnpj().isBlank()) {
                        throw new BadRequestException("CNPJ é obrigatório para pessoa jurídica.");
                    }
                } else {
                    throw new BadRequestException("Tipo de pessoa inválido. Use FISICA ou JURIDICA.");
                }
            }
            if(dto.getSituacaoUsuario() == SituacaoUsuarioEnum.ATIVO || dto.getSituacaoUsuario() == SituacaoUsuarioEnum.INATIVO){
                usuario.setSituacaoUsuario(dto.getSituacaoUsuario());
            }else{
                throw new BadRequestException("Informe uma situação para o usuário - ATIVO ou INATIVO");
            }
            usuario.setDataAtualizacao(LocalDateTime.now());

            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            logger.severe("Erro ao atualizar usuário no service: " + e.getMessage());
            return null;
        }

    }
    public List<Usuario> listarUsuarios(){
        try{
            List<Usuario> listUsuario = usuarioRepository.findAll();
            if(listUsuario != null){
                return listUsuario;
            }else{
                throw new Exception("Lista de usuários vazia!");
            }
        } catch (Exception e) {
            logger.severe("ERROR: Falha ao listar usuario no services!");
        }
        return null;
    }
}
