-- ==========================================
-- v1__create__tables.sql
-- Sistema de Reservas de Hotel
-- ==========================================

-- Tabela HOTEL
CREATE TABLE hotel (
    id BIGSERIAL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    endereco VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(100),
    telefone VARCHAR(30)
);

CREATE TYPE status_quarto AS ENUM (
    'DISPONIVEL',
    'OCUPADO'
    'MANUTENCAO'
);

-- Tabela QUARTO
CREATE TABLE quarto (
    id BIGSERIAL AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(20) NOT NULL,
    tipo VARCHAR(50),
    preco_diaria NUMERIC(10,2) NOT NULL,
    status status_quarto DEFAULT 'DISPONIVEL',
    hotel_id BIGSERIAL NOT NULL,
    CONSTRAINT fk_quarto_hotel FOREIGN KEY (hotel_id) REFERENCES hotel(id) ON DELETE CASCADE
);

-- Tabela: CLIENTE
CREATE TABLE cliente (
    id BIGSERIAL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL ,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefone VARCHAR(30),
    documento VARCHAR(20) UNIQUE
);

CREATE TYPE status_reserva AS ENUM (
    'PENDENTE',
    'EM_ANDAMENTO',
    'CONFIRMADA',
    'CANCELADA'
);

-- Tabela: RESERVA
CREATE TABLE reserva (
    id BIGSERIAL AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGSERIAL NOT NULL,
    quarto_id BIGSERIAL NOT NULL,
    data_check_in DATE NOT NULL,
    data_check_out DATE NOT NULL,
    valor_total NUMERIC(10,2),
    status status_reserva DEFAULT 'PENDENTE',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reserva_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE,
    CONSTRAINT fk_reserva_quarto FOREIGN KEY (quarto_id) REFERENCES quarto(id) ON DELETE CASCADE
);

CREATE TABLE usuario (
    id BIGSERIAL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(50) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE hospede_reserva (
    id BIGSERIAL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    documento VARCHAR(20),
    reserva_id BIGSERIAL NOT NULL,
    CONSTRAINT fk_hospede_reserva_reserva
        FOREIGN KEY (reserva_id)
        REFERENCES reserva(id)
        ON DELETE CASCADE
);

-- Índices para otimização de consultas
CREATE INDEX idx_quarto_hotel ON quarto (hotel_id);
CREATE INDEX idx_reserva_cliente ON reserva (cliente_id);
CREATE INDEX idx_reserva_quarto ON reserva (quarto_id);
CREATE INDEX idx_reserva_status ON reserva (status);