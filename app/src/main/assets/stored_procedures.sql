-- stored_procedures.sql

-- Define a Stored Procedure para inserir um usuário
CREATE PROCEDURE InsertUser (
    @name TEXT,
    @username TEXT,
    @password TEXT,
    @email TEXT,
    @birthdate TEXT,
    @sex TEXT,
    @type TEXT,
    @cpf_cnpj TEXT,
    @photoUrl TEXT,
    @address TEXT
)
AS
BEGIN
    INSERT INTO users (name, username, password, email, birthdate, sex, type, cpf_cnpj, photoUrl, address)
    VALUES (@name, @username, @password, @email, @birthdate, @sex, @type, @cpf_cnpj, @photoUrl, @address);
END;
