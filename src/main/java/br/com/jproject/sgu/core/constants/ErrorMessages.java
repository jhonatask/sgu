package br.com.jproject.sgu.core.constants;

/**
 * Constantes para mensagens de erro do sistema SGU
 * Centraliza todas as mensagens para facilitar manutenção e internacionalização
 */
public final class ErrorMessages {

    private ErrorMessages() {
        // Construtor privado para evitar instanciação
    }

    // ===========================================
    // MENSAGENS DE VALIDAÇÃO
    // ===========================================
    
    public static final String NAME_REQUIRED = "Nome é obrigatório";
    public static final String NAME_SIZE = "Nome deve ter entre 2 e 100 caracteres";
    public static final String NAME_PATTERN = "Nome deve conter apenas letras e espaços";
    
    public static final String EMAIL_REQUIRED = "Email é obrigatório";
    public static final String EMAIL_INVALID = "Email deve ter um formato válido";
    public static final String EMAIL_SIZE = "Email deve ter no máximo 255 caracteres";
    
    public static final String PASSWORD_SIZE = "Senha deve ter entre 8 e 128 caracteres";
    public static final String PASSWORD_PATTERN = "Senha deve conter pelo menos: 1 letra minúscula, 1 maiúscula, 1 dígito e 1 caractere especial";
    
    public static final String PHONE_REQUIRED = "Telefone é obrigatório";
    public static final String PHONE_PATTERN = "Telefone deve ter formato válido: (XX)XXXXX-XXXX ou (XX)XXXX-XXXX";
    
    public static final String CPF_REQUIRED = "CPF/CNPJ é obrigatório";
    public static final String CPF_PATTERN = "CPF/CNPJ deve ter formato válido";
    
    public static final String DEPARTMENT_REQUIRED = "Departamento é obrigatório";

    // ===========================================
    // MENSAGENS DE NEGÓCIO
    // ===========================================
    
    public static final String USER_NOT_FOUND = "Usuário não encontrado";
    public static final String USER_NOT_FOUND_WITH_ID = "Usuário não encontrado com ID: %s";
    
    public static final String DEPARTMENT_NOT_FOUND = "Departamento não encontrado";
    public static final String DEPARTMENT_NOT_FOUND_WITH_ID = "Departamento não encontrado com ID: %s";
    
    public static final String INVALID_PASSWORD = "Senha inválida";
    public static final String INVALID_CREDENTIALS = "Credenciais inválidas";

    // ===========================================
    // MENSAGENS DE DUPLICAÇÃO
    // ===========================================
    
    public static final String CPF_ALREADY_REGISTERED = "Não é possível cadastrar usuário com mesmo CPF/CNPJ";
    public static final String CPF_ALREADY_REGISTERED_UPDATE = "Não é possível atualizar usuário com CPF/CNPJ já cadastrado por outro usuário";
    
    public static final String EMAIL_ALREADY_REGISTERED = "Email já cadastrado no sistema";
    public static final String EMAIL_ALREADY_REGISTERED_UPDATE = "Email já cadastrado por outro usuário no sistema";

    // ===========================================
    // MENSAGENS DE AUTORIZAÇÃO
    // ===========================================
    
    public static final String ACCESS_DENIED = "Você não tem permissão para acessar este recurso";
    public static final String UNAUTHORIZED = "Não autorizado";
    public static final String TOKEN_EXPIRED = "Token expirado";
    public static final String TOKEN_INVALID = "Token inválido";

    // ===========================================
    // MENSAGENS DE SISTEMA
    // ===========================================
    
    public static final String INTERNAL_SERVER_ERROR = "Erro interno do servidor";
    public static final String VALIDATION_FAILED = "Dados de entrada inválidos";
    public static final String INVALID_JSON = "Formato JSON inválido";
    public static final String RATE_LIMIT_EXCEEDED = "Limite de requisições excedido. Tente novamente mais tarde";

    // ===========================================
    // MENSAGENS DE CSV
    // ===========================================
    
    public static final String CSV_FILE_NOT_FOUND = "Arquivo CSV não encontrado";
    public static final String CSV_INVALID_FORMAT = "Formato do arquivo CSV inválido";
    public static final String CSV_IMPORT_ERROR = "Erro ao importar arquivo CSV";
    public static final String CSV_EMPTY_FILE = "Arquivo CSV está vazio";

    // ===========================================
    // MENSAGENS DE CACHE
    // ===========================================
    
    public static final String CACHE_ERROR = "Erro no sistema de cache";
    public static final String CACHE_NOT_AVAILABLE = "Cache não disponível";

    // ===========================================
    // MENSAGENS DE BANCO DE DADOS
    // ===========================================
    
    public static final String DATABASE_CONNECTION_ERROR = "Erro de conexão com o banco de dados";
    public static final String DATABASE_CONSTRAINT_VIOLATION = "Violação de restrição do banco de dados";
    public static final String DATABASE_TIMEOUT = "Timeout na operação do banco de dados";

    // ===========================================
    // MENSAGENS DE VALIDAÇÃO DE NEGÓCIO
    // ===========================================
    
    public static final String USER_ALREADY_ACTIVE = "Usuário já está ativo";
    public static final String USER_ALREADY_INACTIVE = "Usuário já está inativo";
    public static final String USER_CANNOT_DELETE_SELF = "Usuário não pode deletar a si mesmo";
    public static final String USER_CANNOT_UPDATE_SELF = "Usuário não pode atualizar a si mesmo";
    
    public static final String DEPARTMENT_HAS_USERS = "Não é possível deletar departamento que possui usuários";
    public static final String DEPARTMENT_ALREADY_EXISTS = "Departamento já existe";

    // ===========================================
    // MÉTODOS UTILITÁRIOS
    // ===========================================
    
    /**
     * Formata uma mensagem com parâmetros
     * @param message Mensagem com placeholders
     * @param params Parâmetros para substituir
     * @return Mensagem formatada
     */
    public static String format(String message, Object... params) {
        return String.format(message, params);
    }
    
    /**
     * Cria mensagem de erro com timestamp
     * @param message Mensagem base
     * @return Mensagem com timestamp
     */
    public static String withTimestamp(String message) {
        return String.format("[%s] %s", java.time.LocalDateTime.now(), message);
    }
}
