package br.edu.utfpr.pb.projeto_ecommerce.server.security;

public class SecurityConstants {

    // SECRET é a chave secreta que o servidor usa
    // para assinar o token quando ele é criado
    // e validar a assinatura quando o token volta.
    public static final String SECRET = "utfpr";

    // Aqui é o tempo em MS para o token durar 1 dia
    public static final long EXPIRATION_TIME = 86400000;

    // Prefixo esperado antes do token no header Authorization.
    // Exemplo:
    // Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
    //
    // Na autorização, o sistema verifica algo como:
    //
    // if (header == null ||
    //     !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
    //     ...
    // }
    //
    public static final String TOKEN_PREFIX = "Bearer ";


    public static final String HEADER_STRING = "Authorization";
    // é o tipo de Header que vai ser solicitado para colocar o TOKEN_PREFIX
}
