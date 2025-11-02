-- ==========================================
-- v1__create__tables.sql
-- Sistema de Reservas de Hotel
-- ==========================================

-- Tabela HOTEL
CREATE TABLE hotel (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    endereco VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(100),
    telefone VARCHAR(30)
);

-- Tabela QUARTO
CREATE TABLE quarto (
    id BIGSERIAL PRIMARY KEY,
    numero VARCHAR(20) NOT NULL,
    tipo VARCHAR(50),
    preco_diaria NUMERIC(10,2) NOT NULL,
    disponivel BOOLEAN DEFAULT TRUE,
    hotel_id BIGINT NOT NULL,
    CONSTRAINT fk_quarto_hotel FOREIGN KEY (hotel_id) REFERENCES hotel(id) ON DELETE CASCADE
);

-- Tabela: CLIENTE
CREATE TABLE cliente (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL ,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefone VARCHAR(30),
    documento VARCHAR(20) UNIQUE
);

-- Tabela: RESERVA
CREATE TABLE reserva (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    quarto_id BIGINT NOT NULL,
    data_check_in DATE NOT NULL,
    data_check_out DATE NOT NULL,
    valor_total NUMERIC(10,2),
    status VARCHAR(20) DEFAULT 'ATIVA',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reserva_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE,
    CONSTRAINT fk_reserva_quarto FOREIGN KEY (quarto_id) REFERENCES quarto(id) ON DELETE CASCADE
);

-- Índices para otimização de consultas
CREATE INDEX idx_quarto_hotel ON quarto (hotel_id);
CREATE INDEX idx_reserva_cliente ON reserva (cliente_id);
CREATE INDEX idx_reserva_quarto ON reserva (quarto_id);
CREATE INDEX idx_reserva_status ON reserva (status);