-- ==========================================
-- v1__create__tables.sql
-- Sistema de Reservas de Hotel
-- ==========================================

-- Tabela ENDERECO
CREATE TABLE endereco (
    id BIGSERIAL PRIMARY KEY,
    cep VARCHAR(9),
    logradouro VARCHAR(150) NOT NULL,
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL
);

-- Tabela HOTEL
CREATE TABLE hotel (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    telefone VARCHAR(30),
    endereco_id BIGINT NOT NULL,
    CONSTRAINT fk_hotel_endereco FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);


CREATE TYPE status_quarto AS ENUM (
    'DISPONIVEL',
    'OCUPADO'
    'MANUTENCAO'
);

CREATE TYPE tipo_quarto AS ENUM (
    'STANDARD',
    'LUXO'
    'MASTER'
);

-- Tabela QUARTO
CREATE TABLE quarto (
    id BIGSERIAL PRIMARY KEY,
    numero VARCHAR(20) NOT NULL,
    tipo tipo_quarto DEFAULT 'STANDARD',
    preco_diaria NUMERIC(10,2) NOT NULL,
    status status_quarto DEFAULT 'DISPONIVEL',
    hotel_id BIGSERIAL NOT NULL,
    CONSTRAINT fk_quarto_hotel FOREIGN KEY (hotel_id) REFERENCES hotel(id) ON DELETE CASCADE
);

CREATE TYPE tipo_cliente AS ENUM (
    'PESSOA_FISICA',
    'PESSOA_JURIDICA',
    'AGENCIA'
);

CREATE TYPE tipo_documento AS ENUM (
    'CPF',
    'RG',
    'CNPJ',
    'PASSAPORTE'
);

-- Tabela: CLIENTE
CREATE TABLE cliente (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL ,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefone VARCHAR(30),
    tipo_cliente tipo_cliente NOT NULL DEFAULT 'PESSOA_FISICA',
    tipo_documento tipo_documento NOT NULL,
    documento VARCHAR(20) UNIQUE,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TYPE status_reserva AS ENUM (
    'PENDENTE',
    'CHECK_IN',
    'CHECK_OUT'
    'CONFIRMADA',
    'CANCELADA'
);

-- Tabela: RESERVA
CREATE TABLE reserva (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGSERIAL NOT NULL,
    quarto_id BIGSERIAL NOT NULL,
    data_check_in DATE NOT NULL,
    data_check_out DATE NOT NULL,
    valor_total NUMERIC(10,2),
    status status_reserva DEFAULT 'PENDENTE',
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reserva_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE,
    CONSTRAINT fk_reserva_quarto FOREIGN KEY (quarto_id) REFERENCES quarto(id) ON DELETE CASCADE
);

CREATE TYPE perfil AS ENUM (
    'ADMIN',
    'USER',
    'GERENTE'
    'RECEPCIONISTA',
    'CAMAREIRA',
    'HOSPEDE'
);

CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    perfil perfil DEFAULT 'USER',
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE hospede_reserva (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    documento VARCHAR(20),
    reserva_id BIGSERIAL NOT NULL,
    CONSTRAINT fk_hospede_reserva_reserva
        FOREIGN KEY (reserva_id)
        REFERENCES reserva(id)
        ON DELETE CASCADE
);

CREATE TABLE password_reset_token (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    expiration TIMESTAMP NOT NULL,
    usuario_id BIGINT NOT NULL,

    CONSTRAINT fk_password_token_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
        ON DELETE CASCADE
);

-- Índices para otimização de consultas
CREATE INDEX idx_quarto_hotel ON quarto (hotel_id);
CREATE INDEX idx_reserva_cliente ON reserva (cliente_id);
CREATE INDEX idx_reserva_quarto ON reserva (quarto_id);
CREATE INDEX idx_reserva_status ON reserva (status);